package com.loginingsoftware.loginapp.modelo;

import javax.persistence.*;

@Entity
@Table(name = "habitaciones")
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "numero")
    private String numero;

    @Column(name = "estado")
    private String estado;  // Disponible, Ocupada, Mantenimiento

    @Column(name = "tipo")
    private String tipo;  // Habitación o Cabaña

    // Relación con Reserva
    @ManyToOne
    @JoinColumn(name = "reserva_id", nullable = true)  // Permitir nulos
    private Reserva reserva;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
}
