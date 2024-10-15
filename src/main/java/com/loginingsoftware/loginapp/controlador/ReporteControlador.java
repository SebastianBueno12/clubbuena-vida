package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.servicio.PasadiaServicio;
import com.loginingsoftware.loginapp.servicio.ReservaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ReporteControlador {

    @Autowired
    private ReservaServicio reservaServicio;

    @Autowired
    private PasadiaServicio pasadiaServicio;

    @GetMapping("/admin/reportes")
    public String mostrarReportes(Model model) {
        // Obtener los ingresos desde los servicios
        double ingresosReservas = reservaServicio.obtenerIngresosTotalesReservas();
        double ingresosPasadias = pasadiaServicio.obtenerIngresosTotalesPasadias();

        // Agregar los ingresos al modelo
        model.addAttribute("ingresosReservas", ingresosReservas);
        model.addAttribute("ingresosPasadias", ingresosPasadias);

        return "reportes";  // Retorna la vista reportes.html
    }
}

