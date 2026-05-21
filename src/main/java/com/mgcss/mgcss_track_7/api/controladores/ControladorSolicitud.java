package com.mgcss.mgcss_track_7.api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.mgcss_track_7.api.dto.SolicitudPeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.SolicitudRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.SolicitudMapeo;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.service.ServicioSolicitud;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Solicitudes", description = "Gestión de solicitudes de soporte técnico")
@RestController
@RequestMapping("/api/solicitudes")
public class ControladorSolicitud {
    private final ServicioSolicitud servicioSolicitud;
    private final ServicioTecnico servicioTecnico;

    public ControladorSolicitud(ServicioSolicitud servicioSolicitud, ServicioTecnico servicioTecnico) {
        this.servicioSolicitud = servicioSolicitud;
        this.servicioTecnico = servicioTecnico;
    }

    @Operation(summary = "Crear una solicitud", description = "Crea una nueva solicitud de soporte con estado ABIERTA a partir de una descripción")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Solicitud creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Descripción inválida o vacía")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudRespuestaDTO crearSolicitud(@Valid @RequestBody SolicitudPeticionDTO solicitudPeticionDTO) {
        Solicitud solicitud = servicioSolicitud
                .crearSolicitudSinClienteYdescripcionValida(solicitudPeticionDTO.getDescripcion());
        return SolicitudMapeo.toSolicitudRespuestaDTO(solicitud);
    }

    @Operation(summary = "Obtener solicitud por ID", description = "Devuelve los datos de una solicitud a partir de su identificador")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud encontrada o null si no existe")
    })
    @GetMapping("/{id}")
    public SolicitudRespuestaDTO obtenerSolicitudPorId(
            @Parameter(description = "ID de la solicitud", example = "1") @PathVariable Long id) {
        Optional<Solicitud> solicitud = servicioSolicitud.findById(id);
        if (solicitud.isPresent()) {
            return SolicitudMapeo.toSolicitudRespuestaDTO(solicitud.get());
        } else {
            return null;
        }
    }

    @Operation(summary = "Obtener todas las solicitudes", description = "Devuelve la lista completa de solicitudes registradas en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de solicitudes")
    @GetMapping
    public List<SolicitudRespuestaDTO> obtenerTodasLasSolicitudes() {
        return servicioSolicitud.findAll().stream()
                .map(SolicitudMapeo::toSolicitudRespuestaDTO)
                .toList();
    }

    @Operation(summary = "Avanzar estado de una solicitud (PATCH)", description = "Avanza la solicitud al siguiente estado: ABIERTA → EN_PROCESO → CERRADA")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud no encontrada")
    })
    @PatchMapping("/{id}")
    public SolicitudRespuestaDTO cambiarEstadoPatch(
            @Parameter(description = "ID de la solicitud", example = "1") @PathVariable Long id) {
        Optional<Solicitud> opt = servicioSolicitud.findById(id);
        if (opt.isPresent()) {
            Solicitud solicitud = opt.get();
            solicitud.siguienteEstado();
            servicioSolicitud.save(solicitud);
            return SolicitudMapeo.toSolicitudRespuestaDTO(solicitud);
        }
        throw new IllegalArgumentException("Solicitud no encontrada con id: " + id);
    }

    @Operation(summary = "Avanzar estado de una solicitud (PUT)", description = "Avanza la solicitud al siguiente estado usando el servicio de cambio de estado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente")
    })
    @PutMapping("/{id}/estado")
    public SolicitudRespuestaDTO cambiarEstado(
            @Parameter(description = "ID de la solicitud", example = "1") @PathVariable Long id) {
        Solicitud solicitudActualizada = servicioSolicitud.cambiarEstado(id);
        return SolicitudMapeo.toSolicitudRespuestaDTO(solicitudActualizada);
    }

    @Operation(summary = "Reabrir una solicitud", description = "Reabre una solicitud CERRADA asignándola al técnico que tenía previamente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Solicitud reabierta correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}/reabrir")
    public SolicitudRespuestaDTO reabrirSolicitud(
            @Parameter(description = "ID de la solicitud", example = "1") @PathVariable Long id) {
        Optional<Solicitud> solicitud = servicioSolicitud.findById(id);
        if (solicitud.isPresent()) {
            Solicitud solicitudActualizada = servicioSolicitud.reabrirSolicitud(solicitud.get().getId(),
                    solicitud.get().getTecnicoAsignado());
            return SolicitudMapeo.toSolicitudRespuestaDTO(solicitudActualizada);
        } else {
            throw new IllegalArgumentException("Solicitud no encontrada con id: " + id);
        }
    }

    @Operation(summary = "Asignar técnico a una solicitud", description = "Asigna un técnico activo y disponible a la solicitud indicada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Técnico asignado correctamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud no encontrada")
    })
    @PutMapping("/{id}/tecnico")
    public SolicitudRespuestaDTO asignarTecnico(
            @Parameter(description = "ID de la solicitud", example = "1") @PathVariable Long id,
            @Parameter(description = "ID del técnico a asignar", example = "2") @RequestBody Long tecnicoId) {
        Optional<Solicitud> solicitud = servicioSolicitud.findById(id);
        Optional<Tecnico> tecnico = servicioTecnico.findById(tecnicoId);
        if (solicitud.isPresent()) {
            Solicitud solicitudActualizada = servicioSolicitud.asignarTecnico(solicitud.get().getId(), tecnico.get());
            return SolicitudMapeo.toSolicitudRespuestaDTO(solicitudActualizada);
        } else {
            throw new IllegalArgumentException("Solicitud no encontrada con id: " + id);
        }
    }
}
