# Documentación adicional Onepay

Además de [la configuración básica, la creación y la confirmación de transacciones documentadas en el README](../README.md#onepay),
el SDK permite también:

- [Especificar tus propios external unique numbers](#especificar-tus-propios-external-unique-numbers).
- [Anular una transacción](#anular-una-transacción).
- [Configurar el ambiente Onepay de la integración](#configurar-el-ambiente-onepay-de-la-integración).
  - [Credenciales del comercio](#credenciales-del-comercio).
  - [Apuntar a producción](#apuntar-a-producción).


## Especificar tus propios external unique numbers

Al crear una transacción puedes querer especificar un identificador propio de
transacción. Este parámetro se conoce como `externalUniqueNumber` y puede ser
especificado al momento de crear la transacción.

La única condición es que **debes asegurar que este identificador sea único
para toda tu organización**, de lo contrario la transacción será **rechazada**.

Si pasas el parámetro `externalUniqueNumber` al crear la transacción, el SDK
dejará de generar uno por tí:

```java
import cl.transbank.onepay.model.*;

// ...

String externalUniqueNumber = "My Unique Number - 123"
TransactionCreateResponse response = Transaction.create(cart, channel, externalUniqueNumber);
```

De ser creada exitosamente la transacción, `response.externalUniqueNumber`
tendrá el valor especificado en el parámetro `externalUniqueNumber`.

## Anular una transacción

Cuando una transacción fue creada correctamente, se dispone de un plazo de 30 días para realizar la anulación de esta.
Para anularla, puedes realizar lo siguiente:

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

## Configurar el ambiente Onepay de la integración

Por defecto el tipo de Integración del SDK es siempre `TEST` y el SDK trae
pre-configuradas credenciales para operar en este ambiente.

### Credenciales del comercio

Para establecer las credenciales de tu comercio (y no seguir usando las
credenciales pre-configuradas), puedes realizarlo así:

```java
import cl.transbank.onepay.Onepay;
//...

Onepay.setApiKey("api-key-entregado-por-transbank");
Onepay.setSharedSecret("secreto-entregado-por-transbank");
```

También puedes configurar el api key y secret de una petición específica,
utilizando un objeto `cl.transbank.onepay.model.Options`

```java
import cl.transbank.onepay.model.Options;
//...

Options options = new Options()
                  .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                  .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");
```

Ese objeto `Options` puedes pasarlo como parámetro a cualquier método
transaccional de Onepay (`Transaction.create`, `Transaction.commit` y
`Refund.create`).

### Apuntar a producción

Puedes configurar el SDK para utilizar los servicios del ambiente de `LIVE` (Producción) de la siguiente forma:

```java
import cl.transbank.onepay.Onepay;
//...
Onepay.setIntegrationType(Onepay.IntegrationType.LIVE);
```

