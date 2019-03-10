package com.lazycece.admin.server.controller;

import com.lazycece.admin.server.controller.common.MockMvcUtils;
import com.lazycece.admin.server.controller.req.AdminUserInfoReq;
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
import java.util.Map;

/**
 * @author lazycece
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Slf4j
public class AdminUserControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testAdd() throws Exception {
        var req = new AdminUserInfoReq();
        req.setUsername("12_test_");
        req.setEmail("lazycece@gmail.com");
        req.setName("admin");
        req.setPassword("123456");
        req.setRoleId(1L);
        req.setTelephone("15111111111");
        MockMvcUtils.postJson(mockMvc, "/account/add", req);
    }

    @Test
    public void testDelete() throws Exception {
        DeleteMysqlReq req = new DeleteMysqlReq();
        req.setId(5L);
        MockMvcUtils.postJson(mockMvc, "/account/delete", req);
    }

    @Test
    public void testQuery() throws Exception {
        Map<String, String> body = new HashMap<>(5);
        body.put("limit", "10");
        body.put("page", "1");
        body.put("name", "");
        body.put("roleId", "1");
        body.put("username", "te");
        MockMvcUtils.get(mockMvc, "/account/query", body);
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Map<String, String> body = new HashMap<>(5);
        body.put("id", "2");
        body.put("type", "del");
        MockMvcUtils.postJson(mockMvc, "/account/update/status", body);
    }

    @Test
    public void testUpdateRole() throws Exception {
        Map<String, String> body = new HashMap<>(5);
        body.put("id", "2");
        body.put("roleId", "2");
        MockMvcUtils.postJson(mockMvc, "/account/update/role", body);
    }
}
