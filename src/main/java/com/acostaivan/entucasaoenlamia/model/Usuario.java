package  com.acostaivan.entucasaoenlamia.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Usuario {

    private int id;
    private String nombre;
    private String username;
    private String email;
    private String password;
    private String dni;
    private String rol;
    private boolean activo;
    private LocalDate fechaRegistro;
    private int puntos;
    private BigDecimal credito;

    // Constructor vacío
    public Usuario() {}

    // Constructor completo
    public Usuario(int id, String nombre, String username, String email,
                   String password, String dni, String rol, boolean activo,
                   LocalDate fechaRegistro, int puntos, BigDecimal credito) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dni = dni;
        this.rol = rol;
        this.activo = activo;
        this.fechaRegistro = fechaRegistro;
        this.puntos = puntos;
        this.credito = credito;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    public BigDecimal getCredito() { return credito; }
    public void setCredito(BigDecimal credito) { this.credito = credito; }
}