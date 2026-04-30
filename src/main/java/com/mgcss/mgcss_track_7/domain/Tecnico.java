package com.mgcss.mgcss_track_7.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Tecnico {

    private Long id;
    private String nombre;
    private String especialidad;
    private boolean activo;
    private boolean trabajando;

    public Tecnico(Long id, String nombre, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = true;
        this.trabajando = false;
    }
}