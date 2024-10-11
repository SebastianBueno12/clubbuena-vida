package com.loginingsoftware.loginapp.seguridad;

import com.loginingsoftware.loginapp.modelo.Rol;
import com.loginingsoftware.loginapp.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleInitializer {

    @Autowired
    private RolRepositorio rolRepositorio;

    @PostConstruct
    public void init() {
        if (rolRepositorio.findByNombre("ROLE_ADMIN") == null) {
            rolRepositorio.save(new Rol("ROLE_ADMIN"));
        }
        if (rolRepositorio.findByNombre("ROLE_MESERO") == null) {
            rolRepositorio.save(new Rol("ROLE_MESERO"));
        }
        if (rolRepositorio.findByNombre("ROLE_CLIENTE") == null) {
            rolRepositorio.save(new Rol("ROLE_CLIENTE"));
        }
    }
}