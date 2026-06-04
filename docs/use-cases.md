# Casos de Prueba de la API REST (Swagger UI)
## Caso 1 - Crear un nuevo Cliente (Happy Path)
**Precondición:** Ninguna. Base de datos operativa.
**Request:**
```http
POST /api/clientes
```
```json
{
  "nombre": "Acme Corp",
  "email": "contacto@acmecorp.com",
  "tipo": "PREMIUM"
}
```
**Response esperado:** `200`
```json
{
  "id": 1,
  "nombre": "Acme Corp",
  "email": "contacto@acmecorp.com",
  "tipo": "PREMIUM"
}
```

## Caso 2 - Error por Tipo de Cliente Inválido
**Precondición:** Ninguna.
**Request:**
```http
POST /api/clientes
```
```json
{
  "nombre": "Empresa Falsa",
  "email": "error@empresa.com",
  "tipo": "GOLD"
}
```
**Response esperado:** `500 Internal Server Error`

El controlador convierte el tipo con `tipoCliente.valueOf("GOLD")`, que lanza una
`IllegalArgumentException` porque `GOLD` no es un valor válido (`STANDARD`/`PREMIUM`).
Como el proyecto **no** define un manejador global de excepciones
(`@RestControllerAdvice`), Spring Boot devuelve su respuesta de error por defecto con
código `500`. Por defecto el cuerpo no incluye el mensaje de la excepción:
```json
{
  "timestamp": "2026-06-04T10:00:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/clientes"
}
```
Causa interna (visible en los logs): `No enum constant com.mgcss.mgcss_track_7.domain.Cliente.tipoCliente.GOLD`.

## Caso 3 - Crear una Solicitud de Soporte
**Precondición:** Sistema preparado, no se requiere configuración previa.
**Request:**
```http
POST /api/solicitudes
```
```json
{
  "descripcion": "Fallo en la conexión del servidor principal"
}
```
**Response esperado:** `201`
```json
{
  "id": 1,
  "descripcion": "Fallo en la conexión del servidor principal",
  "estado": "ABIERTA",
  "tecnicoAsignado": null
}
```

## Caso 4 - Asignar Técnico Disponible a una Solicitud
**Precondición:** Solicitud con ID `1` creada. Técnico con ID `1` y nombre `Juan Pérez` creado, con estado activo y no trabajando.
**Request:**
```http
PUT /api/solicitudes/1/tecnico
```
```json
1
```
**Response esperado:** `200`

> Nota: el campo `tecnicoAsignado` de la respuesta contiene el **nombre** del técnico (lo que devuelve el mapper), no su id.
```json
{
  "id": 1,
  "descripcion": "Fallo en la conexión del servidor principal",
  "estado": "ABIERTA",
  "tecnicoAsignado": "Juan Pérez"
}
```

## Caso 5 - Avanzar Estado de la Solicitud
**Precondición:** Solicitud con ID `1` en estado `ABIERTA`.
**Request:**
```http
PATCH /api/solicitudes/1
```
*(Sin body o body vacío)*
**Response esperado:** `200`
```json
{
  "id": 1,
  "descripcion": "Fallo en la conexión del servidor principal",
  "estado": "EN_PROCESO",
  "tecnicoAsignado": "Juan Pérez"
}
```

## Caso 6 - Error al Reabrir Solicitud con Técnico No Disponible (Restricción de Negocio)
**Precondición:** Solicitud con ID `2` en estado `CERRADA`. Técnico originalmente asignado ahora está marcado como inactivo (`activo = false`) o está `trabajando = true` en otra solicitud.
**Request:**
```http
PUT /api/solicitudes/2/reabrir
```
*(Sin body)*
**Response esperado:** `500 Internal Server Error`

El método `reabrir()` del dominio lanza una `IllegalArgumentException` cuando el técnico
no está disponible. Al no existir un manejador global de excepciones, Spring Boot devuelve
su respuesta de error por defecto con código `500`:
```json
{
  "timestamp": "2026-06-04T10:00:00.000+00:00",
  "status": 500,
  "error": "Internal Server Error",
  "path": "/api/solicitudes/2/reabrir"
}
```
Causa interna (visible en los logs): `El técnico proporcionado no está disponible (inactivo o ya se encuentra trabajando).`