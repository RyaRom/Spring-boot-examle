package com.TodoApp.Filters;

import com.TodoApp.Utils.JWTUtil;
import com.TodoApp.model.UserAuthDetails;
import com.TodoApp.service.UserAuthDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {
    private final UserAuthDetailsService userAuthDetailsService;
    private final JWTUtil jwtUtil;

    public JWTRequestFilter(UserAuthDetailsService userAuthDetailsService, JWTUtil jwtUtil) {
        this.userAuthDetailsService = userAuthDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");
        String username= null;
        String jwt = null;
        if (header!=null && header.startsWith("Bearer ")){
            jwt = header.substring(7);
            username= jwtUtil.extractUsername(jwt);
        }
        if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserAuthDetails userAuthDetails = userAuthDetailsService.loadUserByUsername(username);
            if  (jwtUtil.validateToken(jwt, userAuthDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken(userAuthDetails, null, userAuthDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
