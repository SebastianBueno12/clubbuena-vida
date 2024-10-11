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
        return usuarioRepositorio.findByEmail(email);
    }

    @Override
    public void crearTokenDeRecuperacion(Usuario usuario, String token) {
        usuario.setTokenRestablecimiento(token);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public Usuario findByTokenDeRecuperacion(String token) {
        return usuarioRepositorio.findByTokenRestablecimiento(token)
                .orElseThrow(() -> new IllegalArgumentException("Token no encontrado"));
    }

    @Override
    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepositorio.save(usuario);
    }

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
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setNombre(usuarioActualizado.getNombre());
        usuario.setApellido(usuarioActualizado.getApellido());
        usuario.setEmail(usuarioActualizado.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioActualizado.getPassword()));
        usuarioRepositorio.save(usuario);
    }

    @Override
    @Transactional
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setApellido(registroDTO.getApellido());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

        // Asignación del rol por defecto (puedes cambiar esto si necesitas otro rol)
        Rol rol = rolRepositorio.findByNombre("ROLE_CLIENTE");
        if (rol == null) {
            throw new IllegalArgumentException("Rol no encontrado");
        }
        usuario.setRoles(new HashSet<>(Collections.singletonList(rol)));

        return usuarioRepositorio.save(usuario); // Guardar el usuario en la base de datos
    }

    @Override
    @Transactional
    public void guardarUsuarioConRol(Usuario usuario, String nombreRol) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Rol rol = rolRepositorio.findByNombre(nombreRol);
        if (rol == null) {
            throw new IllegalArgumentException("Rol no encontrado");
        }

        usuario.setRoles(new HashSet<>(Collections.singletonList(rol)));
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void actualizarUsuario(Long id, Usuario usuarioActualizado, String nombreRol) {

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
