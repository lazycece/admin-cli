package com.lazycece.admin.server.controller.admin;

import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.utils.BeanMapperUtils;
import com.lazycece.admin.common.validation.group.ValidationUpdate;
import com.lazycece.admin.server.controller.req.DeleteMysqlReq;
import com.lazycece.admin.server.controller.req.PermissionInfoReq;
import com.lazycece.admin.server.mysql.mapper.PermissionMapper;
import com.lazycece.admin.server.mysql.po.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author lazycece
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    private PermissionMapper permissionMapper;

    @Autowired
    public PermissionController(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @PostMapping("/add")
    @PreAuthorize(value = "hasPermission(null ,'admin:permission:add')")
    public ResponseData add(@Validated @RequestBody PermissionInfoReq req) {
        if (permissionMapper.findByPermissionAndMenuId(req.getPermission(), req.getMenuId()) != null) {
            return ResponseData.fail("该菜单下已有该资源");
        }
        permissionMapper.insert(BeanMapperUtils.map(req, Permission.class));
        return ResponseData.success();
    }

    @PostMapping("/delete")
    @PreAuthorize(value = "hasPermission(null ,'admin:permission:delete')")
    public ResponseData delete(@Validated @RequestBody DeleteMysqlReq req) {
//        int result =
        return ResponseData.success();
    }

    @GetMapping("/query")
    @PreAuthorize(value = "hasPermission(null ,'admin:permission:query')")
    public ResponseData query() {
//        int result =
        return ResponseData.success();
    }

    @PostMapping("/update")
    @PreAuthorize(value = "hasPermission(null ,'admin:permission:update')")
    public ResponseData update(@Validated(ValidationUpdate.class) @RequestBody PermissionInfoReq req) {
//        int result =
        return ResponseData.success();
    }
}