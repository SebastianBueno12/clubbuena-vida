package com.loginingsoftware.loginapp.controlador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @PreAuthorize("hasRole('ROLE_CLIENTE')")
    @GetMapping("/panel")
    public String mostrarPanelDeCliente() {
        return "clientePanel";  // Nombre de la vista para el panel de clientes
    }
}
