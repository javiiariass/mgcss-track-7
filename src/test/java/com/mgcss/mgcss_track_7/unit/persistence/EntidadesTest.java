package com.mgcss.mgcss_track_7.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.infrastructure.persistence.ClienteEntidad;
import com.mgcss.mgcss_track_7.infrastructure.persistence.SolicitudEntidad;
import com.mgcss.mgcss_track_7.infrastructure.persistence.TecnicoEntidad;

class EntidadesTest {

    @Test
    void solicitudEntidadGettersSettersFaltantes() {
        SolicitudEntidad entidad = new SolicitudEntidad();
        Date fecha = new Date();
        ClienteEntidad cliente = new ClienteEntidad(1L, "Ana", "ana@mail.com", Cliente.tipoCliente.PREMIUM);
        TecnicoEntidad tecnico = new TecnicoEntidad();

        entidad.setFechaCreacion(fecha);
        entidad.setFechaCierre(fecha);
        entidad.setFechaReapertura(fecha);
        entidad.setTiempoResolucionDias(48L);
        entidad.setCliente(cliente);
        entidad.setTecnicoAsignado(tecnico);
        entidad.setHistorico(List.of(Solicitud.estadoSolicitudes.ABIERTA, Solicitud.estadoSolicitudes.EN_PROCESO));

        assertEquals(fecha, entidad.getFechaCreacion());
        assertEquals(fecha, entidad.getFechaCierre());
        assertEquals(fecha, entidad.getFechaReapertura());
        assertEquals(48L, entidad.getTiempoResolucionDias());
        assertEquals(cliente, entidad.getCliente());
        assertEquals(tecnico, entidad.getTecnicoAsignado());
        assertEquals(2, entidad.getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, entidad.getHistorico().get(0));
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, entidad.getHistorico().get(1));
    }

    @Test
    void tecnicoEntidadConstructorConParametros() {
        TecnicoEntidad tecnico = new TecnicoEntidad(1L, "Pedro", true, "Hardware");

        assertEquals(1L, tecnico.getId());
        assertEquals("Pedro", tecnico.getNombre());
        assertTrue(tecnico.isActivo());
        assertEquals("Hardware", tecnico.getEspecialidad());
    }

    @Test
    void clienteEntidadGetterTipoCliente() {
        ClienteEntidad entidad = new ClienteEntidad(2L, "Carlos", "carlos@mail.com", Cliente.tipoCliente.STANDARD);

        assertEquals(2L, entidad.getId());
        assertEquals("Carlos", entidad.getNombre());
        assertEquals("carlos@mail.com", entidad.getEmail());
        assertEquals(Cliente.tipoCliente.STANDARD, entidad.getTipoCliente());
    }
}
