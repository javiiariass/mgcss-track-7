# Documento de Refactorización
## Refactorización 30/04/2026

- Se eliminaron los metodos get y set de las clases TecnicoEntidad, SolicitudEntidad y ClienteEntidad
- Se utilizaron lombok para generarlos automaticamente
- Riesgos: La existencia de duplicidad de codigo en el SonarCloud

## Situacion del proyecto
- Durante el desarrollo del proyecto hemos evitado tener deuda técnica. 
