[![Build Status](https://semaphoreci.com/api/v1/continuum/transbank-onepay-sdk-java/branches/master/badge.svg)](https://semaphoreci.com/continuum/transbank-onepay-sdk-java)
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
    <version>1.0.1</version>
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
```

##### 2. Pasando el `APIKEY` y `APISECRET` a cada petición

Utilizando un objeto `cl.transbank.onepay.model.Options`

```java
Options options = new Options()
                  .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                  .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
```

#### Ambientes `TEST` y `LIVE`

Por defecto el tipo de Integración del SDK es siempre: `TEST`.

Puedes configurar el SDK para utilizar los servicios del ambiente de `LIVE` (Producción) de la suiguiente forma:

```java
import cl.transbank.onepay.Onepay;

//...

Onepay.setIntegrationType(Onepay.IntegrationType.TEST);
```

#### Crear una nueva transacción

Para iniciar un proceso de pago mediante la aplicación móvil de Onepay, primero es necesario crear la transacción en Transbank.
Para esto se debe crear en primera instancia un objeto `cl.transbank.onepay.model.ShoppingCart` el cual se debe llenar con ítems
`cl.transbank.onepay.model.Item`

```csharp
using Transbank.Onepay:
using Transbank.Onepay.Model:

//...

ShoppingCart cart = new ShoppingCart();
cart.Add(new Item(
    description: "Zapatos",
    quantity: 1,
    amount: 10000,
    additionalData: null,
    expire: 10));
```
El monto en el carro de compras, debe ser positivo, en caso contrario se lanzará una excepción del tipo
`Transbank.Onepay.Exceptions.AmountException`

Luego que el carro de compras contiene todos los ítems. Se crea la transacción:

```csharp
using Transbank.Onepay:
using Transbank.Onepay.Model:

// ...

TransactionCreateResponse response = Transaction.Create(cart);
```

El resultado entregado contiene la confirmación de la creación de la transacción, en la forma de un objeto `TransactionCreateResponse`.

```json
"occ": "1807983490979289",
"ott": 64181789,
"signature": "USrtuoyAU3l5qeG3Gm2fnxKRs++jQaf1wc8lwA6EZ2o=",
"externalUniqueNumber": "f506a955-800c-4185-8818-4ef9fca97aae",
"issuedAt": 1532103896,
"qrCodeAsBase64": "QRBASE64STRING"
```

En el caso que no se pueda completar la transacción o la respuesta del servicio sea distinta a `http 200`
Se lanzara una excepción `Transbank.Onepay.Exceptions.TransactionCreateResponse`
>>>>>>> Stashed changes

Posteriormente, se debe presentar al usuario el código QR y el número de OTT para que pueda proceder al pago
mediante la aplicación móvil.

TransactionCreateExample.java

```java
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.*;

import java.io.IOException;

public class TransactionCreateExample {
    public static void main(String[] args) throws IOException, TransbankException {
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");

        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));

        TransactionCreateResponse response = Transaction.create(cart);
        System.out.println(response);
    }
}
```
#
### Confirmar transacción

Una vez que el usuario realizó el pago mediante la aplicación, dispones de 30 segundos para realizar la 
confirmación de la transacción, de lo contrario, se realizará automáticamente la reversa de la transacción.

```java
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.*;

import java.io.IOException;

public class TransactionCommitExample {
    // externalUniqueNumber y OCC vienen dados en la respuesta de Transaction.create
    private static final String EXTERNAL_UNIQUE_NUMBER = "f506a955-800c-4185-8818-4ef9fca97aae";
    private static final String OCC = "1807983490979289";

    public static void main(String[] args) throws IOException, TransbankException {
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");
        
        TransactionCommitResponse response = Transaction.commit(OCC, EXTERNAL_UNIQUE_NUMBER);
        System.out.println(response);
    }
}
```
#
### Anular transacción

Cuando una transacción fue creada correctamente, se dispone de un plazo de 30 días para realizar la 
anulación de esta.

```java
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.*;

import java.io.IOException;

public class TransactionRefundExample {
    public static void main(String[] args) throws IOException, TransbankException {
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");
        
        // amount, OCC y autorizathionCode se obtienen a partir de la respuesta de Transaction.commit
        RefundCreateResponse response = Refund.create(27500, "1807983490979289", "f506a955-800c-4185-8818-4ef9fca97aae",
                   "623245");
        System.out.println(response);
    }
}
```

## Ambientes

El SDK incluye tres ambientes distintos para trabajar: LIVE, TEST y MOCK

#### LIVE

Es el ambiente productivo de Transbank y debe ser configurado una vez que tu integración
esta probada y certificada. La forma de hacerlo es la siguiente:

```java
Onepay.setIntegrationType(Onepay.IntegrationType.LIVE);
```

La configuración de ambiente se realiza en forma estática, lo que quiere decir que basta 
con que se realice una vez durante el ciclo de vida de la aplicación y debemos asegurarnos
de hacerlo antes de realizar cualquier tipo de transacción.

#### TEST

Esta es la configuración por defecto del SDK. Es decir, si no configuramos el tipo de
integración se usaran los servidores de test.

```java
Onepay.setIntegrationType(Onepay.IntegrationType.TEST);
```

#### MOCK

Diseñado para facilitar pruebas automatizadas, este tipo de integracion apunta a servicios
sin lógica cuya respuesta es estática.

```java
Onepay.setIntegrationType(Onepay.IntegrationType.MOCK);
```
#
### Configuración por request

Para aplicaciones que necesiten usar múltiples keys durante el ciclo de vida del proceso,
es posible configurar estas por cada petición.

De esta forma nuestro TransactionCreateExample podría ser también escrito de la siguiente 
forma:

```java
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.*;

import java.io.IOException;

public class TransactionCreateExample {
    public static void main(String[] args) throws IOException, TransbankException {
        ShoppingCart cart = new ShoppingCart();
        cart.add(new Item("Zapatos", 1, 15000, null, -1));
        cart.add(new Item("Pantalon", 1, 12500, null, -1));

        Options options = new Options()
                  .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                  .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");

        TransactionCreateResponse response = Transaction.create(cart, options);
        System.out.println(response);
    }
}
```
## Desarrollo

Esta librería usa [Project Lombok][lombok] en su desarrollo. Si bien no es necesario podrías querer instalar el [plugin][lombok-plugins]
para tu IDE favorito con el fin de evitar que veas errores marcados por la herramienta de desarrollo.

## No usas Maven?

Necesitaras descargar y agregar en forma manual los siguientes archivos JARs en tus dependencias:

* Librería Java [transbank-sdk-java-1.0.0.jar][jar_location]
* [Google Gson](https://github.com/google/gson) from <https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar>.

[jar_location]: http://142.93.18.171/maven-repo/cl/transbank/transbank-sdk-java/1.0.0/transbank-sdk-java-1.0.0.jar
[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview

<!--
# vim: set tw=79:
-->
