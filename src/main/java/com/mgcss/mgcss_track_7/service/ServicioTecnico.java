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
        Optional<Tecnico> tecnicoOpt = tecnicoRepositorio.findById(id);
        if (tecnicoOpt.isPresent()) {
            Tecnico tecnico = tecnicoOpt.get();
            tecnico.setActivo(activo);
            tecnicoRepositorio.save(tecnico);
        }
        
    }

    
    
    public void establecerTecnicoTrabajando(Long id, boolean trabajando){
         Optional<Tecnico> tecnicoOpt = tecnicoRepositorio.findById(id);
        if (tecnicoOpt.isPresent()) {
            Tecnico tecnico = tecnicoOpt.get();
            tecnico.setTrabajando(trabajando);
            tecnicoRepositorio.save(tecnico);
        }
    }
    

}