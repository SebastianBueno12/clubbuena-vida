package com.loginingsoftware.loginapp.servicio;






import com.loginingsoftware.loginapp.controlador.dto.UsuarioRegistroDTO;
import com.loginingsoftware.loginapp.modelo.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UsuarioServicio extends UserDetailsService{

    Usuario findByEmail(String email);
    void crearTokenDeRecuperacion(Usuario usuario, String token);
    Usuario findByTokenDeRecuperacion(String token);
    void actualizarContrasena(Usuario usuario, String nuevaContrasena);
    Usuario obtenerUsuarioPorId(Long id);

    void actualizarUsuario(Long id, Usuario usuarioActualizado);

    public Usuario guardar(UsuarioRegistroDTO registroDTO);

    public List<Usuario> listarUsuarios();
    Usuario findByCedula(String cedula);

}