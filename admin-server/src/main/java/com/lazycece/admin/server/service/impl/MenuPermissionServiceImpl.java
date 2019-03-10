package com.lazycece.admin.server.service.impl;

import com.lazycece.admin.server.mysql.mapper.AdminRoleMapper;
import com.lazycece.admin.server.mysql.mapper.MenuMapper;
import com.lazycece.admin.server.mysql.mapper.PermissionMapper;
import com.lazycece.admin.server.service.MenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lazycece
 */
@Service
public class MenuPermissionServiceImpl implements MenuPermissionService {

    private AdminRoleMapper adminRoleMapper;
    private PermissionMapper permissionMapper;
    private MenuMapper menuMapper;

    @Autowired
    public MenuPermissionServiceImpl(AdminRoleMapper adminRoleMapper, PermissionMapper permissionMapper, MenuMapper menuMapper) {
        this.adminRoleMapper = adminRoleMapper;
        this.permissionMapper = permissionMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    public void deletePermission(Long permissionId) {

    }

    @Override
    public void deleteMenu(Long menuId) {

    }
}
