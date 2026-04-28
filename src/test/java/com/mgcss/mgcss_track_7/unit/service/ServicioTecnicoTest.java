package com.mgcss.mgcss_track_7.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.TecnicoRepositorio;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

class ServicioTecnicoTest {

    @Test
    void findByIdYretornarTecnicoCuandoExiste() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        Tecnico tecnico = new Tecnico();
        tecnico.setId(1L);

        when(repositorio.findById(1L)).thenReturn(Optional.of(tecnico));

        Optional<Tecnico> resultado = servicio.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
        verify(repositorio).findById(1L);
    }

    @Test
    void crearTecnicoYguardarloConDatos() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        when(repositorio.save(Mockito.any(Tecnico.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Tecnico resultado = servicio.crearTecnico(5L, "Ana Lopez", "Redes");

        assertEquals(5L, resultado.getId());
        assertEquals("Ana Lopez", resultado.getNombre());
        assertEquals("Redes", resultado.getEspecialidad());
        verify(repositorio).save(Mockito.any(Tecnico.class));
    }

    @Test
    void estableceTecnicoActivo() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        Tecnico tecnico = new Tecnico();
        tecnico.setId(2L);
        tecnico.setActivo(false);

        when(repositorio.findById(2L)).thenReturn(Optional.of(tecnico));

        servicio.estableceTecnicoActivo(2L, true);

        assertTrue(tecnico.getActivo());
        verify(repositorio).findById(2L);
    }

    @Test
    void establecerTecnicoTrabajando() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        Tecnico tecnico = new Tecnico();
        tecnico.setId(3L);
        tecnico.setTrabajando(false);

        when(repositorio.findById(3L)).thenReturn(Optional.of(tecnico));

        servicio.establecerTecnicoTrabajando(3L, true);

        assertTrue(tecnico.isTrabajando());
        verify(repositorio).findById(3L);
    }
}
