package com.lazycece.admin.server.mysql.po;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author lazycece
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminUser extends Basic {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String name;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 用户角色ID
     */
    private Long roleId;
}
