package org.tdf4j.example.core.exceptions;

public class CasterException extends TranslatorException {
    public CasterException(final String message) {
        super(message);
    }

    public CasterException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CasterException(final Throwable cause) {
        super(cause);
    }
}
