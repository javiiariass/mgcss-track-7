package com.mgcss.mgcss_track_7.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
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
        tecnico.setId(5L);
        tecnico.setNombre("Carlos");

        when(repositorio.findById(5L)).thenReturn(Optional.of(tecnico));

        Optional<Tecnico> resultado = servicio.findById(5L);

        assertTrue(resultado.isPresent());
        assertEquals(5L, resultado.get().getId());
        assertEquals("Carlos", resultado.get().getNombre());
        verify(repositorio).findById(5L);
    }

    @Test
    void crearTecnicoYguardarloConValoresInicial() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        Tecnico tecnico = new Tecnico(8L, "María", "Plomería");

        when(repositorio.save(Mockito.any(Tecnico.class))).thenReturn(tecnico);

        Tecnico resultado = servicio.crearTecnico(8L, "María", "Plomería");

        assertEquals(8L, resultado.getId());
        assertEquals("María", resultado.getNombre());
        assertEquals("Plomería", resultado.getEspecialidad());
        verify(repositorio).save(Mockito.any(Tecnico.class));
    }

    @Test
    void estableceTecnicoActivoYactualizarCuandoExiste() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        Tecnico tecnico = new Tecnico();
        tecnico.setId(3L);
        tecnico.setNombre("Pedro");
        tecnico.setActivo(false);

        when(repositorio.findById(3L)).thenReturn(Optional.of(tecnico));

        servicio.estableceTecnicoActivo(3L, true);

        assertEquals(true, tecnico.getActivo());
        verify(repositorio).findById(3L);
    }

    @Test
    void estableceTecnicoActivoYnoHacerNadaSiNoExiste() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        servicio.estableceTecnicoActivo(99L, true);

        verify(repositorio).findById(99L);
    }

    @Test
    void establecerTecnicoTrabajandoYactualizarCuandoExiste() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        Tecnico tecnico = new Tecnico();
        tecnico.setId(12L);
        tecnico.setNombre("Antonio");
        tecnico.setTrabajando(false);

        when(repositorio.findById(12L)).thenReturn(Optional.of(tecnico));

        servicio.establecerTecnicoTrabajando(12L, true);

        assertEquals(true, tecnico.isTrabajando());
        verify(repositorio).findById(12L);
    }

    @Test
    void establecerTecnicoTrabajandoYnoHacerNadaSiNoExiste() {
        TecnicoRepositorio repositorio = Mockito.mock(TecnicoRepositorio.class);
        ServicioTecnico servicio = new ServicioTecnico(repositorio);

        when(repositorio.findById(88L)).thenReturn(Optional.empty());

        servicio.establecerTecnicoTrabajando(88L, false);

        verify(repositorio).findById(88L);
        verify(repositorio, never()).save(Mockito.any(Tecnico.class));
    }
}
