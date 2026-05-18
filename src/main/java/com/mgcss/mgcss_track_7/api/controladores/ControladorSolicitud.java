package com.mgcss.mgcss_track_7.api.controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.mgcss_track_7.api.dto.SolicitudPeticionDTO;
import com.mgcss.mgcss_track_7.api.dto.SolicitudRespuestaDTO;
import com.mgcss.mgcss_track_7.api.mapper.SolicitudMapeo;
import com.mgcss.mgcss_track_7.domain.Solicitud;
import com.mgcss.mgcss_track_7.domain.Tecnico;
import com.mgcss.mgcss_track_7.infraestrucure.persistence.SolicitudEntidad;
import com.mgcss.mgcss_track_7.service.ServicioSolicitud;
import com.mgcss.mgcss_track_7.service.ServicioTecnico;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/solicitudes")
public class ControladorSolicitud {
    private final ServicioSolicitud servicioSolicitud;
    private final ServicioTecnico servicioTecnico;

    public ControladorSolicitud(ServicioSolicitud servicioSolicitud, ServicioTecnico servicioTecnico) {
        this.servicioSolicitud = servicioSolicitud;
        this.servicioTecnico = servicioTecnico;
    }

    @PostMapping
    public SolicitudRespuestaDTO crearSolicitud(@Valid @RequestBody SolicitudPeticionDTO solicitudPeticionDTO) {
        Solicitud solicitud = servicioSolicitud
                .crearSolicitudSinClienteYdescripcionValida(solicitudPeticionDTO.getDescripcion());
        return SolicitudMapeo.toSolicitudRespuestaDTO(solicitud);
    }

    @GetMapping("/{id}")
    public SolicitudRespuestaDTO obtenerSolicitudPorId(@RequestParam Long id) {
        Optional<Solicitud> solicitud = servicioSolicitud.findById(id);
        if (solicitud.isPresent()) {
            return SolicitudMapeo.toSolicitudRespuestaDTO(solicitud.get());
        } else {
            return null; // O lanzar una excepción personalizada para indicar que la solicitud no fue
                         // encontrada
        }
    }

    @GetMapping
    public List<SolicitudRespuestaDTO> obtenerTodasLasSolicitudes() {
        return servicioSolicitud.findAll().stream()
                .map(SolicitudMapeo::toSolicitudRespuestaDTO)
                .toList();
    }

    @PutMapping("/{id}/estado")
    public SolicitudRespuestaDTO cambiarEstado(@PathVariable Long id) {
        Solicitud solicitudActualizada = servicioSolicitud.cambiarEstado(id);
        return SolicitudMapeo.toSolicitudRespuestaDTO(solicitudActualizada);

    }

    @PutMapping("/{id}/reabrir")
    public SolicitudRespuestaDTO reabrirSolicitud(@PathVariable Long id) {
        Optional<Solicitud> solicitud = servicioSolicitud.findById(id);
        if (solicitud.isPresent()) {
            Solicitud solicitudActualizada = servicioSolicitud.reabrirSolicitud(solicitud.get().getId(),
                    solicitud.get().getTecnicoAsignado());
            return SolicitudMapeo.toSolicitudRespuestaDTO(solicitudActualizada);
        } else {
            throw new IllegalArgumentException("Solicitud no encontrada con id: " + id);
        }
    }

    @PutMapping("/{id}/tecnico")
    public SolicitudRespuestaDTO asignarTecnico(@PathVariable Long id, @RequestBody Long tecnicoId) {
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
