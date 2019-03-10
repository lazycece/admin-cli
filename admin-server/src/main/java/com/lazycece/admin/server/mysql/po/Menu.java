package com.lazycece.admin.server.mysql.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lazycece
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends Basic {

    /**
     * 菜单名称
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 前端路由
     */
    private String router;
    /**
     * 菜单描述
     */
    private String description;
    /**
     * 父级菜单ID
     */
    private Long parentId;
}
