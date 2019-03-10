package com.lazycece.admin.server.controller.admin;

import com.lazycece.admin.server.auth.AuthUtils;
import com.lazycece.admin.server.controller.req.AdminRoleInfoReq;
import com.lazycece.admin.server.controller.req.DeleteMysqlReq;
import com.lazycece.admin.common.reponse.PageData;
import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.utils.JsonUtils;
import com.lazycece.admin.common.validation.group.ValidationUpdate;
import com.lazycece.admin.server.mysql.mapper.AdminRoleMapper;
import com.lazycece.admin.server.mysql.mapper.AdminUserMapper;
import com.lazycece.admin.server.mysql.mapper.dto.AdminRoleQueryParamDto;
import com.lazycece.admin.server.mysql.mapper.dto.AdminUserQueryParamDto;
import com.lazycece.admin.server.mysql.po.AdminRole;
import com.lazycece.admin.server.mysql.po.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lazycece
 */
@RestController
@RequestMapping("/role")
public class AdminRoleController {

    private AdminRoleMapper adminRoleMapper;
    private AdminUserMapper adminUserMapper;

    @Autowired
    public AdminRoleController(AdminRoleMapper adminRoleMapper, AdminUserMapper adminUserMapper) {
        this.adminRoleMapper = adminRoleMapper;
        this.adminUserMapper = adminUserMapper;
    }

    @PostMapping("/add")
    @PreAuthorize(value = "hasPermission(null ,'admin:role:add')")
    public ResponseData add(@Validated @RequestBody AdminRoleInfoReq req) {
        if (adminRoleMapper.findByName(req.getName()) != null) {
            return ResponseData.fail("角色名已存在..");
        }
        AdminRole adminRole = new AdminRole();
        adminRole.setName(req.getName());
        adminRole.setDescription(req.getDescription());
        adminRole.setMenuSet(JsonUtils.toJSONString(req.getMenu()));
        adminRole.setPermissionSet(JsonUtils.toJSONString(req.getPermission()));
        adminRole.setOperator(AuthUtils.getCurrentUsername());
        adminRoleMapper.insert(adminRole);
        return ResponseData.success();
    }

    @PostMapping("/delete")
    @PreAuthorize(value = "hasPermission(null ,'admin:role:delete')")
    public ResponseData delete(@Validated @RequestBody DeleteMysqlReq req) {
        AdminUserQueryParamDto paramDto = new AdminUserQueryParamDto();
        paramDto.setRoleId(req.getId());
        paramDto.setPage(1);
        paramDto.setLimit(1000);
        List<AdminUser> adminUserList = adminUserMapper.listAdminUser(paramDto);
        if (adminUserList.size() > 0) {
            return ResponseData.fail("该角色已分配给用户，不能删除..");
        }
        int result = adminRoleMapper.delete(req.getId());
        if (result == 1) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }

    @GetMapping("/query")
    @PreAuthorize(value = "hasPermission(null ,'admin:role:query')")
    public ResponseData query(@Validated AdminRoleQueryParamDto req) {
        List<AdminRole> roleList = new ArrayList<>();
        long count = adminRoleMapper.listCount(req);
        if (count > 0) {
            roleList = adminRoleMapper.listAdminRole(req);
        }
        return ResponseData.success(
                PageData.builder()
                        .page(req.getPage())
                        .count(count)
                        .data(roleList).build());
    }

    @PostMapping("/update")
    @PreAuthorize(value = "hasPermission(null ,'admin:role:update')")
    public ResponseData update(@Validated(value = {ValidationUpdate.class}) @RequestBody AdminRoleInfoReq req) {
        AdminRole adminRoleFind = adminRoleMapper.findByName(req.getName());
        if (adminRoleFind != null && !adminRoleFind.getId().equals(req.getId())) {
            return ResponseData.fail("角色名已存在..");
        }
        AdminRole adminRole = new AdminRole();
        adminRole.setId(req.getId());
        adminRole.setName(req.getName());
        adminRole.setDescription(req.getDescription());
        adminRole.setMenuSet(JsonUtils.toJSONString(req.getMenu()));
        adminRole.setPermissionSet(JsonUtils.toJSONString(req.getPermission()));
        adminRole.setOperator(AuthUtils.getCurrentUsername());
        adminRoleMapper.update(adminRole);
        return ResponseData.success();
    }
}
