package com.thecodinginterface.tcilol.security;

import com.thecodinginterface.tcilol.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Value("${tcilol.jwt.secret}")
    private String jwtSecret;

    @Value("${tcilol.jwt.expiration.ms}")
    private int jwtExpirationMs;

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var headerToken = JwtUtils.parseJwtFromHeader(request.getHeader("Authorization"));
            var username = JwtUtils.parseUserNameFromJwtToken(jwtSecret, headerToken);
            var userDetails = userDetailsService.loadUserByUsername(username);

            var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch(Exception e) {
            logger.warn("Cannot set authentication {}", e);
        }

        filterChain.doFilter(request, response);
    }
}
