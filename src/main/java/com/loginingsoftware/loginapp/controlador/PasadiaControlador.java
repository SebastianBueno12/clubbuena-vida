package com.loginingsoftware.loginapp.controlador;



import com.loginingsoftware.loginapp.servicio.PasadiaCalculoServicio;
import com.loginingsoftware.loginapp.servicio.PasadiaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.loginingsoftware.loginapp.modelo.Pasadia;

@Controller
public class PasadiaControlador {

    @Autowired
    private PasadiaServicio pasadiaServicio;

    @Autowired
    private PasadiaCalculoServicio pasadiaCalculoServicio;

    @GetMapping("/admin/piscinaPasadias")
    public String verPasadias(Model model) {
        model.addAttribute("pasadias", pasadiaServicio.listarPasadias());
        return "piscinaPasadias";
    }

    @GetMapping("/admin/piscinaPasadias/nueva")
    public String mostrarFormularioNuevaPasadia(Model model) {
        model.addAttribute("pasadia", new Pasadia());
        return "formularioPasadia";
    }

    @PostMapping("/admin/piscinaPasadias/guardar")
    public String guardarPasadia(Pasadia pasadia) {
        double pagoInicial = pasadiaCalculoServicio.calcularPagoInicial(pasadia.getPagoTotal());
        pasadia.setPagoInicial(pagoInicial); // Asignar el pago inicial calculado
        pasadiaServicio.guardarPasadia(pasadia);
        return "redirect:/admin/piscinaPasadias";
    }

    @GetMapping("/admin/piscinaPasadias/editar/{id}")
    public String mostrarFormularioEditarPasadia(@PathVariable Long id, Model model) {
        Pasadia pasadia = pasadiaServicio.obtenerPasadiaPorId(id);
        model.addAttribute("pasadia", pasadia);
        return "formularioPasadia";
    }

    @PostMapping("/admin/piscinaPasadias/actualizar")
    public String actualizarPasadia(Pasadia pasadia) {
        double pagoInicial = pasadiaCalculoServicio.calcularPagoInicial(pasadia.getPagoTotal());
        pasadia.setPagoInicial(pagoInicial); // Recalcular si el pago total ha cambiado
        pasadiaServicio.guardarPasadia(pasadia);
        return "redirect:/admin/piscinaPasadias";
    }

    @GetMapping("/admin/piscinaPasadias/eliminar/{id}")
    public String eliminarPasadia(@PathVariable Long id) {
        pasadiaServicio.eliminarPasadia(id);
        return "redirect:/admin/piscinaPasadias";
    }
}

