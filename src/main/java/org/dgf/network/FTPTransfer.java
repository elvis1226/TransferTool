package org.dgf.network;

import org.dgf.model.TaskResult;

/*
Download the file from FTP server, the server url must like below format,
 ftp://user:secret@www.server.com/test.txt
*/
public class FTPTransfer extends HTTPTransfer{

    @Override
    public TaskResult download(String source, String destination) {
        return super.download(source, destination);
    }
}
