package org.dgf.network;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.checkerframework.checker.nullness.Opt;
import org.dgf.model.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

import static org.dgf.network.Utility.close;
import static org.dgf.network.Utility.filterURL;

/*
Download file from SFTP Server and make sure client public key is added in server ~/.ssh/authorized_keys,
otherwise set the property '-Dpassword=xxxxxxx' with client password.
Expected url format :
    sftp://user@host:/server/location/test.txt
*/
public class SFTPTransfer implements Transfer {
    private static final Logger logger = LoggerFactory.getLogger(SFTPTransfer.class);
    private static final String PASSWORD_KEY = "password";

    public Session setupJsch(String username, String host, Optional<String> password) throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("~/.ssh/known_hosts");
        Session jschSession = jsch.getSession(username, host);

        password.ifPresent(jschSession::setPassword);
        jschSession.setConfig("StrictHostKeyChecking", "no");
        jschSession.connect(CONNECTION_TIMEOUT);

        return jschSession ;
    }

    public String[] getUserAndHost(String source) {
        int quoteIdx = source.indexOf("//");
        int commasIdx = source.indexOf(":/", quoteIdx+2);

        String pair = source.substring(quoteIdx+2, commasIdx);
        String[] userHost = pair.split("@");
        if(userHost.length != 2) {
            throw new IllegalArgumentException("Wrong format, expected 'sftP://user@host:/test' in " + source);
        }
        return userHost;
    }

    public String getFilePath(String source) {
        int commasIdx = source.lastIndexOf(":/");
        return source.substring(commasIdx+1);
    }

    @Override
    public TaskResult download(String source, String destination) {
        TaskResult result = null;
        ChannelSftp channelSftp = null;
        Session jschSession = null;
        String localFile = destination + File.separatorChar + filterURL(source);
        logger.info("write to file {}", localFile);
        FileOutputStream out = null;
        BufferedOutputStream bufferOut = null;

        try {
            String[] userHost = getUserAndHost(source);
            String user = userHost[0];
            String host = userHost[1];
            String password = System.getProperty(PASSWORD_KEY);

            jschSession = setupJsch(user, host, password == null ? Optional.empty() : Optional.of(password));
            channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
            channelSftp.connect(CONNECTION_TIMEOUT);

            out = new FileOutputStream(new File(localFile));
            bufferOut = new BufferedOutputStream(out);

            String remoteFile = getFilePath(source);
            channelSftp.get(remoteFile, bufferOut);
            bufferOut.flush();
        }
        catch (Exception e){
            File file = new File(localFile);
            if (file.exists()) {
                file.delete();
            }
            result = new TaskResult(Optional.of(e), 1, "Failed Download from " + source);
        }
        finally {
            close(bufferOut);
            close(out);
            if (channelSftp != null) {
                channelSftp.exit();
            }
            if (jschSession != null) {
                jschSession.disconnect();
            }
        }
        logger.info("done for {}",localFile);

        return result;
    }


}
