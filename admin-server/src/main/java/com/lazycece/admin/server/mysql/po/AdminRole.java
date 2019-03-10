package com.lazycece.admin.server.mysql.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lazycece
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminRole extends Basic{
    /**
     * 角色名称
     */
    private String name;
    /**
     * 菜单主键集合/json
     */
    private String menuSet;
    /**
     * 资源主键集合/json
     */
    private String permissionSet;
    private String description;
}
