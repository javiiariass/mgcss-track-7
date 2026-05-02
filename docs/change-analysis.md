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
- Se debe extender el modelo con una nueva entidad (`HistorialEstado`).
- Esta nueva entidad debe reflejar las propiedades: `fechaDeCambio`, `estadoAnterior`, `estadoNuevo` y una relación hacia la `Solicitud`.
- La entidad `Solicitud` necesita incorporar una colección (ej. `List<HistorialEstado>`) para almacenar el registro de sus propios cambios.

### 5. ¿Qué impacto tiene en persistencia?
- **Base de datos:** Será necesaria la creación de una tabla adicional asociada (ej. `historial_estados`) con una clave foránea (Foreign Key) apuntando a `Solicitud`.
- **Mapeo ORM/JPA:** Se deberá añadir una relación `@OneToMany` en la entidad `Solicitud` para persistir los historiales.
- **Transaccionalidad:** Los cambios de estado requerirán un guardado que involucre tanto actualizar la `Solicitud` como insertar en la nueva tabla de historial.


# Notas de cambios

## Justificación estructura histórico
Hemos usado un arrayList() porque, dada la complejidad del proyecto y la duración del mismo, preferimos regirnos por el principio KISS. 