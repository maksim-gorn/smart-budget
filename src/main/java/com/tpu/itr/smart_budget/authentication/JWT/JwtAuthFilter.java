package com.tpu.itr.smart_budget.authentication.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    private final JWTService jwtService;

    public JwtAuthFilter(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException, JwtException {

        log.info("JWT FILTER: " + request.getMethod() + " " + request.getRequestURI());

        String path = request.getRequestURI();

        // пропускаем login и register БЕЗ проверки
        if (path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }


        try {
            // достаём Authorization header
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = authHeader.substring(7);

            // валидируем токен
            jwtService.validateToken(token);

            //извлекаем userId
            Long userId = jwtService.extractUserId(token);

            //ложим userId в SecurityContext
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,   //principal
                            null,
                            List.of()
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // рередаём запрос дальше
            filterChain.doFilter(request, response);

            // добавленна обработка ошибок
        } catch (JwtException ex) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");

            response.getWriter().write(
            """
            {
              "errorCode": "JWT_ERROR",
              "message": "%s"
            }
            """.formatted(ex.getMessage()));
        }
    }
}