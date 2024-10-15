package com.loginingsoftware.loginapp.servicio;



import java.util.List;
import com.loginingsoftware.loginapp.modelo.Implemento;

public interface ImplementoServicio {
    List<Implemento> listarImplementos();
    Implemento guardarImplemento(Implemento implemento);
    Implemento obtenerImplementoPorId(Long id);
    void eliminarImplemento(Long id);
}
