# BMC322-BIO-MS-Validacion

[Backend] Biometria - Microservicio Validacion

[![Quality Gate Status](https://newsonar.corp.globant.com/api/project_badges/measure?project=BMC322-BIO-MS-Validacion&metric=alert_status)](https://newsonar.corp.globant.com/dashboard?id=BMC322-BIO-MS-Validacion)

## Descripción General

Este servicio sirve para concentrar operaciones vinculadas a la gestión de Validaciones biometricas.

Contempla servicios para:

### Daon
- Consultar request tracking UID
- Validar Huella en Daon
- Proceso de validación de huellas uno a pocos en Daon

### Enrolamiento
- Agregar DNI a perfil de Usuario
- Consultar Enrolamiento

### FIDO
- Crear Registracion
- Procesar Registracion
- Crear Autenticacion
- Validar Autenticacion
- Obtener Request de Authenticación
- Lista autenticadores de usuario
- Archivar autenticador
- Consultar Registracion Request

### Identidad
- Consultar Identidad
- Crear Hash

### IdentityX
- Validar Rostro
- Consultar Verificacion Rostro
- Validar Usuario No Enrolado
- Consultar Persona

### Renaper
- Consultar Persona Renaper

## Repositorio

- Fuentes: https://githubmacro.corp.globant.com/BancoMacro/BMC322-BIO-MS-Validacion.git
- Sonar Qube: https://newsonar.corp.globant.com/dashboard?id=BMC322-BIO-MS-Validacion

## Puerto para ejecución

### Ambientes Globant
- BIO-DEV: Puerto 8330

### Ambientes Macro
- Test: Puerto 8330

## Dependencias

### Librerías

La implementación de este microservicio utiliza las siguientes librerías comunes:

- macro-commons-values
- macro-commons-exceptions
- macro-commons-utils
- macro-commons-components
- macro-commons-esb

### Servicios externos

Como parte de la implementación de este microservicio, se está mapeando el acceso al siguiente servicio del ESB:

- DAON 
- IDENTITYX
- RENAPER
- DATAPOWER

Los detalles de cada versión se encuentran en el archivo [RELEASE_NOTES.md](RELEASE_NOTES.md)

## Desarrolladores

- Susan Inca
- Francisco Ocón
- Miguel Miño
- Mauricio A. Sanchez Franco
