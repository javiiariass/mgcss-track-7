# Release Notes

## Estrategia de versionado

Este proyecto sigue **Versionado Semantico (SemVer)**: `MAJOR.MINOR.PATCH`

| Componente | Significado | Ejemplo |
|---|---|---|
| MAJOR | Cambios incompatibles con la version anterior | Cambio de API REST, eliminacion de endpoints |
| MINOR | Nueva funcionalidad compatible hacia atras | Nuevo endpoint, nueva entidad |
| PATCH | Correcciones de errores y mejoras menores | Bugfix, typo, refactor sin impacto funcional |

## Historial de versiones

### v1.0.0 - Primera Release Estable

Primera version estable del sistema de gestion de solicitudes.

**Funcionalidades:**
- Modelo de dominio: Solicitud, Tecnico, Cliente con reglas de negocio
- Gestion de estados (ABIERTA -> EN_PROCESO -> CERRADA) con metodo `siguienteEstado()`
- Logica de reabrir solicitudes cerradas con reasignacion de tecnico
- Historico de cambios de estado
- Tiempo de resolucion diferenciado (24 dias PREMIUM, 48 dias STANDARD)
- Capa de persistencia JPA (PostgreSQL + H2 para tests)
- Capa API REST con controladores, DTOs y mappers
- Swagger/OpenAPI integrado
- Pipeline CI con SonarCloud + JaCoCo
- Conventional Commits

### v1.0.1 - Integracion Docker

**Tipo de cambio:** PATCH

**Cambios:**
- Dockerfile basado en `eclipse-temurin:17-jre-alpine`
- `docker-compose.yml` con servicio app + PostgreSQL
- Imagen publicada en DockerHub (`adrianma13/mgcss-track-7`)

**Justificacion PATCH:** Cambio de infraestructura/build sin modificacion funcional ni de API.

### v1.0.2 - Correcciones menores

**Tipo de cambio:** PATCH

**Commits desde v1.0.1:**
- `refactor(structure): typo en structure` - Correccion menor

**Justificacion PATCH:**
- No hay nuevas funcionalidades (descarta MINOR)
- No hay cambios incompatibles (descarta MAJOR)
- Solo contiene una correccion menor de refactorizacion

## Proceso de release

1. Los commits siguen Conventional Commits (`feat:`, `fix:`, `refactor:`, etc.)
2. Se crea un tag con formato `vMAJOR.MINOR.PATCH`
3. El push del tag dispara el workflow `release.yml`
4. El workflow ejecuta Quality Gate (tests + Sonar) antes de liberar
5. Se genera artefacto `.jar`, imagen Docker y Release en GitHub
6. Nunca se libera una version si el Quality Gate no pasa
