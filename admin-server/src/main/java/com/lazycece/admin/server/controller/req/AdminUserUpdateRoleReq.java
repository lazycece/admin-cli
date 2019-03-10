package com.lazycece.admin.server.controller.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lazycece
 */
@Data
public class AdminUserUpdateRoleReq {
    @NotNull(message = "参数不能为空")
    private Long id;
    @NotNull(message = "参数不能为空")
    private Long roleId;
}
