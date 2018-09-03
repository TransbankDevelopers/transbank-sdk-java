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
String returnUrl = "https://callback/resultado/de/transaccion";
String finalUrl = "https://callback/final/post/comprobante/webpay";
WsInitTransactionOutput initResult = transaction.initTransaction(
        buyOrder, sessionId, returnUrl, finalUrl, storeDetails);

```

Luego todo sigue el mismo flujo que [el indicado en el caso de Webpay Plus Normal](../README.md#crear-una-transacció-webpay-plus-normal), con la diferencia de que en el resultado de `getTransactionResult` al llamar al método `result.getDetailOutput()` obtendrás tantos resultados como detalles de transacciones hayas enviado y debes verificar el resultado para cada uno de esos *outputs*.

## Realizar transacciones con captura diferida

TODO

## Anular una transacción Webpay Plus

TODO

## Configurar el ambiente Webpay de la integración

### Credenciales del comercio

TODO

### Apuntar a producción

TODO