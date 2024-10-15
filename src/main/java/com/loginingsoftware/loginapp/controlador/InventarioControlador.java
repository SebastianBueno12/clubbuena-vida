package com.loginingsoftware.loginapp.controlador;

import com.loginingsoftware.loginapp.modelo.Implemento;
import com.loginingsoftware.loginapp.servicio.ImplementoServicio;
import com.loginingsoftware.loginapp.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.loginingsoftware.loginapp.modelo.Producto;


@Controller
public class InventarioControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private ImplementoServicio implementoServicio;

    // Mostrar vista principal de inventario
    @GetMapping("/admin/inventario")
    public String verInventario(Model model) {
        model.addAttribute("productos", productoServicio.listarProductos());
        model.addAttribute("implementos", implementoServicio.listarImplementos());
        return "inventario";
    }

    // ------------------------------ PRODUCTOS -----------------------------------
    @GetMapping("/admin/inventario/nuevoProducto")
    public String mostrarFormularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        return "formularioProducto";
    }

    @PostMapping("/admin/inventario/guardarProducto")
    public String guardarProducto(Producto producto) {
        productoServicio.guardarProducto(producto);
        return "redirect:/admin/inventario";
    }

    @GetMapping("/admin/inventario/editarProducto/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model) {
        Producto producto = productoServicio.obtenerProductoPorId(id);
        model.addAttribute("producto", producto);
        return "formularioProducto";
    }

    @PostMapping("/admin/inventario/actualizarProducto")
    public String actualizarProducto(Producto producto) {
        productoServicio.guardarProducto(producto);
        return "redirect:/admin/inventario";
    }

    @GetMapping("/admin/inventario/eliminarProducto/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoServicio.eliminarProducto(id);
        return "redirect:/admin/inventario";
    }

    // ------------------------------ IMPLEMENTOS -----------------------------------
    @GetMapping("/admin/inventario/nuevoImplemento")
    public String mostrarFormularioNuevoImplemento(Model model) {
        model.addAttribute("implemento", new Implemento());
        return "formularioImplemento";
    }

    @PostMapping("/admin/inventario/guardarImplemento")
    public String guardarImplemento(Implemento implemento) {
        implementoServicio.guardarImplemento(implemento);
        return "redirect:/admin/inventario";
    }

    @GetMapping("/admin/inventario/editarImplemento/{id}")
    public String mostrarFormularioEditarImplemento(@PathVariable Long id, Model model) {
        Implemento implemento = implementoServicio.obtenerImplementoPorId(id);
        model.addAttribute("implemento", implemento);
        return "formularioImplemento";
    }

    @PostMapping("/admin/inventario/actualizarImplemento")
    public String actualizarImplemento(Implemento implemento) {
        implementoServicio.guardarImplemento(implemento);
        return "redirect:/admin/inventario";
    }

    @GetMapping("/admin/inventario/eliminarImplemento/{id}")
    public String eliminarImplemento(@PathVariable Long id) {
        implementoServicio.eliminarImplemento(id);
        return "redirect:/admin/inventario";
    }
}
