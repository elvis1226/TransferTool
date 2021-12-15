package org.dgf.model;

import org.dgf.network.Action;
import org.dgf.network.ProtocolType;

import java.util.Objects;

public final class Task {
    private final ProtocolType type;
    private final String source;
    private final String destination;
    private final Action action;

    public Task(ProtocolType type, String source, String destination, Action action) {
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.action = action;
    }

    public ProtocolType getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Action getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "Task{" +
                "type=" + type +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", action=" + action +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return type == task.type && Objects.equals(source, task.source) && Objects.equals(destination, task.destination) && action == task.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, source, destination, action);
    }
}
