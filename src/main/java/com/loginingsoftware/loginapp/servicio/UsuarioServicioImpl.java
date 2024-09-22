package com.loginingsoftware.loginapp.servicio;


import java.util.*;
import java.util.stream.Collectors;

import com.loginingsoftware.loginapp.controlador.dto.UsuarioRegistroDTO;
import com.loginingsoftware.loginapp.modelo.Rol;
import com.loginingsoftware.loginapp.modelo.Usuario;
import com.loginingsoftware.loginapp.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioServicioImpl implements UsuarioServicio {


    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Map<String, String> tokenStorage = new HashMap<>();

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }
    @Override
    public Usuario findByCedula(String cedula) {
        return usuarioRepositorio.findByCedula(cedula);
    }

    @Override
    public void crearTokenDeRecuperacion(Usuario usuario, String token) {
        tokenStorage.put(token, usuario.getEmail());
    }

    @Override
    public Usuario findByTokenDeRecuperacion(String token) {
        String email = tokenStorage.get(token);
        if (email != null) {
            return usuarioRepositorio.findByEmail(email);
        }
        return null;
    }

    @Override
    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepositorio.save(usuario);
        tokenStorage.values().remove(usuario.getEmail()); // Elimina el token después de usarlo
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    @Override
    public void actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setEdad(usuarioActualizado.getEdad());
            usuario.setAltura(usuarioActualizado.getAltura());
            usuario.setPeso(usuarioActualizado.getPeso());
            usuario.setDescripcion(usuarioActualizado.getDescripcion());
            usuarioRepositorio.save(usuario);
        }
    }
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio) {
        super();
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        Usuario usuario = new Usuario(registroDTO.getNombre(),
                registroDTO.getApellido(),registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),registroDTO.getCedula(),Arrays.asList(new Rol("ROLE_USER")));
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if(usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }
        return new User(usuario.getEmail(),usuario.getPassword(), mapearAutoridadesRoles(usuario.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearAutoridadesRoles(Collection<Rol> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getNombre())).collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }
}