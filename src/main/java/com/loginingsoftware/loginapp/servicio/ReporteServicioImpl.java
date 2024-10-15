package com.loginingsoftware.loginapp.servicio;


import com.loginingsoftware.loginapp.repositorio.ReservaRepositorio;
import com.loginingsoftware.loginapp.repositorio.PasadiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loginingsoftware.loginapp.servicio.ReporteServicio;

@Service
public class ReporteServicioImpl implements ReporteServicio {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Autowired
    private PasadiaRepositorio pasadiaRepositorio;

    // Método para obtener ingresos por reservas
    @Override
    public double obtenerIngresosPorReservas() {
        return reservaRepositorio.obtenerIngresosTotalesReservas();
    }


    // Método para obtener ingresos por pasadías
    @Override
    public double obtenerIngresosPorPasadias() {
        return pasadiaRepositorio.obtenerIngresosTotalesPasadias();
    }
}
