package com.mgcss.mgcss_track_7.integration;

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.JpaTecnicoRepositorio;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.TecnicoEntidad;
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
        TecnicoEntidad nuevoTecnico = new TecnicoEntidad(1L,"jUAN",true,"Electricidad");
        nuevoTecnico.setTrabajando(false);

        TecnicoEntidad tecnicoBD = tecnicoRepositorio.save(nuevoTecnico);

        Optional <TecnicoEntidad> tecnicoEncontrado = tecnicoRepositorio.findById(tecnicoBD.getId());

        assertTrue(tecnicoEncontrado.isPresent());
        assertEquals(tecnicoBD.getId() ,tecnicoEncontrado.get().getId());
    }
    @Test
    void getterAndSettersAll(){
     
         TecnicoEntidad nuevoTecnico = new TecnicoEntidad();
          nuevoTecnico.setId(1L);
          nuevoTecnico.setNombre("Juan");
          nuevoTecnico.setEspecialidad("Electricidad");
          nuevoTecnico.setActivo(true);
          nuevoTecnico.setTrabajando(false);
    
          assertEquals(1L, nuevoTecnico.getId());
          assertEquals("Juan", nuevoTecnico.getNombre());
          assertEquals("Electricidad", nuevoTecnico.getEspecialidad());
          assertTrue(nuevoTecnico.isActivo());
          assertFalse(nuevoTecnico.isTrabajando());
    }
}
