package com.lazycece.admin.server.mysql.mapper;

import com.lazycece.admin.server.mysql.mapper.dto.AdminRoleQueryParamDto;
import com.lazycece.admin.server.mysql.mapper.provider.AdminRoleMapperProvider;
import com.lazycece.admin.server.mysql.po.AdminRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lazycece
 */
@Repository
public interface AdminRoleMapper {

    String COLUMNS = "create_time,update_time,status,operator,name,menu_set,permission_set,description";

    @Insert({"INSERT INTO admin_role(" + COLUMNS + ")" +
            " VALUES(#{createTime},#{updateTime},#{status},#{operator},#{name},#{menuSet},#{permissionSet},#{description})"})
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(AdminRole adminRole);

    @Delete({"DELETE FROM admin_role WHERE id = #{id}"})
    int delete(long id);

    @Select({"SELECT * FROM admin_role WHERE name = #{name}"})
    AdminRole findByName(String name);

    @SelectProvider(type = AdminRoleMapperProvider.class, method = "listAdminRole")
    List<AdminRole> listAdminRole(AdminRoleQueryParamDto paramDto);

    @SelectProvider(type = AdminRoleMapperProvider.class, method = "listCount")
    long listCount(AdminRoleQueryParamDto paramDto);

    @Select({"SELECT * FROM admin_role WHERE id = #{id}"})
    AdminRole findById(long id);

    @Update({"UPDATE admin_role " +
            " SET operator=#{operator},update_time=#{updateTime},name=#{name},menu_set=#{menuSet},permission_set=#{permissionSet},description=#{description} " +
            " WHERE id = #{id}"})
    int update(AdminRole adminRole);
}
