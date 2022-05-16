package com.cyj.rbacalpha.filter;

import com.cyj.rbacalpha.exception.AuthenticationFailureException;
import com.cyj.rbacalpha.model.LoginUser;
import com.cyj.rbacalpha.util.JwtUtil;
import com.cyj.rbacalpha.util.RedisUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(HEADER);
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith(PREFIX)) {
            jwt = authorizationHeader.substring(7);
        }

        if (jwt != null) {
            boolean pass;
            try {
                pass = jwtUtil.validateToken(jwt);
            } catch (Exception ex) {
                throw new AuthenticationFailureException("Please Re-Login again");
            }
            if (!pass) {
                throw new AuthenticationFailureException("Please Re-Login again");
            }
            String username = jwtUtil.extractUsername(jwt);
            LoginUser loginUser = redisUtil.get("login:" + username, LoginUser.class);
            if (loginUser == null) {
                throw new AuthenticationFailureException("Please Re-Login again");
            }
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }

        filterChain.doFilter(request, response);
    }
}
