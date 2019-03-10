package com.lazycece.admin.server.mysql.mapper.provider;

import com.lazycece.admin.common.utils.StringUtils;
import com.lazycece.admin.server.mysql.mapper.dto.AdminRoleQueryParamDto;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author lazycece
 */
public class AdminRoleMapperProvider {

    public String listAdminRole(AdminRoleQueryParamDto paramDto) {
        return queryList(paramDto).SELECT("*").FROM("admin_role")
                .ORDER_BY("create_time DESC")
                .toString() + CommonSqlBuilder.limitSqlBuilder(paramDto.getPage(), paramDto.getLimit());
    }

    public String listCount(AdminRoleQueryParamDto paramDto) {
        return queryList(paramDto).SELECT("COUNT(*)").FROM("admin_role").toString();
    }

    private SQL queryList(AdminRoleQueryParamDto paramDto) {
        return new SQL() {{
            if (StringUtils.isNotBlank(paramDto.getName())) {
                WHERE("name LIKE '%" + paramDto.getName() + "%'");
            }
        }};
    }
}
