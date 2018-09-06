# Documentación adicional Webpay Plus

Además de [la configuración usando los certificados de pruebas y el flujo de Webpay Plus Normal](../README.md#webpay-plus), el SDK permite también:

- [Crear una transacción Webpay Plus Mall](#crear-una-transacción-webpay-plus-mall).
- [Realizar transacciones con captura diferida](#realizar-transacciones-con-captura-diferida)
- [Anular una transacción Webpay Plus](#anular-una-transaccion-webpay-plus)
- [Configurar el ambiente Webpay de la integración](#configurar-el-ambiente-webpay-de-la-integración)
  - [Credenciales del comercio](#credenciales-del-comercio).
  - [Apuntar a producción](#apuntar-a-producción).


## Crear una transacción Webpay Plus Mall

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
detail.setBuyOrder("identificador único de la orden de compra para este store"); // generado por el comercio
storeDetails.add(detail);

WsTransactionDetail detail = new WsTransactionDetail();
detail.setCommerceCode("597020000544");
detail.setAmount(3000);
detail.setBuyOrder("identificador único de la orden de compra para este store"); // generado por el comercio
storeDetails.add(detail);
```

Y finalmente iniciar la transacción:

```java
String buyorder = "identificador único de la orden completa"
String sessionId = "identificador que será retornado en el callback de resultado";
String returnUrl = "https://callback/resultado/de/transaccion";
String finalUrl = "https://callback/final/post/comprobante/webpay";
WsInitTransactionOutput initResult = transaction.initTransaction(
        buyOrder, sessionId, returnUrl, finalUrl, storeDetails);

```

Luego todo sigue el mismo flujo que [el indicado en el caso de Webpay Plus Normal](../README.md#crear-una-transacció-webpay-plus-normal), con la diferencia de que en el resultado de `getTransactionResult` al llamar al método `result.getDetailOutput()` obtendrás tantos resultados como detalles de transacciones hayas enviado y debes verificar el resultado para cada uno de esos *outputs*.

## Realizar transacciones con captura diferida

Para realizar una transacción con captura diferida (donde en el momento de la
autorización del usuario se reserva el cupo pero no se hace efectiva la captura
final de la transacción) debes usar una `Configuration` con un código de
comercio con captura diferida.

Para hacer pruebas, puedes usar las credenciales pre-configuradas en el SDK:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
//...

WebpayNormal transaction =
    new Webpay(Configuration.forTestingWebpayPlusCapture()).getNormalTransaction();
```

Luego debes seguir los mismos pasos que
[en el caso de Webpay Plus Normal](../README.md#crear-una-transacció-webpay-plus-normal)
considerando que al final del flujo solo se ha autorizado la transacción pero
ha quedado pendiente su captura.

Para realizar esta captura, necesitarás el número de orden de compra y el
código de autorización obtenido al final del flujo de autorización. Teniendo
esa información puedes hacer lo siguiente:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayCapture;
import com.transbank.webpay.wswebpay.service.CaptureOutput;
//...

WebpayCapture captureTransaction =
    new Webpay(Configuration.forTestingWebpayPlusCapture()).getCaptureTransaction();
CaptureOutput result = captureTransaction.capture(authorizationCode, capAmount, buyOrder);
```

Eso te permitirá capturar el monto `capAmount` para la transacción identificada
por la orden de compra `buyOrder` y autorizada previamente con el código
`authorizationCode`. El monto capturado debe ser menor o igual al monto usado
cuando se autorizó la transacción.

En caso de éxito, el objeto `result` contiene el resultado incluyendo un código
de autorización asociado a la captura.

## Anular una transacción Webpay Plus

Para anular transacciones normales debes contar con la orden de compra, el
código de autorización y el monto asociado a la transacción.  Para transacciones
con captura diferida, debe usarse el código de autorización obtenido en la
captura (y el monto efectivamente capturado).

Las transacciones pueden anularse totalmente o, en el caso que sea una venta
sin cuotas, puede anularse parcialmente una transacción.

> **Restricción**: Se puede realizar como máximo una anulación parcial para
cada venta. Una segunda anulación de la misma transacción debe ser total.

Para ejecutar una anulación (usando las credenciales pre-configuradas), puedes
realizar lo siguiente:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNullify;
import com.transbank.webpay.wswebpay.service.NullificationOutput;
//...

WebpayCapture nullifyTransaction =
    new Webpay(Configuration.forTestingWebpayPlusNormal()).getNullifyTransaction();
NullificationOutput result = nullifyTransaction.nullify(
    authorizationCode, authAmount, buyOrder, nullAmount);
```
En caso que la transacción a anular sea Webpay Plus Mall, el código es similar.
Solo debes asegurarte que la `Configuration` sea de un comercio mall y luego
debes especificar el código de comercio del _store_ dentro del mall al que
pertenece la transacción. Por ejemplo, usando las credenciales pre-configuradas:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNullify;
import com.transbank.webpay.wswebpay.service.NullificationOutput;
//...

WebpayCapture nullifyTransaction =
    new Webpay(Configuration.forTestingWebpayPlusMall()).getNullifyTransaction();

// Configuration.forTestingWebpayPlusMall() configura un mall cuyos stores
// tienen el código 597020000543 y 597020000544
String storeCommerceCode = "597020000543";
NullificationOutput result = nullifyTransaction.nullify(
    authorizationCode, authAmount, buyOrder, nullAmount, storeCommerceCode);
```

## Configurar el ambiente Webpay de la integración

### Credenciales del comercio

Para Webpay, las credenciales del comercio (código de comercio y certificados)
varían según el subproducto usado (Webpay Plus, Webpay Plus Mall, Webpay OneClick,
Webpay OneClick Mall). Asimismo, varían las credenciales si la captura es
diferida. Y también varían si la moneda a manejar es pesos chilenos (CLP) o
dólares (USD).

Por lo tanto, es clave que antes de operar con las clases que permiten
realizar transacciones (`WebpayNormal`, `WebpayMallNormal`, `WebpayCapture`,
`WebpayNullify`, `WebpayOneClick`) se configure correctamente el objeto 
`Webpay` desde donde se obtienen dichas instancias.

Ese objeto `Webpay` se configura en base a un objeto `Configuration`. Y si bien
para hacer pruebas iniciales pueden usarse las credenciales pre-configuradas
(como se puede ver en todos los ejemplos anteriores), para poder superar el
proceso de validación en el ambiente de integración será necesario configurar
explícitamente tu código de comercio y certificados:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayNormal;
import cl.transbank.webpay.WebpayMallNormal;
import cl.transbank.webpay.WebpayCapture;
import cl.transbank.webpay.WebpayNullify;

//...

Configuration configuration = new Configuration();
configuration.setCommerceCode("12345"); // acá va tu código de comercio
configuration.setPrivateKey( // pega acá la llave privada de tu certificado
    "-----BEGIN RSA PRIVATE KEY-----\n" +
    "MIIEpQIBAAKCAQEA0ClVcH8RC1u+KpCPUnzYSIcmyXI87REsBkQzaA1QJe4w/B7g\n" +
    //....
    "MtdweTnQt73lN2cnYedRUhw9UTfPzYu7jdXCUAyAD4IEjFQrswk2x04=\n" +
    "-----END RSA PRIVATE KEY-----");
configuration.setPublicCert( // pega acá tu certificado público
    "-----BEGIN CERTIFICATE-----\n" +
    "MIIDujCCAqICCQCZ42cY33KRTzANBgkqhkiG9w0BAQsFADCBnjELMAkGA1UEBhMC\n" +
    //....
    "-----END CERTIFICATE-----");

Webpay webpay = new Webpay(configuration);
// Ahora puedes obtener las instancias de las transacciones de Webpay Plus
// que usarás:
WebpayNormal normalTransaction = webpay.getNormalTransaction();
WebpayMallNormal mallTransaction = webpay.getMallNormalTransaction();
WebpayCapture captureTransaction = webpay.getCaptureTransaction();
WebpayNullify nullifyTransaction = webpay.getNullifyTransaction();
```

### Apuntar a producción

Para cambiar el ambiente al que apunta el SDK (que por defecto es integración),
también debes usar el objeto `Configuration` (antes de crear una instancia de
`Webpay`):

```java
Configuration configuration = new Configuration();
configuration.setEnvironment(Webpay.Environment.PRODUCCION);
// agregar también configuración del código de comercio y certificados
```
