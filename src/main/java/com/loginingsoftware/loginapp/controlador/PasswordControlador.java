package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Usuario;
import com.loginingsoftware.loginapp.servicio.EmailServicio;
import com.loginingsoftware.loginapp.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class PasswordControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private EmailServicio emailServicio;

    @GetMapping("/recuperarContrasena")
    public String mostrarFormularioRecuperacion() {
        return "recuperarContrasena"; // Vista de recuperación de contraseña
    }

    @PostMapping("/recuperarContrasena")
    public String procesarRecuperacion(@RequestParam("correo") String correo, HttpServletRequest request, Model model) {
        Usuario usuario = usuarioServicio.findByEmail(correo);
        if (usuario == null) {
            model.addAttribute("error", "No existe una cuenta asociada a ese correo.");
            return "recuperarContrasena";
        }

        String token = UUID.randomUUID().toString();
        usuarioServicio.crearTokenDeRecuperacion(usuario, token);

        String url = request.getRequestURL().toString().replace(request.getServletPath(), "") + "/restablecerContrasena?token=" + token;

        emailServicio.enviarCorreo(correo, "Recuperación de Contraseña", "Para restablecer su contraseña, haga clic en el siguiente enlace: " + url);

        model.addAttribute("mensaje", "Se ha enviado un enlace de recuperación a su correo.");
        return "recuperarContrasena";
    }

    @GetMapping("/restablecerContrasena")
    public String mostrarFormularioRestablecimiento(@RequestParam("token") String token, Model model) {
        Usuario usuario = usuarioServicio.findByTokenDeRecuperacion(token);
        if (usuario == null) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "restablecerContrasena";
        }
        model.addAttribute("token", token);
        return "restablecerContrasena"; // Vista de restablecimiento de contraseña
    }

    @PostMapping("/restablecerContrasena")
    public String procesarRestablecimiento(@RequestParam("token") String token,
                                           @RequestParam("clave") String clave,
                                           @RequestParam("confirmarClave") String confirmarClave,
                                           RedirectAttributes redirectAttributes,
                                           Model model) {
        if (!clave.equals(confirmarClave)) {
            model.addAttribute("error", "Las contraseñas no coinciden.");
            return "restablecerContrasena";
        }

        Usuario usuario = usuarioServicio.findByTokenDeRecuperacion(token);
        if (usuario == null) {
            model.addAttribute("error", "Token inválido o expirado.");
            return "restablecerContrasena";
        }

        usuarioServicio.actualizarContrasena(usuario, clave);

        redirectAttributes.addFlashAttribute("mensaje", "Su contraseña ha sido restablecida con éxito.");
        return "redirect:/inicioSesion?restablecimientoExitoso";
    }
    }