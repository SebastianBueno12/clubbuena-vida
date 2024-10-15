package com.loginingsoftware.loginapp.modelo;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "pasadias")
public class Pasadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_cliente")
    private String nombreCliente;

    @Column(name = "documento_cliente")
    private String documentoCliente;

    @Column(name = "correo_cliente")
    private String correoCliente;

    @Column(name = "num_adultos")
    private int numAdultos;

    @Column(name = "num_ninos")
    private int numNinos;

    @Column(name = "servicio")
    private String servicio; // "Piscina" o "Piscina y Almuerzo"

    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @Column(name = "pago_inicial")
    private double pagoInicial;

    @Column(name = "pago_total")
    private double pagoTotal;

    @Column(name = "estado_pago")
    private String estadoPago; // Pendiente, Pago Parcial, Pagado

    // Constructor vacío
    public Pasadia() {}

    // Constructor con parámetros
    public Pasadia(String nombreCliente, String documentoCliente, String correoCliente, int numAdultos, int numNinos, String servicio, LocalDate fechaReserva, double pagoTotal, String estadoPago) {
        this.nombreCliente = nombreCliente;
        this.documentoCliente = documentoCliente;
        this.correoCliente = correoCliente;
        this.numAdultos = numAdultos;
        this.numNinos = numNinos;
        this.servicio = servicio;
        this.fechaReserva = fechaReserva;
        this.pagoTotal = pagoTotal;
        this.pagoInicial = calcularPagoInicial(pagoTotal); // Cálculo automático del 30%
        this.estadoPago = estadoPago;
    }

    // Método para calcular el pago inicial automáticamente
    public double calcularPagoInicial(double pagoTotal) {
        return pagoTotal * 0.30; // 30% del total
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getDocumentoCliente() { return documentoCliente; }
    public void setDocumentoCliente(String documentoCliente) { this.documentoCliente = documentoCliente; }

    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }

    public int getNumAdultos() { return numAdultos; }
    public void setNumAdultos(int numAdultos) { this.numAdultos = numAdultos; }

    public int getNumNinos() { return numNinos; }
    public void setNumNinos(int numNinos) { this.numNinos = numNinos; }

    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }

    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }

    public double getPagoInicial() { return pagoInicial;}
    public void setPagoInicial(Double pagoInicial) { this.pagoInicial = pagoInicial; }

    public double getPagoTotal() { return pagoTotal; }
    public void setPagoTotal(double pagoTotal) {
        this.pagoTotal = pagoTotal;
        this.pagoInicial = calcularPagoInicial(pagoTotal); // Recalcular el pago inicial cada vez que se actualice el total
    }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }
}
