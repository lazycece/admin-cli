package com.lazycece.admin.server.mysql.mapper;

import com.lazycece.admin.common.core.Status;
import com.lazycece.admin.server.mysql.mapper.dto.AdminUserQueryParamDto;
import com.lazycece.admin.server.mysql.mapper.provider.AdminUserMapperProvider;
import com.lazycece.admin.server.mysql.po.AdminUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lazycece
 */
@Repository
public interface AdminUserMapper {

    String COLUMNS = "create_time,update_time,status,operator,username,password,name,telephone,email,role_id";

    @Insert({"INSERT INTO admin_user(" + COLUMNS + ")" +
            " VALUES(#{createTime},#{updateTime},#{status},#{operator},#{username},#{password}," +
            "#{name},#{telephone},#{email},#{roleId})"})
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(AdminUser adminUser);

    @Delete({"DELETE FROM admin_user WHERE id = #{id}"})
    int delete(long id);

    @SelectProvider(type = AdminUserMapperProvider.class, method = "listAdminUser")
    List<AdminUser> listAdminUser(AdminUserQueryParamDto paramDto);

    @SelectProvider(type = AdminUserMapperProvider.class, method = "listCount")
    long listCount(AdminUserQueryParamDto paramDto);

    @Select({"SELECT * FROM admin_user WHERE username = #{username}"})
    AdminUser findByUsername(String username);

    @Select({"SELECT * FROM admin_user WHERE username = #{username} AND password = #{password}"})
    AdminUser findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Update({"UPDATE admin_user" +
            " SET operator=#{username},username=#{username},telephone=#{telephone},email=#{email},update_time=#{updateTime}" +
            " WHERE id = #{id}"})
    int updateBySelf(AdminUser adminUser);

    @Update({"UPDATE admin_user SET password=#{password},operator = #{username},update_time=#{updateTime} WHERE username=#{username}"})
    int updatePassword(@Param("password") String password, @Param("username") String username);

    @Update({"UPDATE admin_user SET role_id=#{roleId}, operator = #{operator},update_time=#{updateTime} WHERE id=#{userId}"})
    int updateRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId, @Param("operator") String operator);

    @Update({"UPDATE admin_user SET status=#{status}, operator = #{operator},update_time=#{updateTime} WHERE id=#{userId}"})
    int updateStatus(@Param("userId") Long userId, @Param("status") Status status, @Param("operator") String operator);
}
