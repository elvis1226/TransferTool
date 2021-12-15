package org.dgf.network;

import org.dgf.TransferManager;
import org.dgf.model.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;

import static org.dgf.network.Utility.close;
import static org.dgf.network.Utility.filterURL;

/*
 Download file from HTTP server with default connection timeout and read timeout
 Expected url format :
      http://www.server.com:port/test.txt
*/
public class HTTPTransfer implements Transfer {
    private static final Logger logger = LoggerFactory.getLogger(HTTPTransfer.class);

    @Override
    public TaskResult download(String source, String destination) {
        TaskResult result = null;
        String fileName = destination + File.separatorChar + filterURL(source);

        HttpURLConnection   urlConnection  = null;
        BufferedInputStream in             = null;
        FileOutputStream    fos            = null;

        logger.info("write to file {}", fileName);
        try{
            urlConnection = (HttpURLConnection)(new URL(source)).openConnection();
            urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
            urlConnection.setReadTimeout(READ_TIMEOUT);

            in = new BufferedInputStream(urlConnection.getInputStream());
            fos = new FileOutputStream(new File(fileName));

            byte buffer[] = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, 2048)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
            fos.flush();
            result = new TaskResult(Optional.empty(), 0, "Finish download from " + source);
        }
        catch(Exception e) {

            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            result = new TaskResult(Optional.of(e), 1, "Failed Download from " + source);
        }
        finally {
            close(fos);
            close(in);
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }

        logger.info("done for {}",fileName);
        return result;
    }
}
