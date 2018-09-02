[![Build Status](https://travis-ci.org/TransbankDevelopers/transbank-sdk-java.svg?branch=master)](https://travis-ci.org/TransbankDevelopers/transbank-sdk-java)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.github.transbankdevelopers%3Atransbank-sdk-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.transbankdevelopers%3Atransbank-sdk-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java)

# Transbank Java SDK
SDK oficial de Transbank

* [Requisitos](#requisitos)
* [Instalación](#instalación)
* [Primeros pasos](#primeros-pasos)
  * [Onepay](#onepay)
    * [Configuración de callbacks](#configuración-de-callbacks)
    * [Crear una nueva transacción](#crear-una-nueva-transacción)
    * [Confirmar una transacción](#confirmar-una-transacción)
    * [Otras funcionalidades de Onepay](#otras-funcionalidades-de-onepay)
* [Información para contribuir y desarrollar este SDK](#información-para-contribuir-y-desarrollar-este-sdk)

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

### Webpay

#### Webpay Plus Normal

Lo primero que necesitas es preparar una instancia de `WebpayNormal` con la
`Configuration` que incluye el código de comercio y los certificados a usar.

Una forma fácil de comenzar es usar la configuración para pruebas que viene
incluida en el SDK:


```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
// ...

WebpayNormal transaction =
    new Webpay(Configuration.forTestingWebpayPlusNormal()).getNormalTransaction();
```

> **Tip**: Como necesitarás ese objeto `transaction` en múltiples ocasiones, es buena idea
encapsular la lógica que lo genera en algún método que puedas reutilizar.

Una vez que ya cuentas con esa preparación, puedes iniciar transacciones:

```java
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
// ...
double amount = 1000;
String sessionId = "identificador que será retornado en el callback de resultado";
String buyOrder = "identificador único de orden de compra";
String returnUrl = "http://callback/resultado/de/transaccion";
String finalUrl = "http://callback/final/post/comprobante/webpay";
WsInitTransactionOutput initResult = transaction.initTransaction(
        amount, sessionId, buyOrder, returnUrl, finalUrl);

String formAction = initResult.getUrl();
String wsToken = initResult.getToken();
```

La URL y el token retornados te indican donde debes redirigir al usuario para
que comience el flujo de pago. Esta redirección debe ser vía `POST` por lo que
deberás crear un formulario web con un campo `ws_token` hidden y enviarlo
programáticamente para entregar el control a Webpay.

Una vez que el tarjetahabiente ha pagado (o declinado, o ha ocurrido un error),
Webpay retornará el control via `POST` a la URL que indicaste en el `returnUrl`.
Recibirás también el parámetro `ws_token` que te permitirá conocer el resultado
de la transacción:

```java
import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetailOutput;
// ...
TransactionResultOutput result =
    transaction.getTransactionResult(request.getParameter("ws_token"));
WsTransactionDetailOutput output = result.getDetailOutput().get(0);
if (output.getResponseCode() == 0) {
    // Transaccion exitosa, puedes procesar el resultado con el contenido de
    // las variables result y output.
}
```

En el caso exitoso deberás llevar el control via `POST` nuevamente a Webpay para
que el tarjetahabiente vea el comprobante que le deja claro que se ha realizado
el cargo en su tarjeta. Nuevamente deberás generar un formulario con el
`ws_token` como un campo hidden. La URL para redirigir la debes obtener desde
`result.getUrlRedirection()`.

Finalmente después del comprobante Webpay redigirá otra vez (via `POST`) a tu
sitio, esta vez a la URL que indicaste en el `finalUrl` cuando iniciaste la
transacción. Tal como antes, recibirás el `ws_token` que te permitirá
identificar la transacción y mostrar un comprobante o página de éxito a tu
usuario.

#### Webpay Plus Mall

En Webpay Plus Mall, puedes realizar cargos atribuibles a múltiples comercios dentro de una agrupación denominada *mall*. En la `Configuration` de `Webpay` se indica el código de comercio del *mall*. Y al realizar transacciones se asocian al código de comercio de cada *store* que pertenecen al *mall*.

Para comenzar, obtener una instancia de `WebpayMallNormal` es muy similar a lo visto anteriormente para `WebpayNormal`:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayMallNormal;
// ...
WebpayMallNormal transaction =
    new Webpay(Configuration.forTestingWebpayPlusMall()).getMallNormalTransaction();
```

Luego se deben crear las transacciones de los *stores*:

```java
import com.transbank.webpay.wswebpay.service.WsTransactionDetail;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;


// Configuration.forTestingWebpayPlusMall() configura un mall cuyos stores
// tienen el código 597020000543 y 597020000544
List<WsTransactionDetail> storeDetails = new ArrayList<WsTransactionDetail>();

WsTransactionDetail detail = new WsTransactionDetail();
detail.setCommerceCode("597020000543");
detail.setAmount(2000);
detail.setBuyOrder("identificador único de la orden para este store");
storeDetails.add(detail);

WsTransactionDetail detail = new WsTransactionDetail();
detail.setCommerceCode("597020000544");
detail.setAmount(3000);
detail.setBuyOrder("identificador único de la orden para este store");
storeDetails.add(detail);
```

Y finalmente iniciar la transacción:

```java
String buyorder = "identificador único de la orden completa"
String sessionId = "identificador que será retornado en el callback de resultado";
String returnUrl = "http://callback/resultado/de/transaccion";
String finalUrl = "http://callback/final/post/comprobante/webpay";
WsInitTransactionOutput initResult = transaction.initTransaction(
        buyOrder, sessionId, returnUrl, finalUrl, storeDetails);

```

Luego todo sigue el mismo flujo que [el indicado en el caso de Webpay Plus Normal](#webpay-plus-normal), con la diferencia de que en el resultado de `getTransactionResult` al llamar al método `result.getDetailOutput()` obtendrás tantos resultados como detalles de transacciones hayas enviado y debes verificar el resultado para cada uno de esos *outputs*.

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

### Generar una nueva versión (con deploy automático a maven)

Para generar una nueva versión, se debe crear un PR (con un título "Prepare release X.Y.Z" con los valores que correspondan para `X`, `Y` y `Z`). Se debe seguir el estándar semver para determinar si se incrementa el valor de `X` (si hay cambios no retrocompatibles), `Y` (para mejoras retrocompatibles) o `Z` (si sólo hubo correcciones a bugs).

En ese PR deben incluirse los siguientes cambios:

1. Modificar el archivo CHANGELOG.md para incluir una nueva entrada (al comienzo) para `X.Y.Z` que explique en español los cambios **de cara al usuario del SDK**.
2. Modificar este README.md para que los ejemplos usen la nueva versión `X.Y.Z`
3. Modificar el archivo pom.xml para que la versión snapshot sea `X.Y.{Z+1}` (de manera que los snapshots que se generen después del release sean de la siguiente versión).

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

## No usas Maven?

Si usas Gradle, Ivy, Grape o cualquier otro gestor compatible con Maven simplemente indica el grupo `com.github.transbankdevelopers` y el nombre de artefacto `transbank-sdk-java` y tu herramienta se encargará de todo.

Ahora, si gestionas las dependencias manualmente 😱 te quedan las siguientes opciones:

- Puedes [descargar manualmente el archivo "jar" desde Maven Central](https://search.maven.org/search?q=g:com.github.transbankdevelopers%20AND%20a:transbank-sdk-java&core=gav), pero tendrás también que buscar las dependencias (listadas en el archivo `pom.xml`) y descargarlas tú mismo manualmente (y quizás tengas que hacerlo recursivamente para las dependencias de las dependencias)

- Otra alternativa es que en lugar de descargar el "jar", descargues el archivo "with-all-deps-included.jar" que como sospecharás, incluye todas las dependencias. Eso te evitará buscar las dependencias a mano, pero te puede generar conflictos si ya estás usando una librería que este SDK ya usa, pero en una versión diferente y no compatible.

(Por eso te recomendamos fuertemente que uses maven u otra herramienta que gestione las dependencias por tí)

<!--
# vim: set tw=79:
-->
