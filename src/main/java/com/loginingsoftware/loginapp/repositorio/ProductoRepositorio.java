package com.loginingsoftware.loginapp.repositorio;

import com.loginingsoftware.loginapp.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto, Long> {
}
