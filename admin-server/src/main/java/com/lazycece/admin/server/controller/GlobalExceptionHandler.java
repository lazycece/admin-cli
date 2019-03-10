package com.lazycece.admin.server.controller;

import com.lazycece.admin.common.exception.AbstractGlobalException;
import com.lazycece.admin.common.reponse.ResponseCode;
import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.reponse.ResponseMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.ServletException;


/**
 * @author lazycece
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验错误异常
     *
     * @param e MethodArgumentNotValidException|BindException
     * @return ResponseData
     */
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public ResponseData bindExceptionHandler(Exception e) {
        BindingResult bindingResult;
        if (e instanceof BindException) {
            bindingResult = ((BindException) e).getBindingResult();
        } else {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        StringBuilder stringBuilder = new StringBuilder();
        bindingResult.getAllErrors().forEach(
                objectError ->
                        stringBuilder.append(",").append(objectError.getDefaultMessage())
        );
        String errorMessage = stringBuilder.toString();
        errorMessage = errorMessage.substring(1, errorMessage.length());
        return ResponseData.fail(errorMessage);
    }

    /**
     * 捕获自定义的统一全局异常
     *
     * @param e AbstractGlobalException
     * @return ResponseData
     */
    @ExceptionHandler(value = AbstractGlobalException.class)
    public ResponseData customExceptionHandler(AbstractGlobalException e) {
        return ResponseData.fail(e.getCode(), e.getMessage());
    }

    /**
     * 捕获权限控制异常
     *
     * @return ResponseData
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseData accessDeniedExceptionHandler() {
        return ResponseData.fail(ResponseCode.ACCESS_DENIED, ResponseMsg.ACCESS_DENIED);
    }

    /**
     * 捕获未知异常
     *
     * @param e Exception
     * @return ResponseData
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseData commonExceptionHandler(Exception e) throws ServletException {
        if (e instanceof ServletException) {
            throw (ServletException) e;
        }
        log.error("server inner error: {}", e);
        return ResponseData.fail(ResponseMsg.SERVER_INNER_ERROR);
    }
}
