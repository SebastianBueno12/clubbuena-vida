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
            } else if (authority.getAuthority().equals("ROLE_CLIENTE")) {
                response.sendRedirect("/cliente/panel");  // Redirigir al panel de cliente (o la página que prefieras)
                return;
            }
        }

        // Si el usuario tiene otro rol o no se encuentra en los anteriores
        response.sendRedirect("/defaultPage");  // Redirigir a una página por defecto si no se encuentra el rol
    }
}
