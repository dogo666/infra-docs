# BMC322-BIO-MS-Scripts

Proyecto Flyway de gestión de los recursos SQL (migraciones) de Biometria
<br/>
[Flyway Plugin](https://flywaydb.org/documentation/usage/maven/)


## Ambiente de desarrollo local (Docker Compose)
Ejecute el servicio de docker-compose desde el root del proyecto para subir una instancia de SQL server 
<br/>
```manifest 
 docker-compose up -d
```
Nota: La instancial local de Mysql (docker-compose) corre por el puerto 3306 (mas detalles pueden ser obtenidos en el archivo docker-compose.yml)

## Ejecución de scripts de migrado de la base de datos (Flyway)
1- Sobre un ambiente con Java 11 y Maven instalado, proceda a ejecutar el siguiente comando para publicar los archivos *.sql en el classpath

```manifest
mvn clean install
```

2 - Ejecute el comando de migración Flyway de la siguiente forma:

```manifest
mvn -Dflyway.configFiles=flyway-local.conf flyway:migrate
```

Si todo salio bien, la terminal deberia mostrar el mensaje "BUILD SUCCESS"


## Estructura del archivo flyway-*.conf
El archivo de conf referido en los comandos anteriores debera tener al menos las siguientes properties (cambie los valores según corresponda): 


```manifest
flyway.url=jdbc:mysql://localhost:3306/db_macro_biometrics
flyway.user=my_user
flyway.password=my_pass
flyway.encoding=UTF-8
```


## Nota: Los siguientes comandos son OPCIONALES y estan disponibles para determinadas situaciones

Saber el estado de la base de datos (en terminos de migraciones ejecutadas):

```manifest
mvn -Dflyway.configFiles=flyway-local.conf flyway:info
```

Eliminar todos los objetos creados en la base de datos:

```manifest
mvn -Dflyway.configFiles=flyway-local.conf flyway:clean
```