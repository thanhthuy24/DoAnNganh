package com.htt.ecourse.filters;

import com.htt.ecourse.components.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain) throws ServletException, IOException {
        if(isBypassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7);
            final String username = jwtTokenUtil.extractUsername(token);
        }
    }

//    tách riêng những api không cần đăng nhập vẫn xem được.
    private boolean isBypassToken(@NotNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of("/api/courses", "GET"),
                Pair.of("/api/categories", "GET"),
                Pair.of("/api/users/register", "POST"),
                Pair.of("/api/users/login", "POST")
        );

        for(Pair<String, String> bypassToken : bypassTokens) {
            if(request.getServletPath().contains(bypassToken.getFirst()) &&
                request.getMethod().equals(bypassToken.getSecond())) {
                    return true;
            }
        }

        return false;
    }
}
