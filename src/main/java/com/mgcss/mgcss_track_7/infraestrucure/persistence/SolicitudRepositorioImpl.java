package com.mgcss.mgcss_track_7.infraestrucure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;

@Repository
public class SolicitudRepositorioImpl implements SolicitudRepositorio {
    private final JpaSolicitudRepositorio jpaSolicitud;

    public SolicitudRepositorioImpl(JpaSolicitudRepositorio jpaSolicitud) {
        this.jpaSolicitud = jpaSolicitud;
    }

    @Override
    public List<Solicitud> findAll() {
        return jpaSolicitud.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Solicitud> findById(Long id) {
        return jpaSolicitud.findById(id).map(this::toDomain);
    }

    @Override
    public Solicitud save(Solicitud solicitud) {
        SolicitudEntidad solicitudEntidad = toEntity(solicitud);
        SolicitudEntidad savedEntity = jpaSolicitud.save(solicitudEntidad);
        return toDomain(savedEntity);
    }


    private SolicitudEntidad toEntity(Solicitud solicitud) {
        SolicitudEntidad entidad = new SolicitudEntidad();
        entidad.setId(solicitud.getId());
        entidad.setDescripcion(solicitud.getDescripcion());
        if (solicitud.getCliente() != null) {
            Cliente c = solicitud.getCliente();
            ClienteEntidad clienteEntidad = new ClienteEntidad();
            clienteEntidad.setId(c.getId());
            clienteEntidad.setNombre(c.getNombre());
            clienteEntidad.setEmail(c.getEmail());
            clienteEntidad.setTipoCliente(c.getTipo());
            entidad.setCliente(clienteEntidad);
        }
        if (solicitud.getTecnicoAsignado() != null) {
            Tecnico t = solicitud.getTecnicoAsignado();
            TecnicoEntidad tecnicoEntidad = new TecnicoEntidad();
            tecnicoEntidad.setId(t.getId());
            tecnicoEntidad.setNombre(t.getNombre());
            tecnicoEntidad.setEspecialidad(t.getEspecialidad());
            tecnicoEntidad.setActivo(t.isActivo());
            tecnicoEntidad.setTrabajando(t.isTrabajando());
            entidad.setTecnicoAsignado(tecnicoEntidad);
        }
        return entidad;
    }


    private Solicitud toDomain(SolicitudEntidad solicitudEntidad) {
        Cliente cliente = null;
        if (solicitudEntidad.getCliente() != null) {
            ClienteEntidad ce = solicitudEntidad.getCliente();
            cliente = new Cliente();
            cliente.setId(ce.getId());
            cliente.setNombre(ce.getNombre());
            cliente.setEmail(ce.getEmail());
            cliente.setTipo(ce.getTipoCliente());
        }
        Tecnico tecnico = null;
        if (solicitudEntidad.getTecnicoAsignado() != null) {
            TecnicoEntidad te = solicitudEntidad.getTecnicoAsignado();
            tecnico = new Tecnico();
            tecnico.setId(te.getId());
            tecnico.setNombre(te.getNombre());
            tecnico.setEspecialidad(te.getEspecialidad());
            tecnico.setActivo(te.isActivo());
            tecnico.setTrabajando(te.isTrabajando());
        }
        return new Solicitud(
                solicitudEntidad.getId(),
                cliente,
                solicitudEntidad.getDescripcion(),
                tecnico);
    }

}
