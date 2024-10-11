package com.loginingsoftware.loginapp.servicio;

import com.loginingsoftware.loginapp.modelo.EstadoReserva;
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
    public List<Reserva> obtenerReservasPorCorreo(String correo) {
        return reservaRepositorio.findByCorreo(correo);
    }

    public Reserva guardarReserva(Reserva reserva) {
        // Calcular el total del hospedaje según los días
        double totalHospedaje = reserva.getDiasHospedaje() * reserva.getPrecioPorNoche(); // Precio por día (puedes cambiarlo)
        reserva.setTotalHospedaje(totalHospedaje);

        // Calcular el pago inicial (30%)
        reserva.setPagoInicial(totalHospedaje * 0.30);
        reserva.setPagoRestante(totalHospedaje * 0.70);

        // Estado inicial
        reserva.setEstadoReserva(EstadoReserva.PENDIENTE_PAGO_INICIAL);

        return reservaRepositorio.save(reserva);
    }

    // Obtener una reserva por su ID
    public Reserva obtenerReservaPorId(Long id) {
        return reservaRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
    }

    public void actualizarReserva(Long id, Reserva reservaActualizada) {
        Reserva reserva = obtenerReservaPorId(id);
        reserva.setNombre(reservaActualizada.getNombre());
        reserva.setNumeroDocumento(reservaActualizada.getNumeroDocumento());
        reserva.setCorreo(reservaActualizada.getCorreo());
        reserva.setAdultos(reservaActualizada.getAdultos());
        reserva.setNinos(reservaActualizada.getNinos());
        reserva.setDiasHospedaje(reservaActualizada.getDiasHospedaje());
        reserva.setPrecioPorNoche(reservaActualizada.getPrecioPorNoche());

        // Recalcular el total
        double totalHospedaje = reservaActualizada.getDiasHospedaje() * reservaActualizada.getPrecioPorNoche();
        reserva.setTotalHospedaje(totalHospedaje);
        reserva.setPagoInicial(totalHospedaje * 0.30);
        reserva.setPagoRestante(totalHospedaje * 0.70);

        reservaRepositorio.save(reserva);
    }

    // Eliminar una reserva
    public void eliminarReserva(Long id) {
        Reserva reserva = obtenerReservaPorId(id);  // Verificamos que la reserva exista antes de eliminar
        if (reserva != null) {
            reservaRepositorio.deleteById(id);
        }
    }
}
