package com.lazycece.admin.server.controller.req;

import com.lazycece.admin.common.validation.constants.ValidationRegex;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author lazycece
 */
@Data
public class UserUpdatePasswordReq {
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = ValidationRegex.PASSWORD, message = "密码格式不正确（包含大小写字母、数字，长度6-20位）")
    private String password;
}
