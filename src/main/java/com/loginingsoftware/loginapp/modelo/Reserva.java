package com.loginingsoftware.loginapp.modelo;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String numeroDocumento;
    private String correo;
    private int adultos;
    private int ninos;
    private int diasHospedaje;
    private double precioPorNoche;
    private double totalHospedaje;
    private double pagoInicial;
    private double pagoRestante;

    @Enumerated(EnumType.STRING)
    private EstadoReserva estadoReserva;
    // Relación con Habitacion
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Habitacion> habitaciones;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getAdultos() {
        return adultos;
    }

    public void setAdultos(int adultos) {
        this.adultos = adultos;
    }

    public int getNinos() {
        return ninos;
    }

    public void setNinos(int ninos) {
        this.ninos = ninos;
    }

    public int getDiasHospedaje() {
        return diasHospedaje;
    }

    public void setDiasHospedaje(int diasHospedaje) {
        this.diasHospedaje = diasHospedaje;
    }

    public double getPrecioPorNoche() {
        return precioPorNoche;
    }

    public void setPrecioPorNoche(double precioPorNoche) {
        this.precioPorNoche = precioPorNoche;
    }

    public double getTotalHospedaje() {
        return totalHospedaje;
    }

    public void setTotalHospedaje(double totalHospedaje) {
        this.totalHospedaje = totalHospedaje;
    }

    public double getPagoInicial() {
        return pagoInicial;
    }

    public void setPagoInicial(double pagoInicial) {
        this.pagoInicial = pagoInicial;
    }

    public double getPagoRestante() {
        return pagoRestante;
    }

    public void setPagoRestante(double pagoRestante) {
        this.pagoRestante = pagoRestante;
    }

    public EstadoReserva getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(EstadoReserva estadoReserva) {
        this.estadoReserva = estadoReserva;
    }
    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }
}

