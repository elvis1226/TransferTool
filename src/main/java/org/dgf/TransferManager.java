package org.dgf;

import com.google.common.collect.ImmutableMap;
import org.dgf.model.Task;
import org.dgf.model.TaskResult;
import org.dgf.network.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.dgf.network.ProtocolType.*;

public class TransferManager {
    private static final Logger logger = LoggerFactory.getLogger(TransferManager.class);

    private static final Map<ProtocolType, Transfer> transfers = ImmutableMap.of(HTTP, new HTTPTransfer(),
                                                                                FTP, new FTPTransfer(),
                                                                                SFTP, new SFTPTransfer());

    public TransferManager() {
    }

    public List<TaskResult> process(List<Task> tasks) {
        List<TaskResult> taskResult = tasks.stream()
                .filter(t-> transfers.containsKey(t.getType()))
                .parallel()
                .map(t-> transfers.get(t.getType()).process(t.getSource(),t.getDestination(),t.getAction()))
                .collect(Collectors.toList());

        return taskResult;
    }
}
