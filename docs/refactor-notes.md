# Documento de Refactorización

Refactorizaciones aplicadas durante el proyecto, guiadas por el análisis de SonarCloud.
Para cada una se indica el problema, la métrica asociada, el riesgo de no corregirlo, la
técnica aplicada y el resultado. En todas ellas se mantuvieron los tests en verde (no se
modificó el comportamiento observable).

## 1. Eliminación de getters/setters manuales con Lombok (30/04/2026)

- **Problema:** Las entidades `ClienteEntidad`, `TecnicoEntidad` y `SolicitudEntidad` (y las
  clases de dominio) tenían getters y setters escritos a mano, muy repetitivos.
- **Métrica asociada:** Duplicación de código (Sonar marcaba bloques duplicados por el
  boilerplate repetido en varias clases).
- **Riesgo si no se corrige:** Más código que mantener, mayor probabilidad de inconsistencias
  al añadir o cambiar campos, y duplicación creciente penalizando la mantenibilidad.
- **Técnica aplicada:** *Encapsulate Field* / eliminación de código repetido generándolo
  automáticamente con las anotaciones de Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`).
- **Resultado:** Menos líneas de código repetido y duplicación baja. Como contrapartida se
  introdujo la dependencia de Lombok, asumida conscientemente.

## 2. Simplificación de expresiones booleanas (dominio)

- **Problema:** Code smells reportados por Sonar en condiciones del tipo
  `tecnico.getActivo() == true` y uso de `Boolean` envuelto donde bastaba un `boolean`
  primitivo en `asignarTecnico()`.
- **Métrica asociada:** Code Smells.
- **Riesgo si no se corrige:** Código menos legible y propenso a errores (comparaciones
  redundantes, posibles `NullPointerException` con wrappers).
- **Técnica aplicada:** Uso de expresiones booleanas primitivas directas
  (`if (tecnico.isActivo() && ...)`), eliminando la comparación con `true`.
- **Resultado:** Code smells eliminados y condicionales más claros.

## 3. Mejora del manejo de `Optional` en los servicios

- **Problema:** Tratamiento poco idiomático de `Optional` en los servicios de Técnico.
- **Métrica asociada:** Code Smells / mantenibilidad.
- **Riesgo si no se corrige:** Código frágil ante valores ausentes y más difícil de leer.
- **Técnica aplicada:** Refactor del flujo de `Optional` para un manejo más expresivo y seguro.
- **Resultado:** Servicios más legibles y sin smells asociados.

## 4. Refactor de la entidad `Solicitud` y sus tests siguiendo Sonar

- **Problema:** Sugerencias de SonarCloud sobre la clase central `Solicitud` y duplicación en
  los tests del dominio.
- **Métrica asociada:** Code Smells y duplicación.
- **Riesgo si no se corrige:** La entidad central acumularía deuda, complicando los cambios
  futuros (justo lo que se evalúa en la gestión del cambio).
- **Técnica aplicada:** Simplificación de la lógica y reorganización/limpieza de los tests
  (reducción de código duplicado en las pruebas).
- **Resultado:** Menos duplicación en tests y la clase central más limpia de cara a la
  Entrega 4 (reabrir + histórico).

## Resultado global (estado final en SonarCloud)

- Cobertura: **98,8 %**
- Bugs: **0** · Vulnerabilidades: **0**
- Code Smells: **12** (ninguno crítico)
- Duplicación: **2,6 %**
- Deuda técnica: **~24 min** (ratio **0,1 %**)
- Maintainability / Reliability / Security Rating: **A / A / A**
- Quality Gate: **PASSED**

## Situación del proyecto

Durante el desarrollo hemos evitado acumular deuda técnica, refactorizando de forma
incremental cada vez que el análisis estático señalaba un problema y manteniendo siempre el
pipeline y los tests en verde.
