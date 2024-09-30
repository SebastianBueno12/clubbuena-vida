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

    // Obtener todas las habitaciones disponibles
    public List<Habitacion> obtenerHabitacionesDisponibles() {
        // Retornar solo habitaciones disponibles (disponible = true)
        return habitacionRepositorio.findByDisponible(true);
    }

    // Obtener una habitación por su ID
    public Habitacion obtenerHabitacionPorId(Long id) {
        // Retornar la habitación si existe, o null si no
        return habitacionRepositorio.findById(id).orElse(null);
    }

    // Guardar o actualizar una habitación
    public Habitacion guardarHabitacion(Habitacion habitacion) {
        return habitacionRepositorio.save(habitacion);
    }

    // Eliminar una habitación por su ID
    public void eliminarHabitacion(Long id) {
        habitacionRepositorio.deleteById(id);
    }
}
