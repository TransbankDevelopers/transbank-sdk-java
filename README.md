[![Build Status](https://semaphoreci.com/api/v1/continuum/transbank-onepay-sdk-java/branches/master/badge.svg)](https://semaphoreci.com/continuum/transbank-onepay-sdk-java)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.github.transbankdevelopers%3Atransbank-sdk-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.transbankdevelopers%3Atransbank-sdk-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-java)

# Transbank Java SDK
SDK oficial de Transbank

## Requisitos
- Java 1.7+

## Dependencias
Al realizar la instalación con Maven las dependencias debieran instalarse automáticamente.
- [Google Gson](https://github.com/google/gson)

## Instalación

### Instalar como depenedencia Maven

Agrega la siguiente dependencia en el archivo pom de tu proyecto:

```xml
<dependency>
    <groupId>com.github.transbankdevelopers</groupId>
    <artifactId>transbank-sdk-java</artifactId>
    <version>1.2.0</version>
</dependency>
```

## Primeros pasos

### Onepay

#### Configuración del APIKEY y APISECRET

Existen 2 formas de configurar esta información, la cual es única para cada comercio.

##### 1. En la inicialización de tu proyecto. (Solo una vez, al iniciar)

La clase `Onepay` contiene la configuración básica de tu comercio.

```java
import cl.transbank.onepay.Onepay;

//...

Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");
Onepay.setCallbackUrl("http://www.somecallback.com/example");
```

##### 2. Pasando el APIKEY y APISECRET a cada petición

Utilizando un objeto `cl.transbank.onepay.model.Options`

```java
Options options = new Options()
                  .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                  .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
```

#### Ambientes TEST y LIVE

Por defecto el tipo de Integración del SDK es siempre: `TEST`.

Puedes configurar el SDK para utilizar los servicios del ambiente de `LIVE` (Producción) de la siguiente forma:

```java
import cl.transbank.onepay.Onepay;

//...

Onepay.setIntegrationType(Onepay.IntegrationType.LIVE);
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

Luego que el carro de compras contiene todos los ítems. Se crea la transacción:

```java
import cl.transbank.onepay.model.*;

// ...

TransactionCreateResponse response = Transaction.create(cart, channel);
```

El parametro `channel` puede ser `WEB`, `MOBILE` o `APP` dependiendo si quien esta realizando el pago esta usando un 
browser en versión Desktop, Móvil o esta utilizando alguna aplicación móvil nativa.

En caso que `channel` sea `APP` es obligatorio que este previamente configurado el `appScheme`:

```java
import cl.transbank.onepay.Onepay;

//...

Onepay.setAppScheme("STRINGAPPSCHEME");
```

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

En el caso que no se pueda completar la transacción o `responseCode` en la respuesta del API sea distinto de `ok`
se lanzará una excepción `cl.transbank.onepay.exception.TransactionCreateException`

Posteriormente, se debe presentar al usuario el código QR y el número de OTT para que pueda proceder al pago
mediante la aplicación móvil.

#### Confirmar una transacción

Una vez que el usuario realizó el pago mediante la aplicación, dispones de 30 segundos
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

El resultado entregado contiene la confirmación de la confirmación de la transacción, en la forma de un objeto `TransactionCreateResponse`.

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

#### Anular una transacción

Cuando una transacción fue creada correctamente, se dispone de un plazo de 30 días para realizar la anulación de esta.

```java
import cl.transbank.onepay.model.*;

//...

// amount, occ y autorizathionCode se obtienen a partir de la respuesta de Transaction.commit
long amount = 27500;
String occ = "1807983490979289";
String externalUniqueNumber = "f506a955-800c-4185-8818-4ef9fca97aae";
String autorizathionCode = "623245";
RefundCreateResponse response = Refund.create(amount, occ, externalUniqueNumber, autorizathionCode);
```

El resultado entregado contiene la confirmación de la anulación, en la forma de un objeto `RefundCreateResponse`.

```json
"occ": "1807983490979289",
"externalUniqueNumber": "f506a955-800c-4185-8818-4ef9fca97aae",
"reverseCode": "623245",
"issuedAt": 1532104252,
"signature": "52NpZBolTEs+ckNOXwGRexDetY9MOaX1QbFYkjPymf4="
```

## Desarrollo

Esta librería usa [Project Lombok][lombok] en su desarrollo. Si bien no es necesario podrías querer instalar el [plugin][lombok-plugins]
para tu IDE favorito con el fin de evitar que veas errores marcados por la herramienta de desarrollo.

## No usas Maven?

Necesitaras descargar y agregar en forma manual los siguientes archivos JARs en tus dependencias:

* Librería Java [transbank-sdk-java-1.2.0.jar][jar_location]
* [Google Gson](https://github.com/google/gson) from <https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar>.

[jar_location]: http://search.maven.org/remotecontent?filepath=com/github/transbankdevelopers/transbank-sdk-java/1.2.0/transbank-sdk-java-1.2.0.jar
[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview

<!--
# vim: set tw=79:
-->
