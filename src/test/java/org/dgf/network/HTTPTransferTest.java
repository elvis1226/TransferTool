package org.dgf.network;

import org.dgf.model.Task;
import org.dgf.model.TaskResult;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class HTTPTransferTest {
    @Test
    public void testHTTPDownloadWithSucceed() {
        String source = "http://localhost:9000/1.txt";
        String destination = "/local/tmp/";

        HTTPTransfer httpTransfer = Mockito.mock(HTTPTransfer.class);

        TaskResult expectedTaskResult = new TaskResult(Optional.empty(), 0, "OK");
        when(httpTransfer.download(source, destination)).thenReturn(expectedTaskResult);

        TaskResult actualResult = httpTransfer.download(source,destination);
        assertEquals(expectedTaskResult, actualResult);
    }

    @Test
    public void testHTTPDownloadWithException() {
        String source = "http://localhost:9000/1.txt";
        String destination = "/local/tmp/";

        HTTPTransfer httpTransfer = Mockito.mock(HTTPTransfer.class);

        TaskResult expectedTaskResult = new TaskResult(Optional.of(new RuntimeException("Failed")), 12, "Error");
        when(httpTransfer.download(source, destination)).thenReturn(expectedTaskResult);

        TaskResult actualResult = httpTransfer.download(source,destination);
        assertEquals(expectedTaskResult, actualResult);
    }

}
