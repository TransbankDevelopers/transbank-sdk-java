[![Build Status](https://travis-ci.org/TransbankDevelopers/transbank-sdk-java.svg?branch=master)](https://travis-ci.org/TransbankDevelopers/transbank-sdk-java)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.github.transbankdevelopers%3Atransbank-sdk-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.transbankdevelopers%3Atransbank-sdk-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java)

# Transbank Java SDK
SDK oficial de Transbank

## Requisitos
- Java 1.7 o 1.8

## Instalación

Agrega la siguiente dependencia en el archivo pom de tu proyecto Maven:

```xml
<dependency>
    <groupId>com.github.transbankdevelopers</groupId>
    <artifactId>transbank-sdk-java</artifactId>
    <version>1.9.5</version>
</dependency>
```

### No usas Maven?

Si usas Gradle, Ivy, Grape o cualquier otro gestor compatible con Maven simplemente indica el grupo `com.github.transbankdevelopers` y el nombre de artefacto `transbank-sdk-java` y tu herramienta se encargará de todo.

Ahora, si gestionas las dependencias manualmente 😱 te quedan las siguientes opciones:

- Puedes [descargar manualmente el archivo "jar" desde Maven Central](https://search.maven.org/search?q=g:com.github.transbankdevelopers%20AND%20a:transbank-sdk-java&core=gav), pero tendrás también que buscar las dependencias (listadas en el archivo `pom.xml`) y descargarlas tú mismo manualmente (y quizás tengas que hacerlo recursivamente para las dependencias de las dependencias)

- Otra alternativa es que en lugar de descargar el "jar", descargues el archivo "with-all-deps-included.jar" que como sospecharás, incluye todas las dependencias. Eso te evitará buscar las dependencias a mano, pero te puede generar conflictos si ya estás usando una librería que este SDK ya usa, pero en una versión diferente y no compatible.

(Por eso te recomendamos fuertemente que uses maven u otra herramienta que gestione las dependencias por tí)

## Documentación 

Puedes encontrar toda la documentación de cómo usar este SDK en el sitio https://www.transbankdevelopers.cl.

La documentación relevante para usar este SDK es:

- Documentación general sobre los productos y sus diferencias:
  [Webpay](https://www.transbankdevelopers.cl/producto/webpay) y
  [Onepay](https://www.transbankdevelopers.cl/producto/onepay).
- Documentación sobre [ambientes, deberes del comercio, puesta en producción,
  etc](https://www.transbankdevelopers.cl/documentacion/como_empezar#ambientes).
- Primeros pasos con [Webpay](https://www.transbankdevelopers.cl/documentacion/webpay) y [Onepay](https://www.transbankdevelopers.cl/documentacion/onepay).
- Referencia detallada sobre [Webpay](https://www.transbankdevelopers.cl/referencia/webpay) y [Onepay](https://www.transbankdevelopers.cl/referencia/onepay).

## Información para contribuir y desarrollar este SDK

Esta librería usa [Project Lombok][lombok] en su desarrollo. Si bien no es necesario podrías querer instalar el [plugin][lombok-plugins]
para tu IDE favorito con el fin de evitar que veas errores marcados por la herramienta de desarrollo.

Se recomienda usar Java 7 u 8 para compilar este SDK. En Java 9 o superior la generación de Javadocs falla debido a la introducción de módulos (y a que varias clases de JavaEE en el paquete javax.* han sido movidas a módulos separados).

### Standares

- Para los commits respetamos las siguientes normas: https://chris.beams.io/posts/git-commit/
- Usamos ingles, para los mensajes de commit.
- Se pueden usar tokens como WIP, en el subject de un commit, separando el token con `:`, por ejemplo:
`WIP: This is a useful commit message`
- Para los nombres de ramas también usamos ingles.
- Se asume, que una rama de feature no mezclada, es un feature no terminado.
- El nombre de las ramas va en minúsculas.
- Las palabras se separan con `-`.
- Las ramas comienzan con alguno de los short lead tokens definidos, por ejemplo: `feat/tokens-configuration`

#### Short lead tokens
##### Commits
- WIP = Trabajo en progreso.
##### Ramas
- feat = Nuevos features
- chore = Tareas, que no son visibles al usuario.
- bug = Resolución de bugs.

### Todas las mezclas a master se hacen mediante Pull Request.

### Construir el proyecto localmente
```bash
mvn clean compile
```
### Correr los test localmente
```bash
mvn test
```

### Generar un jar local

````bash
mvn package
````

#### Instalar jar local

````bash
mvn clean install
````

Si te encuentras con un error como

````bash
[INFO] --- maven-gpg-plugin:1.6:sign (sign-artifacts) @ transbank-sdk-java ---
gpg: signing failed: Timeout
````

tienes que exportar la siguiente variable de entorno:

````bash
export GPG_TTY=$(tty)
````

Luego, se te pedira una frase para desbloquear la firma (puedes encontrar más informacion en 1Password)

### Generar una nueva versión (con deploy automático a maven)

Para generar una nueva versión, se debe crear un PR (con un título "Prepare release X.Y.Z" con los valores que correspondan para `X`, `Y` y `Z`). Se debe seguir el estándar semver para determinar si se incrementa el valor de `X` (si hay cambios no retrocompatibles), `Y` (para mejoras retrocompatibles) o `Z` (si sólo hubo correcciones a bugs).

En ese PR deben incluirse los siguientes cambios:

1. Modificar el archivo `CHANGELOG.md` para incluir una nueva entrada (al comienzo) para `X.Y.Z` que explique en español los cambios **de cara al usuario del SDK**.
2. Modificar este `README.md` para que los ejemplos usen la nueva versión `X.Y.Z`
3. Modificar el archivo `pom.xml` para que la versión snapshot sea `X.Y.{Z+1}` (de manera que los snapshots que se generen después del release sean de la siguiente versión).

Luego de obtener aprobación del pull request, debe mezclarse a master e inmediatamente generar un release en GitHub con el tag `vX.Y.Z`. En la descripción del release debes poner lo mismo que agregaste al changelog.

Con eso Travis CI generará automáticamente una nueva versión de la librería y la publicará en Maven Central.

### Deploy manual a maven central

El deploy de una nueva version ocurre automáticamente, en Travis CI, cuando una nueva tag de git es creada.
Los tag de git deben respetar el standard de [SemVer](https://semver.org/). Además si el commit (o PR) a master no tiene un tag asociada, se generara una version snapshot.
Si de todas maneras necesitas hacer el release manualmente a MavenCentral ya sea de un snapshot o una nueva version, entonces debes configurar lo siguiente en tu archivo settings de maven, comúnmente ubicado en `~/.m2/settings.xml`

```xml
<settings>
    <servers>
        <server>
            <id>ossrh</id>
            <username>your-jira-id</username>
            <password>your-jira-pwd</password>
        </server>
    </servers>
    <profiles>
        <profile>
            <id>ossrh</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <gpg.executable>gpg</gpg.executable>
                <gpg.passphrase>your-gpg-pwd</gpg.passphrase>
            </properties>
       </profile>
    </profiles>
</settings>
```

- `your-jira-id`: Usuario de Jira del repositorio Nexus.
- `your-jira-pwd`: Password del usuario Jira de Nexus.
- `your-gpg-pwd`: Frase para la el certificado de firma gpg.

_*Nota*: para subir codigo a MavenCentral, este debe estar firmado._ [Mas información](https://dracoblue.net/dev/uploading-snapshots-and-releases-to-maven-central-with-travis/)

Si quieres probar el snapshot que se genera en MavenCentral, debes agregar el repositorio de snapshots de Sonatype, a continuación 
esta la configuración que debes agregar a tu settings `~/.m2/settings.xml`
```xml
<profiles>
  <profile>
     <id>allow-snapshots</id>
        <activation><activeByDefault>true</activeByDefault></activation>
     <repositories>
       <repository>
         <id>snapshots-repo</id>
         <url>https://oss.sonatype.org/content/repositories/snapshots</url>
         <releases><enabled>false</enabled></releases>
         <snapshots><enabled>true</enabled></snapshots>
       </repository>
     </repositories>
   </profile>
</profiles>
```

<!--
# vim: set tw=79:
-->

