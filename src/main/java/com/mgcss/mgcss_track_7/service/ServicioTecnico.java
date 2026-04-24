package com.mgcss.mgcss_track_7.service;

import java.util.Optional;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.TecnicoRepositorio;

import java.util.List;

public class ServicioTecnico {

    public final TecnicoRepositorio tecnicoRepositorio;
    List<Tecnico> tecnicos;

    public ServicioTecnico(TecnicoRepositorio tecnicoRepositorio) {
        this.tecnicoRepositorio = tecnicoRepositorio ;
    }

    public Optional<Tecnico> findById(Long id) {

        return tecnicoRepositorio.findById(id);

    }
 
    public Tecnico crearTecnico(Long id, String nombre, String especialidad) {
        Tecnico tecnico = new Tecnico(id, nombre, especialidad);
        return tecnicoRepositorio.save(tecnico);
    }

    public void estableceTecnicoActivo(Long id, boolean activo){
        tecnicoRepositorio.findById(id).get().setActivo(activo);
    }
    
    // TODO DANI crea este medoto con el atributo nuevo
    // public void estableceTecnicoActivo(Long id, boolean activo){
    //     tecnicoRepositorio.findById(id).get().setActivo(activo);
    // }
    // public void trabajando o no trabajando

    // REVIEW hay que implementarlo si mantenemos la doble relación entre Tecnico y solicitud 
    // public Tecnico asignarSolicitud(Long id, Solicitud solicitud) {

    //     Optional<Solicitud> solicitudOpt = solicitudRepositorio.findById(idSolicitud);
    //     if (solicitudOpt.isEmpty()) {
    //         throw new IllegalArgumentException("Solicitud no encontrada con id: " + idSolicitud);
    //     }

    //     Solicitud solicitud = solicitudOpt.get();
    //     solicitud.asignarTecnico(tecnico);
    //     return solicitudRepositorio.save(solicitud);

    // }

    

}