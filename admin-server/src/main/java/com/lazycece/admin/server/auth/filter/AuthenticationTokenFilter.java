package com.lazycece.admin.server.auth.filter;

import com.lazycece.admin.common.reponse.ResponseCode;
import com.lazycece.admin.common.reponse.ResponseData;
import com.lazycece.admin.common.reponse.ResponseMsg;
import com.lazycece.admin.common.utils.JsonUtils;
import com.lazycece.admin.server.auth.custom.CustomAuthenticationToken;
import com.lazycece.admin.server.auth.token.JwtTokenUtils;
import com.lazycece.admin.server.auth.token.UserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author lazycece
 */
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String HEAD_FIELD_TOKEN = "ADMIN_TOKEN";
    private static Set<String> WHITE_URL;

    static {
        WHITE_URL = new HashSet<>();
        WHITE_URL.add("/u/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!authenticationToken(request)) {
            ResponseData responseData = ResponseData.fail(ResponseCode.INVALID_TOKEN, ResponseMsg.INVALID_TOKEN);
            response.getOutputStream().print(JsonUtils.toJSONString(responseData));
        } else {
            filterChain.doFilter(request, response);
        }
    }

    private boolean authenticationToken(HttpServletRequest request) {
        if (!WHITE_URL.contains(request.getRequestURI())) {
            //token验证机制过滤白名单
            String token = request.getHeader(HEAD_FIELD_TOKEN);
            if (token == null || !JwtTokenUtils.verification(token)) {
                return false;
            }
            UserToken userToken = JwtTokenUtils.parseToken(token);
            Authentication authentication = new CustomAuthenticationToken(userToken.getUsername(),
                    null, userToken.getPermission(), mappingRoles(userToken.getRole()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        return true;
    }

    private Collection<? extends GrantedAuthority> mappingRoles(String role) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        if (role != null) {
            //加ROLE_前缀是由于spring security验证权限时带有默认前缀
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return grantedAuthorityList;
    }
}
