package com.mgcss.mgcss_track_7;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
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
	void asignarTecnico_NoActivo_y_Activo(){
		Solicitud solicitud = new Solicitud();
		Tecnico t_activo=new Tecnico();
		assertEquals(solicitud.asignarTecnico(t_activo), false);
		t_activo.setActivo(true);
		assertEquals(solicitud.asignarTecnico(t_activo), true);
	}

	@Test
	void asignarTecnico_Activo_Ya_Asignado(){
		Solicitud solicitud = new Solicitud();
		Tecnico t_activo=new Tecnico();
		Tecnico t_buffer = new Tecnico();
		t_activo.setActivo(true);
		t_buffer.setActivo(true);
		solicitud.asignarTecnico(t_buffer);
		assertEquals(solicitud.asignarTecnico(t_activo), false);
		System.out.println("***************************************************************************************************");
	}

	@Test
	void asignarTecnico_Doble_Solicitud(){
		Solicitud solicitud = new Solicitud();
		Solicitud solicitud2 = new Solicitud();
		Tecnico t_activo=new Tecnico();
		t_activo.setActivo(true);
		solicitud.asignarTecnico(t_activo);
		assertEquals(solicitud2.asignarTecnico(t_activo), false);
		System.out.println("***************************************************************************************************");
	}
}
