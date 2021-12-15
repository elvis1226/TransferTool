package org.dgf;

import com.google.common.collect.ImmutableList;
import org.dgf.model.Task;
import org.dgf.model.TaskResult;
import org.dgf.network.Action;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.dgf.network.ProtocolType.FTP;
import static org.dgf.network.ProtocolType.HTTP;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class TransferManagerTest {
    @Test
    public void testProcessWithTaskResult() {
        TransferManager tm = Mockito.mock(TransferManager.class);
        List<Task> tasks = ImmutableList.of(
                new Task(HTTP, "http://localhost/1.txt","/local/tmp", Action.DOWNLOAD),
                new Task(FTP, "ftp://localhost/1.txt","/local/tmp", Action.DOWNLOAD));

        List<TaskResult> result = ImmutableList.of(
                new TaskResult(Optional.empty(), 0, "OK"),
                new TaskResult(Optional.empty(), 0, "OK")
        );

        when(tm.process(tasks)).thenReturn(result);
        tm.process(tasks);

        assertEquals(2, result.size());
        assertEquals("OK", result.get(0).getMessage());
    }

}
