package com.irctc.config.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationHelper extends OncePerRequestFilter {

    private  JwtHelper jwtHelper;

    private UserDetailsService userDetailsService;
    public JwtAuthenticationHelper(JwtHelper jwtHelper, UserDetailsService userDetailsService) {
        this.jwtHelper = jwtHelper;
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader=request.getHeader("Authorization");
        String username=null;
        String token=null;

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
           try {
               token=authorizationHeader.substring(7);
               username=jwtHelper.getUsernameFromToken(token);

               if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails= userDetailsService.loadUserByUsername(username);

                if(jwtHelper.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                }
               }
           }catch(IllegalArgumentException e){
               System.out.println("Unable to get JWT Token");
               e.printStackTrace();
           }
           catch(ExpiredJwtException e){
                System.out.println("JWT Token has expired");
                e.printStackTrace();
           }
           catch(MalformedJwtException e){
                System.out.println("Invalid JWT Token");
                e.printStackTrace();
           }
           catch (Exception e) {
               System.out.println("Invalid Authorization Token");
               e.printStackTrace();
           }

        }
        else{
            System.out.println("Invalid Authorization Token");
        }

        filterChain.doFilter(request,response);
    }
}
