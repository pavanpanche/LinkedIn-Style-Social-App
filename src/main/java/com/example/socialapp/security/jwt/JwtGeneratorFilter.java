package com.example.socialapp.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtGeneratorFilter extends OncePerRequestFilter {

    private Object keys;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            String jwt = generateToken(authentication);
            response.setHeader(SecurityConstant.JWT_HEADER, "Bearer " + jwt);
        }
        filterChain.doFilter(request, response);
    }
    public String generateToken(Authentication authentication) {
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstant.JWT_KEY.getBytes());

        String jwt = Jwts.builder()
                .setIssuer("Avdhesh")
                .claim("username", authentication.getName())
                .claim("roles", getRole(authentication.getAuthorities()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 30000000))
                .signWith(key)
                .compact();

        return jwt;
    }

    private List<String> getRole(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }



}
