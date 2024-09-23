package com.loginingsoftware.loginapp.repositorio;

import com.loginingsoftware.loginapp.modelo.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepositorio extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
}
