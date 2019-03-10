package com.lazycece.admin.server.controller.admin;

import com.lazycece.admin.common.reponse.PageData;
import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.utils.BeanMapperUtils;
import com.lazycece.admin.server.auth.AuthUtils;
import com.lazycece.admin.server.controller.req.AdminUserInfoReq;
import com.lazycece.admin.server.controller.req.AdminUserUpdateRoleReq;
import com.lazycece.admin.server.controller.req.AdminUserUpdateStatusReq;
import com.lazycece.admin.server.controller.req.DeleteMysqlReq;
import com.lazycece.admin.server.mysql.mapper.AdminUserMapper;
import com.lazycece.admin.server.mysql.mapper.dto.AdminUserQueryParamDto;
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
@RequestMapping("/account")
public class AdminUserController {

    private AdminUserMapper adminUserMapper;

    @Autowired
    public AdminUserController(AdminUserMapper adminUserMapper) {
        this.adminUserMapper = adminUserMapper;
    }

    @PostMapping("/add")
    @PreAuthorize(value = "hasPermission(null ,'admin:account:add')")
    public ResponseData add(@Validated @RequestBody AdminUserInfoReq req) {
        AdminUser adminUser = adminUserMapper.findByUsername(req.getUsername());
        if (adminUser != null) {
            return ResponseData.fail("用户名已存在");
        }
        adminUser = BeanMapperUtils.map(req, AdminUser.class);
        adminUser.setOperator(AuthUtils.getCurrentUsername());
        adminUserMapper.insert(adminUser);
        return ResponseData.success();
    }

    @PostMapping("/delete")
    @PreAuthorize(value = "hasPermission(null ,'admin:account:delete')")
    public ResponseData delete(@Validated @RequestBody DeleteMysqlReq req) {
        int result = adminUserMapper.delete(req.getId());
        if (result == 1) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }

    @GetMapping("/query")
    @PreAuthorize(value = "hasPermission(null ,'admin:account:query')")
    public ResponseData query(@Validated AdminUserQueryParamDto req) {
        List<AdminUser> userList = new ArrayList<>();
        long count = adminUserMapper.listCount(req);
        if (count > 0) {
            userList = adminUserMapper.listAdminUser(req);
        }
        return ResponseData.success(
                PageData.builder()
                        .count(count).page(req.getPage()).data(userList)
                        .build());
    }

    @PostMapping("/update/status")
    @PreAuthorize(value = "hasPermission(null ,'admin:account:updateStatus')")
    public ResponseData addBlackList(@Validated @RequestBody AdminUserUpdateStatusReq req) {
        int result = adminUserMapper.updateStatus(req.getId(), req.getType(), AuthUtils.getCurrentUsername());
        if (result == 1) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }

    @PostMapping("/update/role")
    @PreAuthorize(value = "hasPermission(null ,'admin:account:updateRole')")
    public ResponseData updateUserRole(@Validated @RequestBody AdminUserUpdateRoleReq req) {
        int result = adminUserMapper.updateRoleId(req.getId(), req.getRoleId(), AuthUtils.getCurrentUsername());
        if (result == 1) {
            return ResponseData.success();
        } else {
            return ResponseData.fail();
        }
    }
}

