package com.lazycece.admin.server.controller.admin;

import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.utils.BeanMapperUtils;
import com.lazycece.admin.common.validation.group.ValidationUpdate;
import com.lazycece.admin.server.controller.req.DeleteMysqlReq;
import com.lazycece.admin.server.controller.req.MenuInfoReq;
import com.lazycece.admin.server.mysql.mapper.MenuMapper;
import com.lazycece.admin.server.mysql.po.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lazycece
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private MenuMapper menuMapper;

    @Autowired
    public MenuController(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @PostMapping("/add")
    @PreAuthorize(value = "hasPermission(null ,'admin:menu:add')")
    public ResponseData add(@Validated @RequestBody MenuInfoReq req) {
        if (menuMapper.findByName(req.getName()) != null) {
            return ResponseData.fail("菜单名已存在..");
        }
        Menu menu = BeanMapperUtils.map(req, Menu.class);
        menuMapper.insert(menu);
        return ResponseData.success();
    }

    @PostMapping("/delete")
    @PreAuthorize(value = "hasPermission(null ,'admin:menu:delete')")
    public ResponseData delete(@Validated @RequestBody DeleteMysqlReq req) {
//        int result =
        return ResponseData.success();
    }

    @GetMapping("/query")
    @PreAuthorize(value = "hasPermission(null ,'admin:menu:query')")
    public ResponseData query() {
//        int result =
        return ResponseData.success();
    }

    @PostMapping("/update")
    @PreAuthorize(value = "hasPermission(null ,'admin:menu:update')")
    public ResponseData update(@Validated(value = ValidationUpdate.class) @RequestBody MenuInfoReq req) {
        Menu menuFind = menuMapper.findByName(req.getName());
        if (menuFind != null && !menuFind.getId().equals(req.getId())) {
            return ResponseData.fail("菜单名已存在..");
        }
        int result = menuMapper.update(BeanMapperUtils.map(req, Menu.class));
        if (result == 1) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }
}
