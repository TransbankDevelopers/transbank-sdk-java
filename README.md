[![Build Status](https://travis-ci.org/TransbankDevelopers/transbank-sdk-java.svg?branch=master)](https://travis-ci.org/TransbankDevelopers/transbank-sdk-java)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.github.transbankdevelopers%3Atransbank-sdk-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.transbankdevelopers%3Atransbank-sdk-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java)

# Transbank Java SDK
SDK oficial de Transbank

## Requisitos
- Java 1.7+

## Instalación

Agrega la siguiente dependencia en el archivo pom de tu proyecto Maven:

```xml
<dependency>
    <groupId>com.github.transbankdevelopers</groupId>
    <artifactId>transbank-sdk-java</artifactId>
    <version>1.3.1</version>
</dependency>
```

## Primeros pasos

### Onepay

#### Configuración de callbacks

A través de la clase `Onepay` puedes fijar los parámetros globales de la
configuración de tu comercio. Lo mínimo necesario es configurar los callbacks
en que Onepay retornará el control a tu backend después que el usuario haya
autorizado o abortado la transacción:

```java
import cl.transbank.onepay.Onepay;


// URL de retorno para canal MOBILE (web móvil). También será usada en canal WEB
// si integras la modalidad checkout del SDK javascript.
Onepay.setCallbackUrl("http://www.misitioweb.com/onepay-result");
// URL de retorno para canal APP (app móvil). Si no integras Onepay en tu app,
// entonces no es necesario.
Onepay.setAppScheme("mi-app://mi-app/onepay-result");
```

#### Crear una nueva transacción

Para iniciar un proceso de pago mediante la aplicación móvil de Onepay, primero es necesario crear la transacción en Transbank.
Para esto se debe crear en primera instancia un objeto `cl.transbank.onepay.model.ShoppingCart` el cual se debe llenar con ítems
`cl.transbank.onepay.model.Item`

```java
import cl.transbank.onepay.model.*;

//...

final Item zapatos = new Item()
        .setDescription("Zapatos")
        .setQuantity(1)
        .setAmount(15000)
        .setAdditionalData(null)
        .setExpire(-1);
        
ShoppingCart cart = new ShoppingCart();
cart.add(zapatos);
```

El monto en el carro de compras, debe ser positivo, en caso contrario se lanzará una excepción del tipo
`cl.transbank.onepay.exception.AmountException`

Luego que el carro de compras contiene todos los ítems, se crea la transacción:

```java
import cl.transbank.onepay.model.*;

// ...

TransactionCreateResponse response = Transaction.create(cart, Onepay.Channel.WEB);
```

El segundo parámetro en el ejemplo corresponde al `channel` y puede ser puede ser `Onepay.Channel.WEB`, 
`Onepay.Channel.MOBILE` o `Onepay.Channel.APP` dependiendo si quien está realizando el pago está usando un browser en 
versión Desktop, Móvil o está utilizando alguna aplicación móvil nativa.

En caso que `channel` sea `Onepay.Channel.MOBILE` es obligatorio que esté [previamente configurado el `callbackUrl`](#configuracion-de-callbacks) o de
lo contrario la aplicación móvil no podrá re-direccionar a este cuando el pago se complete con éxito y como consecuencia
no podrás confirmar la transacción.

En caso que `channel` sea `Onepay.Channel.APP` es obligatorio que esté [previamente configurado el `appScheme`](#configuración-de-callbacks).

El resultado entregado contiene la confirmación de la creación de la transacción, en la forma de un objeto 
`cl.transbank.onepay.model.TransactionCreateResponse`.

```json
"occ": "1807983490979289",
"ott": 64181789,
"signature": "USrtuoyAU3l5qeG3Gm2fnxKRs++jQaf1wc8lwA6EZ2o=",
"externalUniqueNumber": "f506a955-800c-4185-8818-4ef9fca97aae",
"issuedAt": 1532103896,
"qrCodeAsBase64": "QRBASE64STRING"
```

El `externalUniqueNumber` corresponde a un UUID generado por SDK que identifica
la transacción en el lado del comercio. Opcionalmente, puedes [especificar tus
propios external unique numbers](doc/Onepay.md#especificar-tus-propios-external-unique-numbers)

En el caso que no se pueda completar la transacción o `responseCode` en la respuesta del API sea distinto de `ok`
se lanzará una excepción `cl.transbank.onepay.exception.TransactionCreateException`

Esta información debes hacerla llegar a tu frontend web o app móvil para conectar
con la app Onepay donde el usuario podrá autorizar la transacción creada.

#### Confirmar una transacción

Una vez que el usuario autorizó el pago mediante la aplicación, dispones de 30 segundos
para realizar la confirmación de la transacción, de lo contrario, se realizará automáticamente
la reversa de la transacción.

```java
import cl.transbank.onepay.model.*;

//...

// externalUniqueNumber y occ vienen dados en la respuesta de Transaction.create
String externalUniqueNumber = "f506a955-800c-4185-8818-4ef9fca97aae";
String occ = "1807983490979289";
TransactionCommitResponse response = Transaction.commit(occ, externalUniqueNumber);
```

El resultado entregado contiene la confirmación de la confirmación de la transacción, en la forma de un objeto `TransactionCommitResponse`.

```json
"occ": "1807983490979289",
"authorizationCode": "623245",
"issuedAt": 1532104549,
"signature": "FfY4Ab89rC8rEf0qnpGcd0L/0mcm8SpzcWhJJMbUBK0=",
"amount": 27500,
"transactionDesc": "Venta Normal: Sin cuotas",
"installmentsAmount": 27500,
"installmentsNumber": 1,
"buyOrder": "20180720122456123"
```

#### Otras funcionalidades de Onepay

Eso concluye lo mínimo para crear y confirmar una transacción de Onepay.
En [doc/Onepay.md](doc/Onepay.md) puedes encontrar más información sobre otras
funcionalidades disponibles para Onepay.

## Información para contribuir y desarrollar este SDK

Esta librería usa [Project Lombok][lombok] en su desarrollo. Si bien no es necesario podrías querer instalar el [plugin][lombok-plugins]
para tu IDE favorito con el fin de evitar que veas errores marcados por la herramienta de desarrollo.

Además necesitas tener instalado un SDK de Java igual o superior a `jdk 1.7`

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

## Generar una nueva versión (con deploy automático a maven)

Para generar una nueva versión, se debe crear un PR (con un título "Prepare release X.Y.Z" con los valores que correspondan para `X`, `Y` y `Z`). Se debe seguir el estándar semver para determinar si se incrementa el valor de `X` (si hay cambios no retrocompatibles), `Y` (para mejoras retrocompatibles) o `Z` (si sólo hubo correcciones a bugs).

En ese PR deben incluirse los siguientes cambios:

1. Modificar el archivo CHANGELOG.md para incluir una nueva entrada (al comienzo) para `X.Y.Z` que explique en español los cambios **de cara al usuario del SDK**.
2. Modificar este README.md para que los ejemplos usen la nueva versión `X.Y.Z`
3. Modificar el archivo pom.xml para que la versión snapshot sea `X.Y.{Z+1}` (de manera que los snapshots que se generen después del release sean de la siguiente versión).

Luego de obtener aprobación del pull request, debe mezclarse a master e inmediatamente generar un release en GitHub con el tag `vX.Y.Z`. En la descripción del release debes poner lo mismo que agregaste al changelog.

Con eso Travis CI generará automáticamente una nueva versión de la librería y la publicará en Maven Central.

## Deploy manual a maven central

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

## No usas Maven?

Necesitaras descargar y agregar en forma manual los siguientes archivos JARs en tus dependencias:

* Librería Java [transbank-sdk-java-1.3.0.jar][jar_location]
* [Google Gson](https://github.com/google/gson) from <https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar>.

[jar_location]: http://search.maven.org/remotecontent?filepath=com/github/transbankdevelopers/transbank-sdk-java/1.3.0/transbank-sdk-java-1.3.0.jar
[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview

<!--
# vim: set tw=79:
-->
