package com.lazycece.admin.server.controller.user;

import com.lazycece.admin.common.core.Status;
import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.utils.BeanMapperUtils;
import com.lazycece.admin.common.utils.StringUtils;
import com.lazycece.admin.server.auth.AuthUtils;
import com.lazycece.admin.server.auth.Role;
import com.lazycece.admin.server.auth.token.JwtTokenUtils;
import com.lazycece.admin.server.auth.token.UserToken;
import com.lazycece.admin.server.controller.req.UserLoginReq;
import com.lazycece.admin.server.controller.req.UserUpdateInfoReq;
import com.lazycece.admin.server.controller.req.UserUpdatePasswordReq;
import com.lazycece.admin.server.mysql.mapper.AdminRoleMapper;
import com.lazycece.admin.server.mysql.mapper.AdminUserMapper;
import com.lazycece.admin.server.mysql.mapper.PermissionMapper;
import com.lazycece.admin.server.mysql.po.AdminUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lazycece
 */
@RestController
@RequestMapping("/u")
public class UserController {

    private AdminUserMapper adminUserMapper;
    private AdminRoleMapper adminRoleMapper;
    private PermissionMapper permissionMapper;

    @Autowired
    public UserController(AdminUserMapper adminUserMapper, AdminRoleMapper adminRoleMapper, PermissionMapper permissionMapper) {
        this.adminUserMapper = adminUserMapper;
        this.adminRoleMapper = adminRoleMapper;
        this.permissionMapper = permissionMapper;
    }

    @PostMapping("/login")
    public ResponseData login(@Validated @RequestBody UserLoginReq loginVo) {
        AdminUser adminUser = adminUserMapper.findByUsernameAndPassword(loginVo.getUsername(), loginVo.getPassword());
        if (adminUser == null) {
            return ResponseData.fail("用户名或密码错误..");
        }
        if (adminUser.getStatus() != Status.active) {
            return ResponseData.fail("你的账号已被禁用，请联系管理员..");
        }

//        List<Permission> permissions = new ArrayList<>();
//        AdminRole adminRole = adminRoleMapper.findById(adminUser.getRoleId());
//        if (StringUtils.isNotBlank(adminRole.getPermissionSet())) {
//            permissions = permissionMapper.findByIds(new HashSet<>(JsonUtils.parseArray(adminRole.getPermissionSet(), Long.class)));
//        }

        UserToken userToken = new UserToken();
        userToken.setUsername(adminUser.getUsername());
        userToken.setRole(Role.ADMIN.name());


        Set<String> permission = new HashSet<>();
        permission.add("logout");
        userToken.setPermission(permission);
        return ResponseData.success(JwtTokenUtils.generateToken(userToken));
    }

    @GetMapping("/info")
    public ResponseData userInfo() {
        AdminUser adminUser = adminUserMapper.findByUsername(AuthUtils.getCurrentUsername());
        adminUser.setPassword("");
        return ResponseData.success(adminUser);
    }

    @GetMapping("/logout")
    public ResponseData logout() {
        /* todo */
        return ResponseData.success();
    }

    @PostMapping("/update/info")
    public Object updateSelfInfo(@RequestBody UserUpdateInfoReq req) {
        AdminUser adminUser = BeanMapperUtils.map(req, AdminUser.class);
        if (StringUtils.isNotBlank(req.getUsername())) {
            AdminUser adminUserFind = adminUserMapper.findByUsername(req.getUsername());
            if (adminUserFind != null) {
                return ResponseData.fail("此用户名已被占用..");
            }
        } else {
            adminUser.setUsername(AuthUtils.getCurrentUsername());
        }
        adminUserMapper.updateBySelf(adminUser);
        return ResponseData.success();
    }

    @PostMapping("/confirm/password")
    public Object confirmPassword(@RequestBody UserUpdatePasswordReq req) {
        AdminUser adminUserFind = adminUserMapper.findByUsername(AuthUtils.getCurrentUsername());
        if (adminUserFind == null) {
            return ResponseData.fail("用户不存在..");
        } else if (!req.getPassword().equals(adminUserFind.getPassword())) {
            return ResponseData.fail("密码不正确..");
        }
        return ResponseData.success();
    }

    @PostMapping("/change/password")
    public Object changePassword(@RequestBody UserUpdatePasswordReq req) {
        adminUserMapper.updatePassword(req.getPassword(), AuthUtils.getCurrentUsername());
        return ResponseData.success();
    }
}
