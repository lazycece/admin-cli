package com.lazycece.admin.server.auth.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lazycece.admin.common.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashSet;

/**
 * @author lazycece
 */
public class JwtTokenUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);

    private static final String SECRET = "e0e81f5a856d42ea9f834dc7dbc1609e";
    private static final long TOKEN_EXPIRE = 30 * 60 * 1000;
    private static final String ISSUER = "REUSABLE-ADMIN";

    /*custom claim*/

    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_PERMISSION = "permission";

    public static String generateToken(UserToken userToken) {
        if (userToken == null) {
            return null;
        }
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userToken.getUsername())
                    .withClaim(CLAIM_PERMISSION, JsonUtils.toJSONString(userToken.getPermission()))
                    .withClaim(CLAIM_ROLE, userToken.getRole())
                    .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRE))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (Exception e) {
            LOGGER.error("generate token error: {}", e.getMessage());
        }
        return token;
    }

    public static boolean verification(String token) {
        boolean verify = false;
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            verify = ISSUER.equals(jwt.getIssuer());
        } catch (Exception ignore) {
        }
        return verify;
    }

    public static UserToken parseToken(String token) {
        DecodedJWT jwt = JWT.decode(token);
        UserToken userToken = new UserToken();
        userToken.setUsername(jwt.getSubject());
        userToken.setPermission(new HashSet<>(JsonUtils.parseArray(jwt.getClaim(CLAIM_PERMISSION).asString(), String.class)));
        userToken.setRole(jwt.getClaim(CLAIM_ROLE).asString());
        return userToken;
    }
}
