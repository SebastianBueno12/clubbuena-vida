package com.loginingsoftware.loginapp.modelo;

import javax.persistence.*;

@Entity
@Table(name = "implementos")
public class Implemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "estado")
    private String estado; // Disponible, En uso, Mal estado

    // Constructor vacío
    public Implemento() {}

    // Constructor con parámetros
    public Implemento(String nombre, int cantidad, String estado) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
