package org.dgf;

import static org.dgf.network.Action.DOWNLOAD;
import static org.dgf.network.ProtocolType.HTTP;
import static org.junit.Assert.*;

import org.dgf.model.Task;
import org.junit.Test;

public class StartupTest
{
    @Test
    public void testGenerateTask()
    {
        String http = "http://localhost:9000/test.pdf";
        String destination = "/local/tmp";

        Task task = Startup.generateTask(http, destination);

        assertEquals(http, task.getSource());
        assertEquals(destination, task.getDestination());
        assertEquals(HTTP, task.getType());
        assertEquals(DOWNLOAD, task.getAction());
    }
}
