package com.mgcss.mgcss_track_7.api.dto;

public class ClienteRespuestaDTO {
    private Long id;
    private String nombre;
    private String email;
    private String tipo;

    public ClienteRespuestaDTO() {
    }

    public ClienteRespuestaDTO(Long id, String nombre, String email, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.tipo = tipo;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
