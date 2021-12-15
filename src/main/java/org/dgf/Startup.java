package org.dgf;

import com.google.common.collect.ImmutableList;
import org.dgf.model.Task;
import org.dgf.model.TaskResult;
import org.dgf.network.Action;
import org.dgf.network.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 */
public class Startup
{
    private static final Logger logger = LoggerFactory.getLogger(Startup.class);

    public static Task generateTask(String source, String destination) {
        int quoteIdex = source.indexOf("://");
        String typePrefix = source.substring(0, quoteIdex);

        if (!ProtocolType.types.contains(typePrefix.toUpperCase())) {
            logger.warn("Not support protocol {}", typePrefix);
            return null;
        }

        ProtocolType type = ProtocolType.valueOf(typePrefix.toUpperCase());
        Task task = new Task(type, source, destination, Action.DOWNLOAD);

        return task;
    }

    public static void main( String[] args )
    {
        if (args.length < 2) {
            throw new IllegalArgumentException("Required 2 params, Startup <Path> <list of sources and split by ','>");
        }
        String output = args[0];
        List<String> sources = ImmutableList.copyOf(args[1].split(","));

        logger.info("Start file transfer...");
        logger.info("output {}", output);
        logger.info("sources {}", sources);

        if (sources.isEmpty()) {
            throw new IllegalArgumentException("Wrong format on 2nd params, expect <list of sources and split by ','>");
        }

        List<Task> tasks = sources.stream().map(s -> generateTask(s, output)).filter(t -> t != null).collect(Collectors.toList());

        logger.info("tasks {}", tasks);
        TransferManager tm = new TransferManager();

        List<TaskResult> results = tm.process(tasks);

        logger.info("{}", results);

        logger.info("Complete");
    }
}
