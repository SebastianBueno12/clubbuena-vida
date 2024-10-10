package com.loginingsoftware.loginapp.servicio;

import com.loginingsoftware.loginapp.controlador.dto.UsuarioRegistroDTO;
import com.loginingsoftware.loginapp.modelo.Rol;
import com.loginingsoftware.loginapp.modelo.Usuario;
import com.loginingsoftware.loginapp.repositorio.RolRepositorio;
import com.loginingsoftware.loginapp.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void crearTokenDeRecuperacion(Usuario usuario, String token) {

    }

    @Override
    public Usuario findByTokenDeRecuperacion(String token) {
        return null;
    }

    @Override
    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {

    }
    // Nuevo método para contar el total de usuarios
    @Override
    public long contarUsuarios() {
        return usuarioRepositorio.count();
    }


    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    @Override
    public void actualizarUsuario(Long id, Usuario usuarioActualizado) {

    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        return null;
    }

    @Override
    @Transactional
    public void guardarUsuarioConRol(Usuario usuario, String nombreRol) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Obtener el rol de la base de datos
        Rol rol = rolRepositorio.findByNombre(nombreRol);
        if (rol == null) {
            throw new IllegalArgumentException("Rol no encontrado");
        }

        usuario.setRoles(new HashSet<>(Collections.singletonList(rol)));
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void actualizarUsuario(Long id, Usuario usuarioActualizado, String nombreRol) {
        Usuario usuario = obtenerUsuarioPorId(id);

        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());

        // Obtener y asignar el nuevo rol
        Rol rol = rolRepositorio.findByNombre(nombreRol);
        if (rol == null) {
            throw new IllegalArgumentException("Rol no encontrado");
        }
        usuario.setRoles(new HashSet<>(Collections.singletonList(rol)));

        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepositorio.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario o contraseña inválidos"));

        Set<Rol> roles = usuario.getRoles();
        Set<org.springframework.security.core.GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Rol rol : roles) {
            grantedAuthorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(rol.getNombre()));
        }

        return new User(usuario.getEmail(), usuario.getPassword(), grantedAuthorities);
    }

}
