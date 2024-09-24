package com.loginingsoftware.loginapp.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Obtener los roles del usuario autenticado
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Redirigir según el rol del usuario
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/panel");  // Redirigir al panel de admin
                return;
            }
        }
        // Redirigir a la página de inicio por defecto para otros roles
        response.sendRedirect("/");
    }
}