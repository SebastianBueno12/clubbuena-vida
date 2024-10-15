package com.loginingsoftware.loginapp.servicio;


import java.time.LocalDate;
import java.util.List;

import com.loginingsoftware.loginapp.modelo.Producto;
import com.loginingsoftware.loginapp.repositorio.PasadiaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loginingsoftware.loginapp.modelo.Pasadia;


@Service
public class PasadiaServicioImpl implements PasadiaServicio {

    @Autowired
    private PasadiaRepositorio pasadiaRepositorio;

    @Override
    public List<Pasadia> listarPasadias() {
        return pasadiaRepositorio.findAll();
    }

    @Override
    public Pasadia guardarPasadia(Pasadia pasadia) {
        // Si la fecha de ingreso es nula, asignar la fecha actual
        if (pasadia.getFechaReserva() == null) {
            pasadia.setFechaReserva(LocalDate.now());
        }
        return pasadiaRepositorio.save(pasadia);
    }

    @Override
    public Pasadia obtenerPasadiaPorId(Long id) {
        return pasadiaRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminarPasadia(Long id) {
        pasadiaRepositorio.deleteById(id);
    }
    // Método para obtener el ingreso total por pasadías pagadas
    public double obtenerIngresosTotalesPasadias() {
        List<Pasadia> pasadias = pasadiaRepositorio.findByEstadoPago("Pagado");
        return pasadias.stream().mapToDouble(Pasadia::getPagoTotal).sum();
    }
}
