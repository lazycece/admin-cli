package com.lazycece.admin.server.auth.custom;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;
import java.util.Set;

/**
 * @author lazycece
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private Object principal;
    private Object credentials;
    private Set<String> permission;


    /**
     * This constructor can be safely used that wishes to create a
     * <code>CustomAuthenticationToken</code>
     * <p>
     * As the {@link #isAuthenticated()} will return <code>true</code>,
     * because we custom the token, add only use the permission-resources to auth
     *
     * @param principal   principal
     * @param credentials credentials
     * @param permission  permission
     */
    public CustomAuthenticationToken(Object principal, Object credentials, Set<String> permission) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.permission = permission;
        super.setAuthenticated(true);
    }

    /**
     * This constructor should only be used by <code>AuthenticationManager</code> or
     * <code>AuthenticationProvider</code> implementations that are satisfied with
     * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
     * authentication token.
     * <p>
     * we custom and permit it can use role or permission-resources to auth
     *
     * @param principal   principal
     * @param credentials credentials
     * @param permission  permission
     * @param authorities authorities
     */
    public CustomAuthenticationToken(Object principal, Object credentials, Set<String> permission,
                                     Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.permission = permission;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    public Set<String> getPermission() {
        return permission;
    }
}
