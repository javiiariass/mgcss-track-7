package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mgcss.mgcss_track_7.domain.Tecnico;

@Repository
public class TecnicoRepositorioImpl implements TecnicoRepositorio {
    private final JpaTecnicoRepositorio jpaTecnico;

    public TecnicoRepositorioImpl(JpaTecnicoRepositorio jpaTecnico) {
        this.jpaTecnico = jpaTecnico;
    }

    @Override
    public Optional<Tecnico> findById(Long id) {
        return jpaTecnico.findById(id).map(this::toDomain);
    }

    @Override
    public Tecnico save(Tecnico tecnico) {
        TecnicoEntidad entidad = toEntity(tecnico);
        TecnicoEntidad saved = jpaTecnico.save(entidad);
        return toDomain(saved);
    }

    private TecnicoEntidad toEntity(Tecnico tecnico) {
        TecnicoEntidad entidad = new TecnicoEntidad();
        entidad.setId(tecnico.getId());
        entidad.setNombre(tecnico.getNombre());
        entidad.setEspecialidad(tecnico.getEspecialidad());
        entidad.setActivo(tecnico.isActivo());
        entidad.setTrabajando(tecnico.isTrabajando());
        return entidad;
    }

    private Tecnico toDomain(TecnicoEntidad entidad) {
        Tecnico tecnico = new Tecnico();
        tecnico.setId(entidad.getId());
        tecnico.setNombre(entidad.getNombre());
        tecnico.setEspecialidad(entidad.getEspecialidad());
        tecnico.setActivo(entidad.isActivo());
        tecnico.setTrabajando(entidad.isTrabajando());
        return tecnico;
    }
}
