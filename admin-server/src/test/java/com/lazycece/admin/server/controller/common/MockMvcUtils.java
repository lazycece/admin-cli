package com.lazycece.admin.server.controller.common;

import com.lazycece.admin.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @author lazycece
 */
@Slf4j
public class MockMvcUtils {
    private static final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJBRE1JTiIsImlzcyI6IlJFVVNBQkxFLUFETUlOIiwicGVybWlzc2lvbiI6IltcImxvZ291dFwiXSIsImV4cCI6MTU0MzEzMTkxMSwiaWF0IjoxNTQzMTMwMTExfQ.cVUtH2aNnQFkBDLv70aeU3KvRaqgSMg81VwDhJzpSoM";

    public static void postJson(MockMvc mockMvc, String url, Object body) throws Exception {
        printResponse(
                mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .header("ADMIN_TOKEN", token)
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(JsonUtils.toJSONString(body)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString());
    }

    public static void get(MockMvc mockMvc, String url, Map<String, String> body) throws Exception {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.setAll(body);
        printResponse(
                mockMvc.perform(MockMvcRequestBuilders.get(url)
                        .header("ADMIN_TOKEN", token)
                        .params(parameters)
                        .content(JsonUtils.toJSONString(body)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn().getResponse().getContentAsString());
    }

    private static void printResponse(String response) {
        log.info("response -> {}", response);
    }
}
