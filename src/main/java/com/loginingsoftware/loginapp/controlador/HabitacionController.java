package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Habitacion;
import com.loginingsoftware.loginapp.modelo.Reserva;
import com.loginingsoftware.loginapp.servicio.HabitacionServicio;
import com.loginingsoftware.loginapp.servicio.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionServicio habitacionServicio;
    @Autowired
    private ReservaServicio reservaServicio;

    // Mostrar todas las habitaciones
    @GetMapping
    public String listarHabitaciones(Model model) {
        List<Habitacion> habitaciones = habitacionServicio.listarHabitaciones();
        model.addAttribute("habitaciones", habitaciones);
        return "adminHabitaciones";
    }

    // Mostrar formulario para asignar una habitación a una reserva
    @GetMapping("/asignar/{id}")
    public String mostrarFormularioAsignar(@PathVariable Long id, Model model) {
        Habitacion habitacion = habitacionServicio.obtenerHabitacionPorId(id);
        List<Reserva> reservas = reservaServicio.listarReservas(); // Obtener todas las reservas
        model.addAttribute("habitacion", habitacion);
        model.addAttribute("reservas", reservas);
        return "asignarHabitacion";
    }


    @PostMapping("/asignar")
    public String asignarHabitacion(@RequestParam("habitacionId") Long habitacionId, @RequestParam("reservaId") Long reservaId) {
        Habitacion habitacion = habitacionServicio.obtenerHabitacionPorId(habitacionId);
        Reserva reserva = reservaServicio.obtenerReservaPorId(reservaId);

        // Asignar la habitación a la reserva
        reserva.setHabitacion(habitacion);
        reservaServicio.actualizarReserva(reserva.getId(), reserva);

        habitacion.setEstado("Ocupada");
        habitacionServicio.actualizarHabitacion(habitacion);

        return "redirect:/admin/habitaciones";
    }


    // Cambiar el estado de la habitación
    @PostMapping("/cambiarEstado/{id}")
    public String cambiarEstado(@PathVariable Long id, @RequestParam("estado") String estado) {
        Habitacion habitacion = habitacionServicio.obtenerHabitacionPorId(id);
        habitacion.setEstado(estado);
        habitacionServicio.actualizarHabitacion(habitacion);
        return "redirect:/admin/habitaciones";
    }
    // Muestra el formulario para agregar una nueva habitación
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaHabitacion(Model model) {
        model.addAttribute("habitacion", new Habitacion());
        return "nuevaHabitacion";  // Esta será la vista del formulario
    }

    // Maneja el POST para guardar la nueva habitación
    @PostMapping("/guardar")
    public String guardarHabitacion(@ModelAttribute Habitacion habitacion) {
        habitacionServicio.guardarHabitacion(habitacion);
        return "redirect:/admin/habitaciones";
    }
}
