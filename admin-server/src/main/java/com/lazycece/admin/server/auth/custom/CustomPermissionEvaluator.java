package com.lazycece.admin.server.auth.custom;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Set;

/**
 * @author lazycece
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

    /**
     * we custom it for checking user's operator is be granted or not
     * <p>
     * note: there only check the permission-resource ,and the targetDomainObject
     * not be checked.
     *
     * @param authentication     represents the user in question. Should not be null.
     * @param targetDomainObject the domain object. the detail can view ${@link PermissionEvaluator}
     * @param permission         user's permission. Not null.
     * @return true if the permission is granted, false otherwise
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication instanceof CustomAuthenticationToken) {
            Set<String> permissionSet = ((CustomAuthenticationToken) authentication).getPermission();
            return permission != null && permissionSet.contains(String.valueOf(permission));
        }
        return false;
    }

    /**
     * Alternative method for evaluating a permission where only the identifier of the
     * target object is available, rather than the target instance itself.
     * <p>
     * And there ,it return true default(it will be define in the future if necessary)
     * <p>
     * the parameter's detail should see ${@link PermissionEvaluator}
     *
     * @param authentication authentication
     * @param targetId       targetId
     * @param targetType     targetType
     * @param permission     permission
     * @return true
     */
    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return true;
    }
}
