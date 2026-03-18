package com.mgcss.domain;

public class Tecnico {

    private Long id;
    private String nombre;
    private String especialidad;
    private Boolean activo;

    public Tecnico(Long id, String nombre, String especialidad, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = activo;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}