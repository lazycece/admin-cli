package com.lazycece.admin.server.mysql.mapper;

import com.lazycece.admin.server.mysql.po.Menu;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author lazycece
 */
@Repository
public interface MenuMapper {

    String COLUMNS = "create_time,update_time,status,operator,name,icon,router,description,parent_id";

    @Insert({"INSERT INTO menu(" + COLUMNS + ") " +
            " VALUES(#{createTime},#{updateTime},#{status},#{operator},#{name},#{icon},#{router},#{description},#{parentId})"})
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int insert(Menu menu);

    @Delete({"DELETE FROM menu WHERE id = #{id}"})
    int deleteById(long id);

    @Delete({"DELETE FROM menu WHERE parent_id = #{parentId}"})
    int deleteByParentId(long parentId);

    @Select({"SELECT * FROM menu WHERE name = #{name}"})
    Menu findByName(String name);

    @Select({"SELECT * FROM menu WHERE parent_id = #{parentId}"})
    List<Menu> findByParentId(long parentId);

    @Update({"UPDATE menu" +
            " SET operator=#{operator},name=#{name},icon=#{icon},router=#{router},description=#{description},parent_id=#{parentId}" +
            " WHERE id = #{id}"})
    int update(Menu menu);
}
