# Análisis de Impacto: Reabrir Solicitudes e Histórico de Estados

**Contexto del Cambio:** Nuevo requisito que permite reabrir una solicitud tras estar cerrada y obliga a mantener un historial de cambios, sin romper comportamiento previo.

### 1. ¿Qué métodos del dominio se ven afectados?
- La entidad `Solicitud` necesitará actualizar su comportamiento al modificar el estado. Cada vez que cambie, debe registrarse este evento para mantener el histórico.
- El servicio `ServicioSolicitud` necesitará gestionar la orquestación del cambio.
  - **Nuevo método**: `reabrir()` que cambia el estado de la `solicitud` de `CERRADA` a `ABIERTA` y registra el cambio en el histórico.

### 2. ¿Qué reglas actuales cambian?
- Se elimina la restricción que impedía que una solicitud pasara del estado `CERRADA` a `ABIERTA` (el cambio introducido recientemente para evitar bucles de estado).
- **Nueva regla**: Todo cambio de estado (incluyendo reabrir) debe generar una entrada en un historial de estados, asegurando trazabilidad completa.

### 3. ¿Qué tests deberían romperse?
- Ninguno de nuestros test debería romperse.

### 4. ¿Qué parte del modelo debe extenderse?
- La entidad `Solicitud` incorpora una colección `List<estadoSolicitudes> historico` que guarda la secuencia de estados por los que pasa la solicitud.
- Cada vez que cambia el estado (`siguienteEstado()`, `cerrar()`, `reabrir()`) se llama a `actualizaHistorico()`, que añade el nuevo estado a la lista.
- Se valoró crear una entidad separada `HistorialEstado` con propiedades como `fechaDeCambio`, `estadoAnterior` y `estadoNuevo`, pero finalmente se descartó (ver "Notas de cambios").

### 5. ¿Qué impacto tiene en persistencia?
- **Base de datos:** El histórico se persiste en una tabla secundaria `solicitud_historial` con una clave foránea (`solicitud_id`) hacia la solicitud, generada automáticamente por JPA.
- **Mapeo ORM/JPA:** En `SolicitudEntidad` el histórico se mapea con `@ElementCollection` + `@CollectionTable(name = "solicitud_historial")`, almacenando cada estado como cadena (`@Enumerated(EnumType.STRING)`). No es una entidad propia, sino una colección de valores.
- **Transaccionalidad:** Al guardar la solicitud, JPA persiste también su colección de estados en la misma operación.


# Notas de cambios

## Justificación estructura histórico
Hemos usado una `List<estadoSolicitudes>` (un `ArrayList` en el dominio, persistido con `@ElementCollection`) en lugar de una entidad separada `HistorialEstado`. Dada la complejidad y la duración del proyecto, preferimos regirnos por el principio **KISS**: la lista cubre el requisito de mantener la trazabilidad de los estados sin añadir una tabla, relaciones `@OneToMany` ni mapeos adicionales que aumentarían la deuda técnica. Como contrapartida, no guardamos la fecha exacta de cada transición ni el estado anterior/nuevo por separado; si en el futuro se necesitara ese detalle, se migraría a la entidad `HistorialEstado`.