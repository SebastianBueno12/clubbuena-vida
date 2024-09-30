package com.loginingsoftware.loginapp.repositorio;

import com.loginingsoftware.loginapp.modelo.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HabitacionRepositorio extends JpaRepository<Habitacion, Long> {
    // Método para encontrar habitaciones que estén disponibles
    List<Habitacion> findByDisponible(boolean disponible);
}
