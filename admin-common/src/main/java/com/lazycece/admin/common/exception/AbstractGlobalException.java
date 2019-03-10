package com.lazycece.admin.common.exception;

/**
 * @author lazycece
 */
public abstract class AbstractGlobalException extends RuntimeException {
    public AbstractGlobalException() {
    }

    public AbstractGlobalException(String message) {
        super(message);
    }

    public AbstractGlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractGlobalException(Throwable cause) {
        super(cause);
    }

    /**
     * global-custom-exception's code
     *
     * @return int
     */
    abstract public int getCode();
}
