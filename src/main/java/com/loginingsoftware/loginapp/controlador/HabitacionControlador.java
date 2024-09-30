package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Habitacion;
import com.loginingsoftware.loginapp.servicio.HabitacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/habitacion")
public class HabitacionControlador {

    @Autowired
    private HabitacionServicio habitacionServicio;

    @GetMapping("/detalles/{id}")
    public String mostrarDetallesHabitacion(@PathVariable Long id, Model model) {
        // Obtener la habitación por su ID
        Habitacion habitacion = habitacionServicio.obtenerHabitacionPorId(id);

        // Validar si la habitación existe
        if (habitacion != null) {
            model.addAttribute("habitacion", habitacion);
            return "detallesHabitacion";  // Nombre de la nueva vista de detalles
        } else {
            // Si no existe, redirigir a una página de error o de habitaciones
            return "redirect:/cliente/panel";
        }
    }
}
