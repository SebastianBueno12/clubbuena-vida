package com.loginingsoftware.loginapp.repositorio;



import com.loginingsoftware.loginapp.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    public Usuario findByEmail(String email);
    Usuario findByCedula(String cedula);

}