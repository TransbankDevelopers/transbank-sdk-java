# Transbank Java SDK

## Requerimientos

Java 1.7+

## Instalación

### Si usas Maven

Agrega la siguiente dependencia en el archivo pom de tu proyecto:

```xml
<dependency>
    <groupId>cl.transbank.onepay</groupId>
    <artifactId>transbank-onepay-sdk-java</artifactId>
    <version>1.0.0</version>
</dependency>
```

#### Agregar repositorio de Transbank 

Actualmente estamos trabajando en deployar la librería en un repositorio 
público de Maven. Mientras eso no este listo debes agregar nuestro
repositorio privado en el archivo pom de tu proyecto:

```xml
<repositories>
    <repository>
        <id>transbank-repo</id>
        <name>transbank-repo</name>
        <url>http://142.93.18.171/maven-repo/</url>
    </repository>
</repositories>
```

## Modo de uso

### Crear transación

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

Diseñado princiupalmente para efectos de test, este tipo de integracion apunta a servicios
los cuales responden una respuesta estatica.

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
