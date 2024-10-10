package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Reserva;
import com.loginingsoftware.loginapp.servicio.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/reservas")
public class ReservaController {

    @Autowired
    private ReservaServicio reservaServicio;

    // Listar todas las reservas
    @GetMapping
    public String listarReservas(Model model) {
        List<Reserva> reservas = reservaServicio.listarReservas();
        model.addAttribute("reservas", reservas);
        return "gestionReservas";  // Nombre de la vista
    }

    // Mostrar formulario para crear una nueva reserva
    @GetMapping("/nueva")
    public String mostrarFormularioReserva(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "nuevaReserva";  // Vista para crear una nueva reserva
    }

    // Guardar nueva reserva
    @PostMapping("/guardar")
    public String guardarReserva(@ModelAttribute("reserva") Reserva reserva) {
        reservaServicio.guardarReserva(reserva);
        return "redirect:/admin/reservas?creada";  // Redirigir después de crear la reserva
    }

    // Mostrar formulario para editar una reserva
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Reserva reserva = reservaServicio.obtenerReservaPorId(id);
        model.addAttribute("reserva", reserva);
        return "editarReserva";  // Vista para editar una reserva
    }

    // Guardar los cambios de una reserva (Actualización)
    @PostMapping("/editar")
    public String actualizarReserva(@ModelAttribute("reserva") Reserva reservaActualizada) {
        reservaServicio.actualizarReserva(reservaActualizada.getId(), reservaActualizada);
        return "redirect:/admin/reservas?actualizada";  // Redirigir después de actualizar
    }

    // Método para eliminar una reserva
    @GetMapping("/eliminar/{id}")
    public String eliminarReserva(@PathVariable("id") Long id) {
        reservaServicio.eliminarReserva(id);
        return "redirect:/admin/reservas?eliminada";
    }
}
