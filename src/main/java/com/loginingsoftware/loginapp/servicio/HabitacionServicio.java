package com.loginingsoftware.loginapp.servicio;

import com.loginingsoftware.loginapp.modelo.Habitacion;
import com.loginingsoftware.loginapp.repositorio.HabitacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitacionServicio {

    @Autowired
    private HabitacionRepositorio habitacionRepositorio;

    public List<Habitacion> listarHabitaciones() {
        return habitacionRepositorio.findAll();
    }

    public Habitacion obtenerHabitacionPorId(Long id) {
        return habitacionRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada"));
    }

    public Habitacion guardarHabitacion(Habitacion habitacion) {
        return habitacionRepositorio.save(habitacion);
    }

    public void eliminarHabitacion(Long id) {
        habitacionRepositorio.deleteById(id);
    }

    public void actualizarEstado(Long id, String nuevoEstado) {
        Habitacion habitacion = obtenerHabitacionPorId(id);
        habitacion.setEstado(nuevoEstado);
        habitacionRepositorio.save(habitacion);
    }

    // Actualizar habitación
    public void actualizarHabitacion(Habitacion habitacion) {
        habitacionRepositorio.save(habitacion);
    }
}
