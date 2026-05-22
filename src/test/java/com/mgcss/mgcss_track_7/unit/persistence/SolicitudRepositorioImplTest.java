package com.mgcss.mgcss_track_7.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.infrastructure.persistence.ClienteEntidad;
import com.mgcss.mgcss_track_7.infrastructure.persistence.JpaSolicitudRepositorio;
import com.mgcss.mgcss_track_7.infrastructure.persistence.SolicitudEntidad;
import com.mgcss.mgcss_track_7.infrastructure.persistence.SolicitudRepositorioImpl;
import com.mgcss.mgcss_track_7.infrastructure.persistence.TecnicoEntidad;

class SolicitudRepositorioImplTest {

    @Test
    void findAll() {
        JpaSolicitudRepositorio jpa = Mockito.mock(JpaSolicitudRepositorio.class);
        SolicitudRepositorioImpl impl = new SolicitudRepositorioImpl(jpa);

        SolicitudEntidad entidad = new SolicitudEntidad();
        entidad.setId(1L);
        entidad.setDescripcion("Test findAll");
        when(jpa.findAll()).thenReturn(List.of(entidad));

        List<Solicitud> result = impl.findAll();
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Test findAll", result.get(0).getDescripcion());
    }

    @Test
    void findByIdEncontrado() {
        JpaSolicitudRepositorio jpa = Mockito.mock(JpaSolicitudRepositorio.class);
        SolicitudRepositorioImpl impl = new SolicitudRepositorioImpl(jpa);

        SolicitudEntidad entidad = new SolicitudEntidad();
        entidad.setId(2L);
        entidad.setDescripcion("Desc findById");
        when(jpa.findById(2L)).thenReturn(Optional.of(entidad));

        Optional<Solicitud> result = impl.findById(2L);
        assertTrue(result.isPresent());
        assertEquals(2L, result.get().getId());
        assertEquals("Desc findById", result.get().getDescripcion());
    }

    @Test
    void findByIdNoEncontrado() {
        JpaSolicitudRepositorio jpa = Mockito.mock(JpaSolicitudRepositorio.class);
        SolicitudRepositorioImpl impl = new SolicitudRepositorioImpl(jpa);
        when(jpa.findById(99L)).thenReturn(Optional.empty());

        assertTrue(impl.findById(99L).isEmpty());
    }

    @Test
    void saveSolicitudSinClienteNiTecnico() {
        JpaSolicitudRepositorio jpa = Mockito.mock(JpaSolicitudRepositorio.class);
        SolicitudRepositorioImpl impl = new SolicitudRepositorioImpl(jpa);

        Solicitud solicitud = new Solicitud();
        solicitud.setId(3L);
        solicitud.setDescripcion("Sin cliente ni tecnico");

        SolicitudEntidad saved = new SolicitudEntidad();
        saved.setId(3L);
        saved.setDescripcion("Sin cliente ni tecnico");
        when(jpa.save(any(SolicitudEntidad.class))).thenReturn(saved);

        Solicitud result = impl.save(solicitud);
        assertEquals(3L, result.getId());
        assertEquals("Sin cliente ni tecnico", result.getDescripcion());
        assertNull(result.getCliente());
        assertNull(result.getTecnicoAsignado());
    }

    @Test
    void saveSolicitudConClienteYTecnico() {
        JpaSolicitudRepositorio jpa = Mockito.mock(JpaSolicitudRepositorio.class);
        SolicitudRepositorioImpl impl = new SolicitudRepositorioImpl(jpa);

        Cliente cliente = new Cliente("Maria", "maria@mail.com", Cliente.tipoCliente.STANDARD);
        cliente.setId(10L);
        Tecnico tecnico = new Tecnico(20L, "Carlos", "Redes");
        Solicitud solicitud = new Solicitud(5L, cliente, "Con cliente y tecnico", tecnico);

        ClienteEntidad clienteEntidad = new ClienteEntidad(10L, "Maria", "maria@mail.com", Cliente.tipoCliente.STANDARD);
        TecnicoEntidad tecnicoEntidad = new TecnicoEntidad(20L, "Carlos", true, "Redes");
        SolicitudEntidad saved = new SolicitudEntidad();
        saved.setId(5L);
        saved.setDescripcion("Con cliente y tecnico");
        saved.setCliente(clienteEntidad);
        saved.setTecnicoAsignado(tecnicoEntidad);
        when(jpa.save(any(SolicitudEntidad.class))).thenReturn(saved);

        Solicitud result = impl.save(solicitud);
        assertEquals(5L, result.getId());
        assertNotNull(result.getCliente());
        assertEquals(10L, result.getCliente().getId());
        assertEquals("Maria", result.getCliente().getNombre());
        assertNotNull(result.getTecnicoAsignado());
        assertEquals(20L, result.getTecnicoAsignado().getId());
        assertEquals("Carlos", result.getTecnicoAsignado().getNombre());
    }
}
