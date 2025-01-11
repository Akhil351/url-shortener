package org.Akhil.shortener.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Akhil.shortener.model.UserRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRequestContext context;
    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt=jwtUtils.getJwtFromHeader(request);
            if (jwt!=null && jwtUtils.validateToken(jwt)){
                String userName=jwtUtils.getUserNameFromJwtToken(jwt);
                UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
                if (userDetails!=null){
                    UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    setContext(userDetails);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        filterChain.doFilter(request,response);

    }
    private void setContext(UserDetails userDetails){
        context.setEmail(userDetails.getUsername());
        context.setAuthorities(userDetails.getAuthorities());
    }
}