package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Rol;
import com.loginingsoftware.loginapp.modelo.Usuario;

import com.loginingsoftware.loginapp.servicio.RolServicio;
import com.loginingsoftware.loginapp.servicio.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class GestionUsuariosController {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private RolServicio rolServicio;

    // Listar todos los usuarios
    @GetMapping
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        model.addAttribute("usuarios", usuarios);
        return "gestionUsuarios";  // Nombre de la vista
    }

    // Mostrar formulario para crear un nuevo usuario
    @GetMapping("/nuevo")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        List<Rol> roles = rolServicio.listarRoles();
        model.addAttribute("roles", roles);
        return "nuevoUsuario";  // Vista para crear un nuevo usuario
    }

    // Guardar un nuevo usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario, @RequestParam("rol") String rol) {
        usuarioServicio.guardarUsuarioConRol(usuario, rol);
        return "redirect:/admin/usuarios?creado";  // Redirigir después de crear
    }

    // Mostrar formulario para editar un usuario
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioServicio.obtenerUsuarioPorId(id);
        List<Rol> roles = rolServicio.listarRoles();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", roles);
        return "editarUsuario";  // Vista para editar el usuario
    }

    // Guardar los cambios de un usuario
    @PostMapping("/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute("usuario") Usuario usuarioActualizado, @RequestParam("rol") String rol) {
        usuarioServicio.actualizarUsuario(id, usuarioActualizado, rol);
        return "redirect:/admin/usuarios?actualizado";  // Redirigir después de actualizar
    }


    // Método para eliminar un usuario
    @PostMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id) {
        usuarioServicio.eliminarUsuario(id);
        return "redirect:/admin/usuarios?eliminado";
    }
    @GetMapping("/panel")
    public String mostrarPanelDeAdministracion(Model model) {
        long totalUsuarios = usuarioServicio.contarUsuarios();  // Obtiene el número de usuarios
        model.addAttribute("totalUsuarios", totalUsuarios);  // Lo envía a la vista
        return "adminPanel";  // Renderiza la vista del panel de administración
    }

}
