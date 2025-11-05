package com.pragma.userservice.infraestructure.output.security.helper;

import com.pragma.userservice.domain.constants.DomainConstants;
import com.pragma.userservice.infraestructure.output.security.adapter.JwtServiceAdapter;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Set;


public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceAdapter jwtServiceAdapter;

    public CustomAuthenticationFilter(JwtServiceAdapter jwtServiceAdapter) {
        this.jwtServiceAdapter = jwtServiceAdapter;
    }

    private static final Set<String> PUBLIC_ROUTES = Set.of(
            "/api/public/**",         // Ruta de login
            "/swagger-ui/**",         // Rutas de Swagger UI
            "/v3/api-docs/**",        // Rutas de la especificación OpenAPI
            "/swagger-ui.html",       // Página principal de Swagger UI
            "/swagger-resources/**",  // Recursos de Swagger
            "/webjars/**"             // Recursos de WebJars
    );


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (isPublicRoute(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = extractToken(request);

        if (token != null) {
            try {
                // Valida el token
                Claims claims = jwtServiceAdapter.validateToken(token);

                // Verifica si el token ha expirado
                if (claims.getExpiration().before(new Date())) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token expirado");
                    return;
                }

                // Establece la autenticación en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(claims));
            } catch (Exception e) {
                // Si el token no es válido, devuelve un 403
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write("Token inválido");
                return;
            }
        }

        // Continúa con la cadena de filtros
        filterChain.doFilter(request, response);

    }

    private boolean isPublicRoute(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return PUBLIC_ROUTES.stream().anyMatch(route -> requestUri.matches(route.replace("**", ".*")));
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(DomainConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(DomainConstants.TOKEN_PREFIX.length());
        }
        return null;
    }



}
