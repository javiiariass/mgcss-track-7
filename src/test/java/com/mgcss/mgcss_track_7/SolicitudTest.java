package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;
import java.util.Date;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Solicitud;



@SpringBootTest
class SolicitudTest {

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
        Solicitud solicitud = new Solicitud(1l,"Descripcion", Solicitud.estadoSolicitudes.ABIERTA);
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
		Tecnico tecnico = new Tecnico();
		Date fecha = new Date();

		solicitud.setId(100L);
		solicitud.setCliente(cliente);
		solicitud.setDescripcion("Error de software");
		solicitud.setTecnicoAsignado(tecnico);
		solicitud.setFechaCreacion(fecha);
		solicitud.setFechaCierre(fecha);
		solicitud.setEstado(Solicitud.estadoSolicitudes.EN_PROCESO);

		assertEquals(100L, solicitud.getId());
		assertEquals(cliente, solicitud.getCliente());
		assertEquals("Error de software", solicitud.getDescripcion());
		assertEquals(tecnico, solicitud.getTecnicoAsignado());
		assertEquals(fecha, solicitud.getFechaCreacion());
		assertEquals(fecha, solicitud.getFechaCierre());
		assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getEstado());
	}

}
