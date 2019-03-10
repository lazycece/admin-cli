package com.lazycece.admin.server.mysql.mapper.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author lazycece
 */
@Data
public class AdminRoleQueryParamDto {
    private String name;
    @NotNull(message = "参数limit不能为空")
    private Integer limit;
    @Min(value = 1, message = "page >= 1")
    @NotNull(message = "参数page不能为空")
    private Integer page;
}
