package com.lazycece.admin.server.auth.token;

import lombok.Data;

import java.util.Set;

/**
 * @author lazycece
 */
@Data
public class UserToken {
    private String username;
    private String role;
    private Set<String> permission;
}
