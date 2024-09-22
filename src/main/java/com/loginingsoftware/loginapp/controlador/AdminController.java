package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Usuario;
import com.loginingsoftware.loginapp.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Inyección de UsuarioServicio
    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/actualizar/{id}")
    public String mostrarFormularioDeActualizacion(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
        model.addAttribute("usuario", usuario);
        return "actualizarUsuario";  // Nombre de la vista que muestra el formulario de actualización
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute("usuario") Usuario usuarioActualizado) {
        usuarioServicio.actualizarUsuario(id, usuarioActualizado);
        return "redirect:/?actualizado";  // Redirige después de actualizar
    }
}
