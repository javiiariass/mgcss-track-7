package com.mgcss.mgcss_track_7.unit.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;


import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.domain.Solicitud;


class TecnicoTest {


	@Test
	void testTecnicoGettersSetters() {
		Tecnico tecnico = new Tecnico();
		tecnico.setId(2L);
		tecnico.setNombre("Ana");
		tecnico.setEspecialidad("Redes");
		tecnico.setActivo(true);
		tecnico.setTrabajando(false);

		Solicitud solicitud = new Solicitud();
		solicitud.asignarTecnico(tecnico);


		assertEquals(2L, tecnico.getId());
		assertEquals("Ana", tecnico.getNombre());
		assertEquals("Redes", tecnico.getEspecialidad());
		assertEquals(true, tecnico.isActivo());
		assertEquals(true, tecnico.isTrabajando());
		
	}

	
}
