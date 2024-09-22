package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Usuario;
import com.loginingsoftware.loginapp.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Controller
public class RegistroControlador {

    @Autowired
    private UsuarioServicio servicio;

    @GetMapping("/login")
    public String iniciarSesion() {
        return "login";
    }

    @GetMapping("/")
    public String verPaginaDeInicio(Model modelo) {
        modelo.addAttribute("usuarios", servicio.listarUsuarios());
        return "index";
    }
    @GetMapping("/buscar")
    public String buscarPorCedula(@RequestParam("cedula") String cedula, Model modelo) {
        Usuario usuario = servicio.findByCedula(cedula);
        if (usuario != null) {
            modelo.addAttribute("usuarios", Arrays.asList(usuario)); // Muestra solo el usuario encontrado
        } else {
            modelo.addAttribute("usuarios", Collections.emptyList()); // Muestra una lista vacía si no se encuentra nada
        }
        return "index"; // Retorna la misma vista con los resultados filtrados
    }
}