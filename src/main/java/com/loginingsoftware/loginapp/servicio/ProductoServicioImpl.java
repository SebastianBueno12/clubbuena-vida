package com.loginingsoftware.loginapp.servicio;


import java.time.LocalDate;
import java.util.List;

import com.loginingsoftware.loginapp.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.loginingsoftware.loginapp.modelo.Producto;

@Service
public class ProductoServicioImpl implements ProductoServicio {

    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> listarProductos() {
        return productoRepositorio.findAll();
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        // Si la fecha de ingreso es nula, asignar la fecha actual
        if (producto.getFechaIngreso() == null) {
            producto.setFechaIngreso(LocalDate.now());
        }
        return productoRepositorio.save(producto);
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        return productoRepositorio.findById(id).orElse(null);
    }

    @Override
    public void eliminarProducto(Long id) {
        productoRepositorio.deleteById(id);
    }
}

