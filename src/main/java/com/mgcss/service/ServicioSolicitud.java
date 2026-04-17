package com.mgcss.service;
import com.mgcss.infraestrucure.SolicitudInterfaz;
import com.mgcss.domain.Solicitud;

public class ServicioSolicitud implements SolicitudInterfaz {

 public boolean cerrarSolicitud(Solicitud solicitud) {
        if (solicitud.getEstado() == Solicitud.estadoSolicitudes.ABIERTA)
            return false;

        solicitud.setEstado(Solicitud.estadoSolicitudes.CERRADA);
        return true;
    }
 
}