package com.lazycece.admin.common.reponse;

/**
 * define the response code
 *
 * @author lazycece
 */
public interface ResponseCode {

    Integer SUCCESS = 200;
    Integer FAIL = 800;
    Integer SERVER_INNER_ERROR = 500;
    Integer ACCESS_DENIED = 403;
    Integer INVALID_TOKEN = -105;
    Integer PARAM_ERROR = 801;
}
