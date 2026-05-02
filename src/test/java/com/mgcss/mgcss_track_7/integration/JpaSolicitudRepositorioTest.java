package com.mgcss.mgcss_track_7.integration;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import com.mgcss.mgcss_track_7.infraestrucure.persistence.ClienteEntidad;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.JpaSolicitudRepositorio;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.SolicitudEntidad;

import com.mgcss.mgcss_track_7.domain.Solicitud;


import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@DataJpaTest
@ActiveProfiles("test")
@Tag("integration")
class JpaSolicitudRepositorioTest {

    @Autowired
    private JpaSolicitudRepositorio solicitudRepositorio;

    @Test
    void guardarEntidad(){
        SolicitudEntidad nuevaSolicitud = new SolicitudEntidad();
        nuevaSolicitud.setId(1L);

        SolicitudEntidad solicitudBD = solicitudRepositorio.save(nuevaSolicitud);

        Optional <SolicitudEntidad> solicitudEncontrada = solicitudRepositorio.findById(solicitudBD.getId());

        assertTrue(solicitudEncontrada.isPresent());
        assertEquals(solicitudBD.getId() ,solicitudEncontrada.get().getId());
    }
    @Test
    void getterAndSettersAll(){
        ClienteEntidad cliente = new ClienteEntidad();
        SolicitudEntidad entidad = new SolicitudEntidad(2L, cliente, "Test description", null);
        
        // Setters
        entidad.setId(1L);
        entidad.setDescripcion("Test description");
        entidad.setEstado(Solicitud.estadoSolicitudes.ABIERTA);
        
        // Getters
        assertEquals(1L, entidad.getId());
        assertEquals("Test description", entidad.getDescripcion());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, entidad.getEstado());
    }

    @Test
    void comprobarHistoriocoEstados(){
        SolicitudEntidad entidad = new SolicitudEntidad(1l, new ClienteEntidad(), "Test description", null);
        SolicitudEntidad solicitudBD = solicitudRepositorio.save(entidad);
        Optional <SolicitudEntidad> solicitudEncontrada = solicitudRepositorio.findById(solicitudBD.getId());
        assertTrue(solicitudEncontrada.isPresent());
        assertEquals(1, solicitudEncontrada.get().getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, solicitudEncontrada.get().getHistorico().get(0));
    }

    
}
