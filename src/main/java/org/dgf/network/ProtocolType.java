package org.dgf.network;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ProtocolType {
    HTTP,
    SFTP,
    FTP;

    public static List<String> types = Stream.of(ProtocolType.values()).map(t -> t.name()).collect(Collectors.toList());
}
