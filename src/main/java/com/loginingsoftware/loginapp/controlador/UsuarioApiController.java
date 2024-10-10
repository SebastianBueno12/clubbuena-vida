package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UsuarioApiController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Método para contar usuarios
    @GetMapping("/contar")
    public ResponseEntity<Long> contarUsuarios() {
        long totalUsuarios = usuarioServicio.contarUsuarios();
        return new ResponseEntity<>(totalUsuarios, HttpStatus.OK);  // Devolver el conteo de usuarios como JSON
    }
}