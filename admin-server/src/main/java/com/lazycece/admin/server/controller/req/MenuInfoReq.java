package com.lazycece.admin.server.controller.req;

import com.lazycece.admin.common.validation.group.ValidationUpdate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author lazycece
 */
@Data
public class MenuInfoReq {

    @NotNull(message = "参数id不能为空", groups = ValidationUpdate.class)
    private Long id;
    @NotBlank(message = "菜单名称不能为空")
    private String name;
    private String icon;
    @NotBlank(message = "参数router不能为空")
    private String router;
    private String description;
    @NotNull(message = "参数parentId不能为空")
    private Long parentId;
}
