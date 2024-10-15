package com.loginingsoftware.loginapp.repositorio;

import com.loginingsoftware.loginapp.modelo.EstadoReserva;
import com.loginingsoftware.loginapp.modelo.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCorreo(String correo);
    List<Reserva> findByCorreoAndEstadoReserva(String correo, EstadoReserva estadoReserva);
    // Query para obtener ingresos totales por reservas
    @Query("SELECT SUM(r.totalHospedaje) FROM Reserva r WHERE r.estadoReserva = 'PAGO_COMPLETADO'")
    double obtenerIngresosTotalesReservas();

    List<Reserva> findByEstadoReserva(EstadoReserva estadoReserva);
}

