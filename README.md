[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=TransbankDevelopers_transbank-sdk-java&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=TransbankDevelopers_transbank-sdk-java)
[![Maven Central](https://maven-badges.sml.io/maven-central/com.github.transbankdevelopers/transbank-sdk-java/badge.svg?style=plastic)](https://central.sonatype.com/artifact/com.github.transbankdevelopers/transbank-sdk-java)

# Transbank Java SDK
SDK oficial de Transbank

## Requisitos
- Java 1.8

## Instalación

Agrega la siguiente dependencia en el archivo pom de tu proyecto Maven:

```xml
<dependency>
    <groupId>com.github.transbankdevelopers</groupId>
    <artifactId>transbank-sdk-java</artifactId>
    <version>6.2.1</version>
</dependency>
```

### No usas Maven?

Si usas Gradle, Ivy, Grape o cualquier otro gestor compatible con Maven simplemente indica el grupo `com.github.transbankdevelopers` y el nombre de artefacto `transbank-sdk-java` y tu herramienta se encargará de todo.

Ahora, si gestionas las dependencias manualmente 😱 te quedan las siguientes opciones:

- Puedes [descargar manualmente el archivo "jar" desde Maven Central](https://search.maven.org/search?q=g:com.github.transbankdevelopers%20AND%20a:transbank-sdk-java&core=gav), pero tendrás también que buscar las dependencias (listadas en el archivo `pom.xml`) y descargarlas tú mismo manualmente (y quizás tengas que hacerlo recursivamente para las dependencias de las dependencias)

- Otra alternativa es que en lugar de descargar el "jar", descargues el archivo "with-all-deps-included.jar" que como sospecharás, incluye todas las dependencias. Eso te evitará buscar las dependencias a mano, pero te puede generar conflictos si ya estás usando una librería que este SDK ya usa, pero en una versión diferente y no compatible.

(Por eso te recomendamos fuertemente que uses maven u otra herramienta que gestione las dependencias por tí)

## Ejecutar Test

```bash
mvn test -P no-gpg
```


## Documentación

Puedes encontrar toda la documentación de cómo usar este SDK en el sitio https://www.transbankdevelopers.cl.

La documentación relevante para usar este SDK es:

- Documentación general sobre los productos y sus diferencias:
  [Webpay](https://www.transbankdevelopers.cl/producto/webpay).
- Documentación sobre [ambientes, deberes del comercio, puesta en producción,
  etc](https://www.transbankdevelopers.cl/documentacion/como_empezar#ambientes).
- Primeros pasos con [Webpay](https://www.transbankdevelopers.cl/documentacion/webpay).
- Referencia detallada sobre [Webpay](https://www.transbankdevelopers.cl/referencia/webpay).


## Información para contribuir a este proyecto

### Forma de trabajo

- Para los mensajes de commits, nos basamos en las [Git Commit Guidelines de Angular](https://github.com/angular/angular.js/blob/master/DEVELOPERS.md#commits).
- Usamos inglés para los nombres de ramas y mensajes de commit.
- Los mensajes de commit no deben llevar punto final.
- Los mensajes de commit deben usar un lenguaje imperativo y estar en tiempo presente, por ejemplo, usar "change" en lugar de "changed" o "changes".
- Los nombres de las ramas deben estar en minúsculas y las palabras deben separarse con guiones (-).
- Todas las fusiones a la rama principal se deben realizar mediante solicitudes de Pull Request(PR). ⬇️
- Se debe emplear tokens como "WIP" en el encabezado de un commit, separados por dos puntos (:), por ejemplo, "WIP: this is a useful commit message".
- Una rama con nuevas funcionalidades que no tenga un PR, se considera que está en desarrollo.
- Los nombres de las ramas deben comenzar con uno de los tokens definidos. Por ejemplo: "feat/tokens-configurations".

### Short lead tokens permitidos

`WIP` = En progreso.

`feat` = Nuevos features.

`fix` = Corrección de un bug.

`docs` = Cambios solo de documentación.

`style` = Cambios que no afectan el significado del código. (espaciado, formateo de código, comillas faltantes, etc)

`refactor` = Un cambio en el código que no arregla un bug ni agrega una funcionalidad.

`perf` = Cambio que mejora el rendimiento.

`test` = Agregar test faltantes o los corrige.

`chore` = Cambios en el build o herramientas auxiliares y librerías.

`revert` = Revierte un commit.

`release` = Para liberar una nueva versión.

### Creación de un Pull Request

- El PR debe estar enfocado en un cambio en concreto, por ejemplo, agregar una nueva funcionalidad o solucionar un error, pero un solo PR no puede agregar una nueva funcionalidad y arreglar un error.
- El título del los PR y mensajes de commit no debe comenzar con una letra mayúscula.
- No se debe usar punto final en los títulos.
- El título del PR debe comenzar con el short lead token definido para la rama, seguido de ":"" y una breve descripción del cambio.
- La descripción del PR debe detallar los cambios que se están incorporando.
- La descripción del PR debe incluir evidencias de que los test se ejecutan de forma correcta o incluir evidencias de que los cambios funcionan y no afectan la funcionalidad previa del proyecto.
- Se pueden agregar capturas, gif o videos para complementar la descripción o demostrar el funcionamiento del PR.

#### Flujo de trabajo

1. Crea tu rama desde develop.
2. Haz un push de los commits y publica la nueva rama.
3. Abre un Pull Request apuntando tus cambios a develop.
4. Espera a la revisión de los demás integrantes del equipo.
5. Para poder mezclar los cambios se debe contar con 2 aprobaciones de los revisores y no tener alertas por parte de las herramientas de inspección.

# Desarrollo con Dev Container (recomendado)

La forma más rápida y consistente de empezar. Un contenedor preconfigurado con Java 8 (Eclipse Temurin), Maven 3.9.9 y herramientas de desarrollo, sin necesidad de instalar nada en la máquina local más allá de Docker y VS Code.

## Requisitos del Dev Container

- **Docker**
  - **macOS:** [Docker Desktop](https://www.docker.com/products/docker-desktop/) o [OrbStack](https://orbstack.dev) (alternativa más liviana y con mejor rendimiento de I/O)
  - **Linux / WSL2:** Docker Engine
- **Visual Studio Code** con la extensión [Dev Containers](https://marketplace.visualstudio.com/items?itemName=ms-vscode-remote.remote-containers)

## Levantar el contenedor

1. Clonar el repositorio y abrirlo en VS Code.
2. VS Code detectará el directorio `.devcontainer/` y ofrecerá la opción **"Reopen in Container"** en la esquina inferior derecha. Si no aparece, abrir la paleta de comandos:
   - **macOS:** `Cmd + Shift + P`
   - **Linux / Windows:** `Ctrl + Shift + P`

   y buscar **"Dev Containers: Reopen in Container"**.
3. **La primera vez tarda entre 3 y 5 minutos** (descarga la imagen base y ejecuta el setup inicial). Las siguientes aperturas son inmediatas: VS Code reutiliza el contenedor existente.

Durante la creación inicial, el contenedor ejecuta automáticamente:
- Descarga de la imagen base Eclipse Temurin 8 JDK
- Instalación de Maven 3.9.9 con verificación de checksum SHA-512
- `mvn clean install -P no-gpg --no-transfer-progress` (compila el SDK y ejecuta tests)

### Esquema de flujo con git

![gitflow](https://wac-cdn.atlassian.com/dam/jcr:cc0b526e-adb7-4d45-874e-9bcea9898b4a/04%20Hotfix%20branches.svg?cdnVersion=1324)

## Generar una nueva versión

Para generar una nueva versión, se debe crear un PR (con un título "release: prepare release X.Y.Z" con los valores que correspondan para `X`, `Y` y `Z`). Se debe seguir el estándar [SemVer](https://semver.org/lang/es/) para determinar si se incrementa el valor de `X` (si hay cambios no retrocompatibles), `Y` (para mejoras retrocompatibles) o `Z` (si sólo hubo correcciones a bugs).

En ese PR deben incluirse los siguientes cambios:

1. Modificar el archivo `CHANGELOG.md` para incluir una nueva entrada (al comienzo) para `X.Y.Z` que explique en español los cambios.
2. Modificar el archivo `pom.xml` y modificar la versión.

Luego de obtener aprobación del PR, debe mezclarse a master e inmediatamente generar un release en GitHub con el tag `vX.Y.Z`. En la descripción del release debes poner lo mismo que agregaste al changelog.

Una vez generado el release, la publicación de los artefactos en Maven Central se disparará automáticamente. **Dicha publicación requiere aprobación manual**: se debe ingresar al portal de Maven Central, revisar los artefactos generados y aprobar su publicación para que queden disponibles públicamente.

Posterior a la liberación debes mezclar la rama release en develop, finalmente realizar un rebase de la rama develop utilizando como base la rama main.
