package com.mgcss.service;
import java.util.List;
import java.util.Optional;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;

public class SolicitudService implements SolicitudRepository{
    private List<Solicitud> repo;    

    public boolean asignarTecnico(Solicitud solicitud, Tecnico tecnico){
        return solicitud.asignarTecnico(tecnico);
    }

    void cambiarEstado(Solicitud solicitud){
        solicitud.cambiarEstado();
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        // TODO añadir a la cola
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    @Override
    public Optional<Solicitud> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    @Override
    public List<Solicitud> obtenerSolicitudes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerSolicitudes'");
    }  
}