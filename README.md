# Onepay Java SDK Quick Start

Puedes registrar tu comercio para usar Onepay en http://algo.transbank.cl

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

### Si usas Gradle

Agrega la siguiente dependencia en archivo build de tu proyecto:

```groovy
compile "cl.transbank.onepay:transbank-onepay-sdk-java:1.0.0"
```

### Otros

Necesitaras descargar y agregar en forma manual los siguientes archivos JARs en tus dependencias:

* Librería Java de Onepay http://algun.lugar.de.transbank
* [Google Gson](https://github.com/google/gson) from <https://repo1.maven.org/maven2/com/google/code/gson/gson/2.6.2/gson-2.6.2.jar>.

## Documentación

Por favor la [Java API docs](http://algun.lugar.de.transbank) para ver la documentación actualizada.

## Uso

OnepayExample.java

```java
import cl.transbank.onepay.Onepay;
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.Item;
import cl.transbank.onepay.model.ShoppingCart;
import cl.transbank.onepay.model.Transaction;
import cl.transbank.onepay.model.TransactionCreateResponse;

import java.io.IOException;

public class OnepayExample {
    public static void main(String[] args) {
        Onepay.setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
        Onepay.setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg");

        ShoppingCart cart = new ShoppingCart();
        try {
            cart.add(new Item("Zapatos", 1, 15000, null, -1));
            cart.add(new Item("Pantalon", 1, 12500, null, -1));

            TransactionCreateResponse response = Transaction.create(cart);
            System.out.println(response);
        } catch (IOException|TransbankException e) {
            e.printStackTrace();
        }
    }
}
```

Puedes mirara los [test funcionales](http://ruta.a.los.test) para mas ejemplos.

### Configuración por request

Para aplicaciones que necesiten usar múltiples keys durante el ciclo de vida del proceso,
es posible configurar sus keys por cada petición.

El ejemplo anterior podría ser también escrito de la siguiente manera

``` java
import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.*;

import java.io.IOException;

public class OnepayExample {
    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();
        
        try {
            cart.add(new Item("Zapatos", 1, 15000, null, -1));
            cart.add(new Item("Pantalon", 1, 12500, null, -1));

            Options options = new Options()
                    .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                    .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");

            TransactionCreateResponse response = Transaction.create(cart, options);
            System.out.println(response);
        } catch (IOException|TransbankException e) {
            e.printStackTrace();
        }
    }
}
```

## Desarrollo

Esta librería usa [Project Lombok][lombok] en su desarrollo. Si bien no es necesario podrías querer instalar el [plugin][lombok-plugins]
para tu IDE favorito con el fin de evitar que veas errores marcados por la herramienta de desarrollo.

[lombok]: https://projectlombok.org
[lombok-plugins]: https://projectlombok.org/setup/overview

<!--
# vim: set tw=79:
-->
