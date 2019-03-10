package com.lazycece.admin.common.exception;

import com.lazycece.admin.common.reponse.ResponseCode;

/**
 * @author lazycece
 */
public class ParamException extends AbstractGlobalException {
    public ParamException() {
    }

    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public int getCode() {
        return ResponseCode.PARAM_ERROR;
    }
}
