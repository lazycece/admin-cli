package com.lazycece.admin.server.mysql.mapper.provider;

import com.lazycece.admin.common.utils.StringUtils;
import com.lazycece.admin.server.mysql.mapper.dto.AdminUserQueryParamDto;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author lazycece
 */
public class AdminUserMapperProvider {

    public String listAdminUser(AdminUserQueryParamDto paramDto) {
        return queryList(paramDto).SELECT("*").FROM("admin_user")
                .ORDER_BY("create_time DESC")
                .toString() + CommonSqlBuilder.limitSqlBuilder(paramDto.getPage(), paramDto.getLimit());
    }

    public String listCount(AdminUserQueryParamDto paramDto) {
        return queryList(paramDto).SELECT("COUNT(*)").FROM("admin_user").toString();
    }

    private SQL queryList(AdminUserQueryParamDto paramDto) {
        return new SQL() {{
            if (StringUtils.isNotBlank(paramDto.getUsername())) {
                WHERE("username LIKE '%" + paramDto.getUsername() + "%'");
            }
            if (StringUtils.isNotBlank(paramDto.getName())) {
                WHERE("name LIKE '%" + paramDto.getName() + "%'");
            }
            if (paramDto.getRoleId() != null) {
                WHERE("role_id = " + paramDto.getRoleId());
            }
        }};
    }
}
