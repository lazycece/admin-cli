package com.lazycece.admin.server.controller.req;

import com.lazycece.admin.common.validation.group.ValidationUpdate;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author lazycece
 */
@Data
public class AdminRoleInfoReq {

    @NotNull(message = "唯一主键参数缺失", groups = {ValidationUpdate.class})
    private Long id;
    @NotBlank(message = "角色名称不能为空")
    private String name;
    @NotEmpty(message = "请选择该角色拥有的菜单")
    private Set<Long> menu;
    @NotEmpty(message = "请选择该角色拥有的资源")
    private Set<Long> permission;
    private String description;
}
