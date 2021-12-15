package org.dgf.network;

import junit.framework.Assert;
import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.dgf.model.TaskResult;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class SFTPTransferTest {
    @Test
    public void testGetUserAndHost() {
        String source = "sftp://user@host:/server/location/test.txt";
        SFTPTransfer sftpTransfer = new SFTPTransfer();
        String[] userHost = sftpTransfer.getUserAndHost(source);
        assertEquals("user", userHost[0]);
        assertEquals("host", userHost[1]);
    }

    @Test
    public void testGetFilePath() {
        String source = "sftp://user@host:/server/location/test.txt";
        SFTPTransfer sftpTransfer = new SFTPTransfer();
        String path = sftpTransfer.getFilePath(source);
        assertEquals("/server/location/test.txt",path);
    }


    @Test
    public void testSFTPDownloadWithSucceed() {
        String source = "sftp://user@localhost:/1.txt";
        String destination = "/local/tmp/";

        SFTPTransfer sftpTransfer = Mockito.mock(SFTPTransfer.class);

        TaskResult expectedTaskResult = new TaskResult(Optional.empty(), 0, "OK");
        when(sftpTransfer.download(source, destination)).thenReturn(expectedTaskResult);

        TaskResult actualResult = sftpTransfer.download(source,destination);
        Assert.assertEquals(expectedTaskResult, actualResult);
    }

    @Test
    public void testSFTPDownloadWithException() {
        String source = "sftp://user@localhost:/1.txt";
        String destination = "/local/tmp/";

        SFTPTransfer sftpTransfer = Mockito.mock(SFTPTransfer.class);

        TaskResult expectedTaskResult = new TaskResult(Optional.of(new RuntimeException("Failed")), 12, "Error");
        when(sftpTransfer.download(source, destination)).thenReturn(expectedTaskResult);

        TaskResult actualResult = sftpTransfer.download(source,destination);
        Assert.assertEquals(expectedTaskResult, actualResult);
    }
}
