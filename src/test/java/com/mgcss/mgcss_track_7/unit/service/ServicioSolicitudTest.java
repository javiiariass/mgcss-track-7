package com.mgcss.mgcss_track_7.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.SolicitudRepositorio;
import com.mgcss.mgcss_track_7.service.ServicioSolicitud;

class ServicioSolicitudTest {

    @Test
    void findByIdYretornarSolicitudCuandoExiste() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(10L);

        when(repositorio.findById(10L)).thenReturn(Optional.of(solicitud));

        Optional<Solicitud> resultado = servicio.findById(10L);

        assertTrue(resultado.isPresent());
        assertEquals(10L, resultado.get().getId());
        verify(repositorio).findById(10L);
    }

    @Test
    void saveYpersistirSolicitud() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(7L);

        when(repositorio.save(solicitud)).thenReturn(solicitud);

        Solicitud resultado = servicio.save(solicitud);

        assertEquals(7L, resultado.getId());
        verify(repositorio).save(solicitud);
    }

    @Test
    void crearSolicitudYguardarlaConEstadoAbierta() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        when(repositorio.save(Mockito.any(Solicitud.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Solicitud resultado = servicio.crearSolicitud(15L, "Error de red", new Cliente());

        assertEquals(15L, resultado.getId());
        assertEquals("Error de red", resultado.getDescripcion());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, resultado.getEstado());
        verify(repositorio).save(Mockito.any(Solicitud.class));
    }

    @Test
    void cambiarEstadoYactualizarSolicitudCuandoExiste() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        Solicitud solicitud = new Solicitud(2L, new Cliente(), "", null);

        when(repositorio.findById(21L)).thenReturn(Optional.of(solicitud));
        when(repositorio.save(solicitud)).thenReturn(solicitud);

        Solicitud resultado = servicio.cambiarEstado(21L);

        assertNotNull(resultado);
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, resultado.getEstado());
        verify(repositorio).findById(21L);
        verify(repositorio).save(solicitud);
    }

    @Test
    void cambiarEstadoYnoActualizarSolicitudCuandoNoExiste() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        Solicitud resultadoNoExiste = servicio.cambiarEstado(99L);

        assertNull(resultadoNoExiste);
        verify(repositorio).findById(99L);
        verify(repositorio, never()).save(Mockito.any(Solicitud.class));
    }

    @Test
    void cambiarEstadoYEliminarTecnico() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);
        Tecnico tecnico = new Tecnico();
        Solicitud solicitud = new Solicitud(21L, new Cliente(), "", null);

        tecnico.setActivo(true);
        tecnico.setTrabajando(false);
        solicitud.asignarTecnico(tecnico);
        solicitud.siguienteEstado();

        when(repositorio.save(solicitud)).thenReturn(solicitud);
        when(repositorio.findById(21L)).thenReturn(Optional.of(solicitud));

        Solicitud resultado = servicio.cambiarEstado(21L);

        assertNotNull(resultado);
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, resultado.getEstado());
        verify(repositorio).save(solicitud);
        verify(repositorio).findById(21L);
    }

    @Test
    void asignarTecnicoYactualizarSolicitud() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);

        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);

        when(repositorio.findById(1L)).thenReturn(Optional.of(solicitud));
        when(repositorio.save(solicitud)).thenReturn(solicitud);

        Solicitud resultado = servicio.asignarTecnico(1L, tecnico);

        verify(repositorio).findById(1L);
        verify(repositorio).save(solicitud);
        assertEquals(tecnico, resultado.getTecnicoAsignado());
    }

    @Test
    void asignarTecnicoYlanzarExcepcion() {
        SolicitudRepositorio repositorio = Mockito.mock(SolicitudRepositorio.class);
        ServicioSolicitud servicio = new ServicioSolicitud(repositorio);

        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);

        when(repositorio.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> servicio.asignarTecnico(99L, tecnico));

        verify(repositorio).findById(99L);
        verify(repositorio, never()).save(Mockito.any(Solicitud.class));
    }
}