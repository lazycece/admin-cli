package com.lazycece.admin.server.controller;

import com.lazycece.admin.server.controller.common.MockMvcUtils;
import com.lazycece.admin.server.controller.req.AdminRoleInfoReq;
import com.lazycece.admin.server.controller.req.DeleteMysqlReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lazycece
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class AdminRoleControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testAdd() throws Exception {
        Set<Long> menu = new HashSet<>(), permission = new HashSet<>();
        menu.add(1L);
        permission.add(1L);
        AdminRoleInfoReq req = new AdminRoleInfoReq();
        req.setMenu(menu);
        req.setPermission(permission);
        req.setName("ssss");
        MockMvcUtils.postJson(mockMvc, "/role/add", req);
    }

    @Test
    public void testDelete() throws Exception {
        DeleteMysqlReq req = new DeleteMysqlReq();
        req.setId(3L);
        MockMvcUtils.postJson(mockMvc, "/role/delete", req);
    }

    @Test
    public void testQuery() throws Exception {
        Map<String, String> body = new HashMap<>(3);
        body.put("limit", "10");
        body.put("page", "1");
        body.put("name", "");
        MockMvcUtils.get(mockMvc, "/role/query", body);
    }

    @Test
    public void testUpdate() throws Exception {
        Set<Long> menu = new HashSet<>(), permission = new HashSet<>();
        menu.add(1L);
        menu.add(2L);
        permission.add(1L);
        AdminRoleInfoReq req = new AdminRoleInfoReq();
        req.setId(1L);
        req.setMenu(menu);
        req.setPermission(permission);
        req.setName("ADMIN");
        MockMvcUtils.postJson(mockMvc, "/role/update", req);
    }
}
