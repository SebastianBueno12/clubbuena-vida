package com.loginingsoftware.loginapp.servicio;

import org.springframework.stereotype.Service;

@Service
public class PasadiaCalculoServicio {

    public double calcularPagoInicial(double pagoTotal) {
        return pagoTotal * 0.30;
    }

    public double calcularPagoRestante(double pagoTotal, double pagoInicial) {
        return pagoTotal - pagoInicial;
    }
}
