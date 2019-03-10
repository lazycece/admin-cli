package com.lazycece.admin.common.reponse;

/**
 * define the response message
 *
 * @author lazycece
 */
public interface ResponseMsg {

    String SUCCESS = "success";
    String FAIL = "fail";
    String INVALID_TOKEN = "invalid token";
    String SERVER_INNER_ERROR = "server internal error";
    String ACCESS_DENIED  = "对不起，你没有权限执行此操作！";
}
