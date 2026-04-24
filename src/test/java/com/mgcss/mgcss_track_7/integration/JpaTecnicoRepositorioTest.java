package com.mgcss.mgcss_track_7.integration;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.JpaTecnicoRepositorio;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.TecnicoEntidad;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


@DataJpaTest
@ActiveProfiles("test")
@Tag("integration")
class JpaTecnicoRepositorioTest {

    @Autowired
    private JpaTecnicoRepositorio tecnicoRepositorio;

    @Test
    void guardarEntidad(){
        TecnicoEntidad nuevoTecnico = new TecnicoEntidad();
        nuevoTecnico.setId(1L);

        TecnicoEntidad tecnicoBD = tecnicoRepositorio.save(nuevoTecnico);

        Optional <TecnicoEntidad> tecnicoEncontrado = tecnicoRepositorio.findById(tecnicoBD.getId());

        assertTrue(tecnicoEncontrado.isPresent());
        assertEquals(tecnicoBD.getId() ,tecnicoEncontrado.get().getId());
    }
    @Test
    void getterAndSettersAll(){
        TecnicoEntidad entidad = new TecnicoEntidad(2L, "Test description", );
        
        // Setters
        entidad.setId(1L);
        entidad.setDescripcion("Test description");
        entidad.setEstado(Solicitud.estadoSolicitudes.ABIERTA);
        
        // Getters
        assertEquals(1L, entidad.getId());
        assertEquals("Test description", entidad.getDescripcion());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, entidad.getEstado());
    }
}
