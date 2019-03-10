package com.lazycece.admin.server.controller.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lazycece
 */
@Data
public class DeleteMysqlReq {
    @NotNull(message = "参数不能为空")
    private Long id;
}
