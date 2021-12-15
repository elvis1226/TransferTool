package org.dgf.network;

import org.dgf.model.TaskResult;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class FTPTransferTest {
    @Test
    public void testFTPDownloadWithSucceed() {
        String source = "ftp://localhost:9000/1.txt";
        String destination = "/local/tmp/";

        HTTPTransfer httpTransfer = Mockito.mock(HTTPTransfer.class);

        TaskResult expectedTaskResult = new TaskResult(Optional.empty(), 0, "OK");
        when(httpTransfer.download(source, destination)).thenReturn(expectedTaskResult);

        TaskResult actualResult = httpTransfer.download(source,destination);
        assertEquals(expectedTaskResult, actualResult);
    }

    @Test
    public void testFTPDownloadWithException() {
        String source = "ftp://localhost:9000/1.txt";
        String destination = "/local/tmp/";

        HTTPTransfer httpTransfer = Mockito.mock(HTTPTransfer.class);

        TaskResult expectedTaskResult = new TaskResult(Optional.of(new RuntimeException("Failed")), 12, "Error");
        when(httpTransfer.download(source, destination)).thenReturn(expectedTaskResult);

        TaskResult actualResult = httpTransfer.download(source,destination);
        assertEquals(expectedTaskResult, actualResult);
    }
}
