package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.EstadoReserva;
import com.loginingsoftware.loginapp.modelo.Reserva;
import com.loginingsoftware.loginapp.servicio.ReservaServicio;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClienteController {

    @Autowired
    private ReservaServicio reservaServicio;

    // Este método muestra el panel del cliente cuando accede con su rol
    @GetMapping("/cliente/panel")
    public String mostrarPanelCliente(Model model) {
        model.addAttribute("mensaje", "Bienvenido al panel de cliente");
        return "clientePanel";  // Retorna la vista clientePanel.html
    }

    // Método para mostrar las reservas del cliente autenticado
    @GetMapping("/cliente/reservas")
    public String verReservasDelCliente(Model model) {
        // Obtener el email del cliente autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailCliente = ((UserDetails) authentication.getPrincipal()).getUsername();

        // Obtener las reservas asociadas a ese cliente
        List<Reserva> reservas = reservaServicio.obtenerReservasPorCorreo(emailCliente);

        // Pasar las reservas al modelo para la vista
        model.addAttribute("reservas", reservas);
        return "clienteReservas";  // Retorna la vista con las reservas del cliente
    }

    // Mostrar formulario de pago
    @GetMapping("/cliente/reserva/pagar/{id}")
    public String mostrarFormularioPago(@PathVariable Long id, Model model) {
        // Obtener la reserva por su ID
        Reserva reserva = reservaServicio.obtenerReservaPorId(id);
        model.addAttribute("reserva", reserva);
        return "pagoReserva";  // Retorna la vista del formulario de pago
    }

    // Procesar el pago
    @PostMapping("/cliente/reserva/procesarPago/{id}")
    public String procesarPagoReserva(@PathVariable Long id, @RequestParam("pagoSeleccionado") double pagoSeleccionado, Model model) {
        // Obtener la reserva por su ID
        Reserva reserva = reservaServicio.obtenerReservaPorId(id);

        // Simulamos el pago
        if (pagoSeleccionado == reserva.getPagoInicial()) {
            // Si paga el 30%
            reserva.setPagoInicial(0.0);  // El pago inicial ya no está pendiente
            reserva.setEstadoReserva(EstadoReserva.PAGO_PARCIAL);  // Cambia el estado a PAGO_PARCIAL
        } else if (pagoSeleccionado == reserva.getPagoRestante()) {
            // Si paga el 70%
            reserva.setPagoRestante(0.0);  // El pago restante ya no está pendiente
            reserva.setEstadoReserva(EstadoReserva.PAGO_COMPLETADO);  // Cambia el estado a PAGO_COMPLETADO
        }

        // Guardar la reserva actualizada
        reservaServicio.actualizarReserva(id, reserva);

        // Redirigir a la vista de reservas con un mensaje de éxito
        model.addAttribute("mensaje", "Pago completado con éxito");
        return "redirect:/cliente/reservas";  // Redirigir a la vista de reservas
    }
}
