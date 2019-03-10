package com.lazycece.admin.server.controller;

import com.lazycece.admin.server.controller.common.MockMvcUtils;
import com.lazycece.admin.server.controller.req.UserLoginReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;

/**
 * @author lazycece
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testLogin() throws Exception {
        UserLoginReq req = new UserLoginReq();
        req.setUsername("admin");
        req.setPassword("123456");
        MockMvcUtils.postJson(mockMvc, "/u/login", req);
    }
}
