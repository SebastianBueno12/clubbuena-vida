package com.loginingsoftware.loginapp.servicio;

import java.util.List;
import com.loginingsoftware.loginapp.modelo.Pasadia;

public interface PasadiaServicio {
    List<Pasadia> listarPasadias();
    Pasadia guardarPasadia(Pasadia pasadia);
    Pasadia obtenerPasadiaPorId(Long id);
    void eliminarPasadia(Long id);

    double obtenerIngresosTotalesPasadias();
}