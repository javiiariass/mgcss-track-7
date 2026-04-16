package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import java.util.Date;
import org.springframework.boot.test.context.SpringBootTest;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.Solicitud;

@SpringBootTest
class ApplicationTests {

	@Test
	void dominioTest() {
		// Probamos instanciar las clases para darles cobertura
		Cliente cliente = new Cliente();
		cliente.setId(1L);
		cliente.setNombre("Paco");
		assertEquals(1L, cliente.getId());
		assertEquals("Paco", cliente.getNombre());

		Tecnico tecnico = new Tecnico(1L, "María", "Hardware");
		assertEquals("María", tecnico.getNombre());

		Solicitud solicitud = new Solicitud(1L, cliente, "Problema PC", tecnico);
		assertEquals("Problema PC", solicitud.getDescripcion());
		assertNotNull(solicitud.getFechaCreacion());
		assertEquals(cliente, solicitud.getCliente());
	}

	@Test
	void cerrarSolicitud_no_En_proceso() {
		// Preparar
		Solicitud solicitud = new Solicitud();

		boolean resultado = solicitud.cerrarSolicitud();
		System.out.println("**************************************" + resultado);
		// Comprobar
		assertEquals(false, resultado);
	}

	@Test
	void cerrarSolicitud_En_proceso() {
		// Preparar
		Solicitud solicitud = new Solicitud();
		solicitud.setEstado(Solicitud.estadoSolicitudes.EN_PROCESO);

		boolean resultado = solicitud.cerrarSolicitud();
		System.out.println("**************************************" + resultado);
		// Comprobar
		assertEquals(true, resultado);
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
		Solicitud solicitud = new Solicitud();
		Solicitud solicitud2 = new Solicitud();
		Tecnico tecnicoActivo = new Tecnico();
		tecnicoActivo.setActivo(true);
		solicitud.asignarTecnico(tecnicoActivo);
		assertEquals(false, solicitud2.asignarTecnico(tecnicoActivo));
		System.out.println(
				"***************************************************************************************************");
	}

	/**************************************** */
	// TEST GETTER/SETTERS
	@Test
	void testClienteGettersSetters() {
		Cliente cliente = new Cliente();
		cliente.setId(10L);
		cliente.setNombre("Juan Pérez");

		assertEquals(10L, cliente.getId());
		assertEquals("Juan Pérez", cliente.getNombre());
	}

	@Test
	void testTecnicoGettersSetters() {
		Tecnico tecnico = new Tecnico();
		tecnico.setId(2L);
		tecnico.setNombre("Ana");
		tecnico.setEspecialidad("Redes");
		tecnico.setActivo(true);

		Solicitud solicitud = new Solicitud();
		tecnico.setSolicitud(solicitud);

		assertEquals(2L, tecnico.getId());
		assertEquals("Ana", tecnico.getNombre());
		assertEquals("Redes", tecnico.getEspecialidad());
		assertEquals(true, tecnico.getActivo());
		assertEquals(solicitud, tecnico.getSolicitud());
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
