package org.dgf.model;

import java.util.Objects;
import java.util.Optional;

public final class TaskResult {

    private final String message;
    private final int exitCode;
    private final Optional<Exception> exception;

    public TaskResult(Optional<Exception> exception, int exitCode, String message) {
        this.exception = exception;
        this.exitCode = exitCode;
        this.message = message;
    }

    public Optional<Exception> getException() {
        return exception;
    }

    public int getExitCode() {
        return exitCode;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "TaskResult{" +
                "message='" + message + '\'' +
                ", exitCode=" + exitCode +
                ", exception=" + exception +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskResult that = (TaskResult) o;
        return exitCode == that.exitCode && Objects.equals(exception, that.exception) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exception, exitCode, message);
    }
}
