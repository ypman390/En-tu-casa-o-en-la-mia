package com.acostaivan.entucasaoenlamia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Solicitud {

    private int id;
    private int usuarioId;
    private int espacioId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean aceptada;
    private BigDecimal precioTotal;
    private String comentario;
    private int numeroPersonas;

    public Solicitud() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public int getEspacioId() { return espacioId; }
    public void setEspacioId(int espacioId) { this.espacioId = espacioId; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public boolean isAceptada() { return aceptada; }
    public void setAceptada(boolean aceptada) { this.aceptada = aceptada; }

    public BigDecimal getPrecioTotal() { return precioTotal; }
    public void setPrecioTotal(BigDecimal precioTotal) { this.precioTotal = precioTotal; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public int getNumeroPersonas() { return numeroPersonas; }
    public void setNumeroPersonas(int numeroPersonas) { this.numeroPersonas = numeroPersonas; }
}