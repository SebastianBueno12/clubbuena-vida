package com.loginingsoftware.loginapp.servicio;

import com.loginingsoftware.loginapp.modelo.Rol;
import com.loginingsoftware.loginapp.repositorio.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolServicio {

    @Autowired
    private RolRepositorio rolRepositorio;

    public List<Rol> listarRoles() {
        return rolRepositorio.findAll();
    }
}
