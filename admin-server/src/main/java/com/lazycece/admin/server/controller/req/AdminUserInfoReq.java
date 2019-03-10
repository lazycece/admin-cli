package com.lazycece.admin.server.controller.req;

import com.lazycece.admin.common.validation.constants.ValidationRegex;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author lazycece
 */
@Data
public class AdminUserInfoReq {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = ValidationRegex.USERNAME, message = "用户名格式不正确（包含大小写字母、数字、下划线，长度4-20位）")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = ValidationRegex.PASSWORD, message = "密码格式不正确（包含大小写字母、数字，长度6-20位）")
    private String password;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "电话号码不能为空")
    @Pattern(regexp = ValidationRegex.TELEPHONE, message = "电话号码格式不正确")
    private String telephone;

    @Email(message = "邮箱格式不正确")
    private String email;

    @NotNull(message = "请为该用户分配角色")
    private Long roleId;
}
