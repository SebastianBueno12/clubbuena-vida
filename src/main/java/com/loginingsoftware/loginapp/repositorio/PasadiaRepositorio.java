package com.loginingsoftware.loginapp.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import com.loginingsoftware.loginapp.modelo.Pasadia;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PasadiaRepositorio extends JpaRepository<Pasadia, Long> {
    // Query para obtener ingresos totales por pasadías
    @Query("SELECT SUM(p.pagoTotal) FROM Pasadia p WHERE p.estadoPago = 'Pagado'")
    double obtenerIngresosTotalesPasadias();
    List<Pasadia> findByEstadoPago(String estadoPago);
}
