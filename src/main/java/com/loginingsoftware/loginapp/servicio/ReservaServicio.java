package com.loginingsoftware.loginapp.servicio;

import com.loginingsoftware.loginapp.modelo.Reserva;
import com.loginingsoftware.loginapp.repositorio.ReservaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    // Listar todas las reservas
    public List<Reserva> listarReservas() {
        return reservaRepositorio.findAll();
    }

    // Guardar nueva reserva
    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepositorio.save(reserva);
    }

    // Obtener una reserva por su ID
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    }

    // Actualizar una reserva
    public void actualizarReserva(Long id, Reserva reservaActualizada) {
        Reserva reserva = obtenerReservaPorId(id);  // Verificamos que la reserva exista
        if (reserva != null) {
            reserva.setNombre(reservaActualizada.getNombre());
            reserva.setNumeroDocumento(reservaActualizada.getNumeroDocumento());
            reserva.setCorreo(reservaActualizada.getCorreo());
            reserva.setAdultos(reservaActualizada.getAdultos());
            reserva.setNinos(reservaActualizada.getNinos());
            reserva.setDiasHospedaje(reservaActualizada.getDiasHospedaje());
            reservaRepositorio.save(reserva);  // Guardamos la reserva actualizada
        }
    }

    // Eliminar una reserva
    public void eliminarReserva(Long id) {
        Reserva reserva = obtenerReservaPorId(id);  // Verificamos que la reserva exista antes de eliminar
        if (reserva != null) {
            reservaRepositorio.deleteById(id);
        }
    }
}
