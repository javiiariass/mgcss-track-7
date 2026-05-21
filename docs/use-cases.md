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
**Response esperado:** `400` / `500` (Dependiendo del GlobalExceptionHandler)
```json
{
  "error": "IllegalArgumentException",
  "message": "No enum constant com.mgcss.mgcss_track_7.domain.Cliente.tipoCliente.GOLD"
}
```

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
**Precondición:** Solicitud con ID `1` creada. Técnico con ID `1` creado, con estado activo y no trabajando.
**Request:**
```http
PUT /api/solicitudes/1/tecnico
```
```json
1
```
**Response esperado:** `200`
```json
{
  "id": 1,
  "descripcion": "Fallo en la conexión del servidor principal",
  "estado": "ABIERTA",
  "tecnicoAsignado": "1"
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
  "tecnicoAsignado": "1"
}
```

## Caso 6 - Error al Reabrir Solicitud con Técnico No Disponible (Restricción de Negocio)
**Precondición:** Solicitud con ID `2` en estado `CERRADA`. Técnico originalmente asignado ahora está marcado como inactivo (`activo = false`) o está `trabajando = true` en otra solicitud.
**Request:**
```http
PUT /api/solicitudes/2/reabrir
```
*(Sin body)*
**Response esperado:** `400` / `500`
```json
{
  "error": "IllegalArgumentException",
  "message": "El técnico proporcionado no está disponible (inactivo o ya se encuentra trabajando)."
}
```