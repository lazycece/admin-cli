package com.lazycece.admin.server.mysql.mapper;

import com.lazycece.admin.server.mysql.po.Permission;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author lazycece
 */
@Repository
public interface PermissionMapper {

    String COLUMNS = "create_time,update_time,status,operator,name,permission,description,menu_id";

    @Insert({"INSERT INFO permission(" + COLUMNS + ") " +
            " VALUES(#{createTime},#{updateTime},#{status},#{operator},#{name},#{permission},#{description},#{menuId})"})
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(Permission permission);

    @Delete({"DELETE FROM permission WHERE id = #{id}"})
    int deleteById(long id);

    @Delete({"DELETE FROM permission WHERE menu_id = #{menuId}"})
    int deleteByMenuId(long menuId);

    @Select({"SELECT * FROM permission WHERE menu_id = #{menuId}"})
    List<Permission> findByMenuId(long menuId);

    @Select({"SELECT * FORM permission WHERE id IN #{ids} "})
    List<Permission> findByIds(Set<Long> ids);

    @Select({"SELECT * FORM permission WHERE permission = #{permission} and menu_id = #{menuId} "})
    Permission findByPermissionAndMenuId(@Param("permission") String permission, @Param("menuId") Long menuId);

    @Update({"UPDATE permission" +
            " SET operator=#{operator},name=#{name},permission=#{permission},description=#{description},menu_id=#{menuId}" +
            " WHERE id = #{id}"})
    int update(Permission permission);
}
