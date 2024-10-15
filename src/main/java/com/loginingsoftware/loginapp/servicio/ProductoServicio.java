package com.loginingsoftware.loginapp.servicio;

import java.util.List;
import com.loginingsoftware.loginapp.modelo.Producto;

public interface ProductoServicio {
    List<Producto> listarProductos();
    Producto guardarProducto(Producto producto);
    Producto obtenerProductoPorId(Long id);
    void eliminarProducto(Long id);
}
