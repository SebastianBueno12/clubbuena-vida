package com.loginingsoftware.loginapp.servicio;



import java.util.List;

import com.loginingsoftware.loginapp.repositorio.ImplementoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loginingsoftware.loginapp.modelo.Implemento;


@Service
public class ImplementoServicioImpl implements ImplementoServicio {

    @Autowired
    private ImplementoRepositorio implementoRepositorio;

    @Override
    public List<Implemento> listarImplementos() {
        return implementoRepositorio.findAll();
    }

    @Override
    public Implemento guardarImplemento(Implemento implemento) {
        return implementoRepositorio.save(implemento);
    }

    @Override
    public Implemento obtenerImplementoPorId(Long id) {
        return implementoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminarImplemento(Long id) {
        implementoRepositorio.deleteById(id);
    }
}
