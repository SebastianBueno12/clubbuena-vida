package com.loginingsoftware.loginapp.repositorio;

import com.loginingsoftware.loginapp.modelo.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCorreo(String correo);
}
