package com.loginingsoftware.loginapp.servicio;

import java.util.*;
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

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private RolRepositorio rolRepositorio;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Mapa temporal para almacenar tokens de recuperación de contraseñas
    private Map<String, String> tokenStorage = new HashMap<>();

    // Guardar un usuario nuevo con el rol asignado
    @Override
    @Transactional
    public Usuario guardar(UsuarioRegistroDTO registroDTO) {
        // Crear un nuevo usuario
        Usuario usuario = new Usuario(
                registroDTO.getNombre(),
                registroDTO.getApellido(),
                registroDTO.getEmail(),
                passwordEncoder.encode(registroDTO.getPassword()),
                registroDTO.getEdad()
        );

        // Asignar rol por defecto "ROLE_CLIENTE"
        Rol rolCliente = rolRepositorio.findByNombre("ROLE_CLIENTE");
        usuario.setRoles(new HashSet<>(Arrays.asList(rolCliente)));

        // Guardar usuario en la base de datos
        return usuarioRepositorio.save(usuario);
    }

    // Cargar detalles de usuario al iniciar sesión
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepositorio.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario o password inválidos");
        }

        // Obtener los roles del usuario
        Set<Rol> roles = usuario.getRoles();
        Set<org.springframework.security.core.GrantedAuthority> grantedAuthorities = new HashSet<>();

        // Convertir roles a GrantedAuthority
        for (Rol rol : roles) {
            grantedAuthorities.add(new org.springframework.security.core.authority.SimpleGrantedAuthority(rol.getNombre()));
        }

        // Crear un UserDetails con las autoridades correspondientes
        return new User(usuario.getEmail(), usuario.getPassword(), grantedAuthorities);
    }

    // Buscar un usuario por email
    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepositorio.findByEmail(email);
    }

    // Crear token de recuperación de contraseña
    @Override
    public void crearTokenDeRecuperacion(Usuario usuario, String token) {
        tokenStorage.put(token, usuario.getEmail());
    }

    // Buscar un usuario por token de recuperación
    @Override
    public Usuario findByTokenDeRecuperacion(String token) {
        String email = tokenStorage.get(token);
        if (email != null) {
            return usuarioRepositorio.findByEmail(email);
        }
        return null;
    }

    // Actualizar la contraseña de un usuario
    @Override
    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuarioRepositorio.save(usuario);
        tokenStorage.values().remove(usuario.getEmail()); // Elimina el token después de usarlo
    }

    // Obtener un usuario por su ID
    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepositorio.findById(id).orElse(null);
    }

    // Actualizar los datos de un usuario existente
    @Override
    public void actualizarUsuario(Long id, Usuario usuarioActualizado) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setEdad(usuarioActualizado.getEdad());
            usuarioRepositorio.save(usuario);
        }
    }

    // Listar todos los usuarios
    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepositorio.findAll();
    }

    // Método para asignar un rol a un usuario existente
    @Transactional
    public void asignarRol(Long idUsuario, String nombreRol) {
        Usuario usuario = usuarioRepositorio.findById(idUsuario).orElse(null);
        if (usuario != null) {
            Rol rol = rolRepositorio.findByNombre(nombreRol);
            if (rol != null) {
                usuario.getRoles().add(rol);
                usuarioRepositorio.save(usuario);
            }
        }
    }
}
