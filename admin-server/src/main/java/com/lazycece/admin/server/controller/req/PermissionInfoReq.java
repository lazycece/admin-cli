package com.lazycece.admin.server.controller.req;

import com.lazycece.admin.common.validation.group.ValidationUpdate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lazycece
 */
@Data
public class PermissionInfoReq {

    @NotNull(message = "重要参数缺失", groups = {ValidationUpdate.class})
    private Long id;
    @NotBlank(message = "资源名称不能为空")
    private String name;
    @NotBlank(message = "资源权限不能为空")
    private String permission;
    private String description;
    @NotNull(message = "请选择父级菜单(默认为0)")
    private Long menuId;
}
