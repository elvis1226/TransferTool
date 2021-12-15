package org.dgf.network;

import org.dgf.model.TaskResult;

import java.io.Closeable;
import java.io.IOException;

public interface Transfer {
    int CONNECTION_TIMEOUT = 120000; // 2 min
    int READ_TIMEOUT       = 15 * 60 * 1000; // 15 min

    default TaskResult download(String source, String destination){
        return null;
    }

    default TaskResult upload(String source, String destination){
        return null;
    }

    default TaskResult process(String source, String destination, Action action){
        TaskResult result = null;
        switch (action) {
            case DOWNLOAD:
                result = download(source, destination);
            break;

            case UPLOAD:
                result = upload(source, destination);
            break;

            default: break;
        }

        return result;
    }


}
