package com.mgcss.mgcss_track_7.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.JpaTecnicoRepositorio;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.TecnicoEntidad;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.TecnicoRepositorioImpl;

class TecnicoRepositorioImplTest {

    @Test
    void findByIdEncontrado() {
        JpaTecnicoRepositorio jpa = Mockito.mock(JpaTecnicoRepositorio.class);
        TecnicoRepositorioImpl impl = new TecnicoRepositorioImpl(jpa);

        TecnicoEntidad entidad = new TecnicoEntidad(1L, "Pedro", true, "Redes");
        when(jpa.findById(1L)).thenReturn(Optional.of(entidad));

        Optional<Tecnico> result = impl.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        assertEquals("Pedro", result.get().getNombre());
        assertEquals("Redes", result.get().getEspecialidad());
        assertTrue(result.get().isActivo());
    }

    @Test
    void findByIdNoEncontrado() {
        JpaTecnicoRepositorio jpa = Mockito.mock(JpaTecnicoRepositorio.class);
        TecnicoRepositorioImpl impl = new TecnicoRepositorioImpl(jpa);
        when(jpa.findById(99L)).thenReturn(Optional.empty());

        assertTrue(impl.findById(99L).isEmpty());
    }

    @Test
    void save() {
        JpaTecnicoRepositorio jpa = Mockito.mock(JpaTecnicoRepositorio.class);
        TecnicoRepositorioImpl impl = new TecnicoRepositorioImpl(jpa);

        Tecnico tecnico = new Tecnico(1L, "Laura", "Soporte");
        tecnico.setActivo(true);
        tecnico.setTrabajando(false);

        TecnicoEntidad saved = new TecnicoEntidad(1L, "Laura", true, "Soporte");
        when(jpa.save(any(TecnicoEntidad.class))).thenReturn(saved);

        Tecnico result = impl.save(tecnico);
        assertEquals(1L, result.getId());
        assertEquals("Laura", result.getNombre());
        assertEquals("Soporte", result.getEspecialidad());
        assertTrue(result.isActivo());
    }
}
