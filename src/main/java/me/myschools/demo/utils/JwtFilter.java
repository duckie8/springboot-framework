package me.myschools.demo.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.filter.GenericFilterBean;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // Change the req and res to HttpServletRequest and HttpServletResponse
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACES");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        String method = request.getMethod();
        String uri = request.getRequestURI();
        logger.info("URI:" + uri + ", method:" + method);

        // Get authorization from Http request
        final String authHeader = request.getHeader("token");

        // If the Http request is OPTIONS then just return the status code 200
        // which is HttpServletResponse.SC_OK in this code
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);

            chain.doFilter(req, res);
        }
        // Except OPTIONS, other request should be checked by JWT
        else {

            // Check the authorization, check if the token is started by "Bearer "
            if (authHeader == null) {
                throw new ExceptionUtil(ExceptionResultEnum.PARAMETER_VERIFICATION_ERROR);
            }

            // Then get the JWT token from authorization
            final String token = authHeader;

            try {
                // Use JWT parser to check if the signature is valid with the Key "secretkey"
                String secret = "DArti8tp6uNBRQDgHS8rnSkjsuCVizzNwEYdabvoK1r1sH2M";
                Key signingKey = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
                final Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
                // Add the claim to request header
                request.setAttribute("user", claims);
            }catch (ExceptionUtil e){
                throw e;
            }

            chain.doFilter(req, res);
        }
    }
}
