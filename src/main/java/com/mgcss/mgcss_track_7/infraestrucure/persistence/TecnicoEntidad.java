package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "tecnicos")
public class TecnicoEntidad {
    @Id
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "activo")
    private boolean activo;

    @Column(name = "trabajando")
    private boolean trabajando;


    public TecnicoEntidad(Long id, String nombre, boolean activo, String especialidad) {
        this.id = id;
        this.nombre = nombre;
        this.especialidad = especialidad;
        this.activo = activo;
    }

}
