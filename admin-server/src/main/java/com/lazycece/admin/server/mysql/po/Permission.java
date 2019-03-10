package com.lazycece.admin.server.mysql.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lazycece
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends Basic {

    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源权限
     */
    private String permission;
    /**
     * 资源的描述
     */
    private String description;
    /**
     * 所属菜单ID
     */
    private Long menuId;
}
