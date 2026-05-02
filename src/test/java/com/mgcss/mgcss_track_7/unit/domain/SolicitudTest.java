package com.mgcss.mgcss_track_7.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import java.util.Date;

import com.mgcss.mgcss_track_7.domain.Cliente;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.domain.Solicitud;

class SolicitudTest {

    @Test
    void cerrarSolicitud() {        
        // flujo correcto 
        Solicitud solicitud2 = new Solicitud();
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, solicitud2.getEstado());
        solicitud2.siguienteEstado();
        solicitud2.cerrar();
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, solicitud2.getEstado());

        // flujo incorrecto
        Solicitud solicitud3 = new Solicitud();
        assertThrows(IllegalStateException.class, solicitud3::cerrar);

    }

    @Test
    void asignarTecnico_NoActivo_y_Activo() {
        Solicitud solicitud = new Solicitud();
        Tecnico tecnicoActivo = new Tecnico();
        assertEquals(false, solicitud.asignarTecnico(tecnicoActivo));
        tecnicoActivo.setActivo(true);
        assertEquals(true, solicitud.asignarTecnico(tecnicoActivo));
    }

    @Test
    void asignarTecnico_Activo_Ya_Asignado() {
        Solicitud solicitud = new Solicitud();
        Tecnico tecnico1 = new Tecnico();
        Tecnico tecnico2 = new Tecnico();
        tecnico1.setActivo(true);
        tecnico2.setActivo(true);
        solicitud.asignarTecnico(tecnico2);
        assertEquals(false, solicitud.asignarTecnico(tecnico1));
        System.out.println(
                "***************************************************************************************************");
    }

    @Test
    void asignarTecnico_Doble_Solicitud() {
        Solicitud solicitud = new Solicitud(2L, new Cliente(), "", null);
        Solicitud solicitud2 = new Solicitud();
        Tecnico tecnicoActivo = new Tecnico();
        tecnicoActivo.setActivo(true);
        solicitud.asignarTecnico(tecnicoActivo);
        assertEquals(false, solicitud2.asignarTecnico(tecnicoActivo));
        System.out.println(
                "***************************************************************************************************");
    }

    @Test
    void testSolicitudGettersSetters() {
        Solicitud solicitud = new Solicitud();
        Cliente cliente = new Cliente();
        Date fecha = new Date();

        solicitud.setId(100L);
        solicitud.setCliente(cliente);
        solicitud.setDescripcion("Error de software");
        solicitud.asignarTecnico(null);
        solicitud.setFechaCreacion(fecha);
        solicitud.setFechaCierre(fecha);

        assertEquals(100L, solicitud.getId());
        assertEquals(cliente, solicitud.getCliente());
        assertEquals("Error de software", solicitud.getDescripcion());
        assertEquals(null, solicitud.getTecnicoAsignado());
        assertEquals(fecha, solicitud.getFechaCreacion());
        assertEquals(fecha, solicitud.getFechaCierre());
    }

    @Test
    void testSolicitudTecnicoNULL() {
        Tecnico tecnicoActivo = new Tecnico(1L, "Juan", "Electricista");
        Cliente cliente = new Cliente();
        Solicitud solicitud = new Solicitud(1l, cliente, "Descripcion", tecnicoActivo);
        assertEquals(true, solicitud.asignarTecnico(null));
    }

}
