package com.lazycece.admin.server.controller.req;

import com.lazycece.admin.common.core.Status;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lazycece
 */
@Data
public class AdminUserUpdateStatusReq {
    @NotNull(message = "参数不能为空")
    private Long id;
    @NotNull(message = "参数不能为空")
    private Status type;
}
