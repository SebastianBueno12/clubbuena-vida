package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Habitacion;
import com.loginingsoftware.loginapp.servicio.HabitacionServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private HabitacionServicio habitacionServicio;

    @GetMapping("/cliente/panel")
    public String mostrarHabitacionesDisponibles(Model model) {
        List<Habitacion> habitaciones = habitacionServicio.obtenerHabitacionesDisponibles();
        model.addAttribute("habitaciones", habitaciones);
        return "clientePanel";  // Renderiza la vista del panel de cliente con las habitaciones
    }

}

