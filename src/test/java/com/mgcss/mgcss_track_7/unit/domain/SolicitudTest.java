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

    @Test
    void testReabrirSolicitud() {
        Tecnico tecnicoActivo = new Tecnico(1L, "Juan", "Electricista");
        Cliente cliente = new Cliente();
        Solicitud solicitud = new Solicitud(1l, cliente, "Descripcion", tecnicoActivo);

        solicitud.siguienteEstado();
        solicitud.siguienteEstado();

        // Solicitud cerrada, reabrimos
        solicitud.reabrir(tecnicoActivo);
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getEstado());

    }

    @Test
    void testReabrirSolicitudExcepcion() {
        Tecnico tecnicoActivo = new Tecnico(1L, "Juan", "Electricista");
        Cliente cliente = new Cliente();
        Solicitud solicitud = new Solicitud(1l, cliente, "Descripcion", tecnicoActivo);

        solicitud.siguienteEstado();
        solicitud.siguienteEstado();

        tecnicoActivo.setActivo(false);

        // Solicitud cerrada, reabrimos (debe fallar porque el técnico está inactivo)
        assertThrows(IllegalArgumentException.class, () -> solicitud.reabrir(tecnicoActivo));
    }

    @Test
    void testReabrirSolicitudNoCerrada() {
        Tecnico tecnicoActivo = new Tecnico(1L, "Juan", "Electricista");
        Cliente cliente = new Cliente();
        Solicitud solicitud = new Solicitud(1l, cliente, "Descripcion", tecnicoActivo);
        solicitud.reabrir(tecnicoActivo);

        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, solicitud.getEstado());
    }

    @Test
    void testHistoricoEstados() {
        Tecnico tecnicoActivo = new Tecnico(1L, "Juan", "Electricista");
        Cliente cliente = new Cliente();
        Solicitud solicitud = new Solicitud(1l, cliente, "Descripcion", tecnicoActivo);

        solicitud.siguienteEstado();
        solicitud.siguienteEstado();
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, solicitud.getEstado().siguiente());
        solicitud.reabrir(tecnicoActivo);

        assertEquals(4, solicitud.getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, solicitud.getHistorico().get(0));
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getHistorico().get(1));
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, solicitud.getHistorico().get(2));
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getHistorico().get(3));
    }

    @Test
    void testConstructorInicializaFechasCierreConClientePremium() {
        Tecnico tecnico = new Tecnico(1L, "Ana", "Redes");
        Cliente clientePremium = new Cliente();
        clientePremium.setTipo(Cliente.tipoCliente.PREMIUM);

        Date antes = new Date();
        Solicitud solicitud = new Solicitud(10L, clientePremium, "Fallo premium", tecnico);

        assertEquals(24 * Solicitud.DIA_EN_MILISEGUNDOS, solicitud.getTiempoResolucionDias());
        // fechaCierre debe estar entre (antes + plazo) y (despues + plazo)
        long plazo = solicitud.getTiempoResolucionDias();
        assertEquals(true, !solicitud.getFechaCierre().before(new Date(antes.getTime() + plazo)));
        assertEquals(1, solicitud.getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, solicitud.getHistorico().get(0));
    }

    @Test
    void testConstructorInicializaFechasCierreConClienteEstandar() {
        Tecnico tecnico = new Tecnico(2L, "Luis", "Hardware");
        Cliente clienteEstandar = new Cliente();
        // tipoCliente distinto de PREMIUM -> 48 dias

        Solicitud solicitud = new Solicitud(1L, clienteEstandar, "Fallo estandar", tecnico);

        assertEquals(48 * Solicitud.DIA_EN_MILISEGUNDOS, solicitud.getTiempoResolucionDias());
        assertEquals(1, solicitud.getHistorico().size());
    }

    @Test
    void siguienteEstadoTransicionesCompletas() {
        Solicitud solicitud = new Solicitud();

        assertEquals(Solicitud.estadoSolicitudes.ABIERTA, solicitud.getEstado());

        solicitud.siguienteEstado(); // ABIERTA -> EN_PROCESO
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getEstado());

        solicitud.siguienteEstado(); // EN_PROCESO -> CERRADA (via cerrar())
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, solicitud.getEstado());
    }

    @Test
    void siguienteEstadoActualizaHistorico() {
        Solicitud solicitud = new Solicitud();
        // historico inicial: [ABIERTA]
        assertEquals(1, solicitud.getHistorico().size());

        solicitud.siguienteEstado(); // -> EN_PROCESO
        assertEquals(2, solicitud.getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getHistorico().get(1));

        solicitud.siguienteEstado(); // -> CERRADA
        assertEquals(3, solicitud.getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, solicitud.getHistorico().get(2));
    }

    @Test
    void asignarTecnico_TecnicoYaEstabaTrabajando() {
        Solicitud solicitud = new Solicitud();
        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);
        tecnico.setTrabajando(true); // ya ocupado

        assertEquals(false, solicitud.asignarTecnico(tecnico));
    }

    @Test
    void cerrarLiberaTecnicoAsignado() {
        Tecnico tecnico = new Tecnico(3L, "Pedro", "Sistemas");
        tecnico.setActivo(true);
        tecnico.setTrabajando(false);

        Solicitud solicitud = new Solicitud();
        solicitud.asignarTecnico(tecnico);
        solicitud.siguienteEstado(); // -> EN_PROCESO

        assertEquals(true, tecnico.isTrabajando());

        solicitud.cerrar();

        assertEquals(false, tecnico.isTrabajando());
        assertEquals(null, solicitud.getTecnicoAsignado());
        assertEquals(Solicitud.estadoSolicitudes.CERRADA, solicitud.getEstado());
    }

    @Test
    void testReabrirActualizaFechasYHistorico() {
        Tecnico tecnico = new Tecnico(4L, "Marta", "Redes");
        Cliente cliente = new Cliente();
        Solicitud solicitud = new Solicitud(20L, cliente, "Corte de red", tecnico);

        solicitud.siguienteEstado(); // -> EN_PROCESO
        solicitud.siguienteEstado(); // -> CERRADA

        Tecnico tecnicoNuevo = new Tecnico(5L, "Sergio", "Redes");
        tecnicoNuevo.setActivo(true);
        tecnicoNuevo.setTrabajando(false);

        Date antesReapertura = new Date();
        solicitud.reabrir(tecnicoNuevo);
        Date despuesReapertura = new Date();

        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getEstado());
        assertEquals(tecnicoNuevo, solicitud.getTecnicoAsignado());

        // fechaReapertura debe estar en el rango de la llamada
        assertEquals(false, solicitud.getFechaReapertura().before(antesReapertura));
        assertEquals(false, solicitud.getFechaReapertura().after(despuesReapertura));

        // nueva fechaCierre = fechaReapertura + tiempoResolucionDias
        long nuevoCierre = solicitud.getFechaReapertura().getTime() + solicitud.getTiempoResolucionDias();
        assertEquals(nuevoCierre, solicitud.getFechaCierre().getTime());

        // historico: ABIERTA, EN_PROCESO, CERRADA, EN_PROCESO
        assertEquals(4, solicitud.getHistorico().size());
        assertEquals(Solicitud.estadoSolicitudes.EN_PROCESO, solicitud.getHistorico().get(3));
    }

}
