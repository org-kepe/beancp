package org.kepe.beancp.exception;

public class BeancpException
        extends RuntimeException {
    public BeancpException(String message) {
        super(message);
    }

    public BeancpException(String message, Throwable cause) {
        super(message, cause);
    }
}
