package com.loginingsoftware.loginapp.controlador;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mesero")
public class MeseroController {
        @PreAuthorize("hasRole('ROLE_MESERO')")
        @GetMapping("/panel")
        public String mostrarPanelDeMesero() {
            return "meseroPanel";
        }
}
