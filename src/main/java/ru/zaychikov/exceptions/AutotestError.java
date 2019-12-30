package ru.zaychikov.exceptions;

public class AutotestError extends AssertionError {
    public AutotestError(String message) {
        super(message);
    }

    public AutotestError(String message, Throwable cause) {
        super(message, cause);
    }

    public AutotestError(Throwable cause) {
        super(cause);
    }
}
