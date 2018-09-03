# Documentación adicional Webpay OneClick

Además de [la configuración usando los certificados de pruebas y el flujo de Webpay OneClick Normal](../README.md#webpay-oneclick), el SDK permite también:

- [Anular una transacción Webpay OneClick](#anular-una-transaccion-webpay-oneclick).
- [Eliminar una inscripción Webpay OneClick](#eliminar-una-inscripcion-webpay-oneclick).
- [Configurar el ambiente Webpay de la integración](#configurar-el-ambiente-webpay-de-la-integración).
  - [Credenciales del comercio](#credenciales-del-comercio).
  - [Apuntar a producción](#apuntar-a-producción).

## Anular una transacción Webpay OneClick

Para anular una transacción de Webpay OneClick, debes tener el número de orden
de compra usado al autorizar la transacción

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayOneClick;
// ...

WebpayOneClick transaction =
    new Webpay(Configuration.forTestingWebpayOneClickNormal()).getOneClickTransaction();


Long buyOrder = 1234; // numero de orden de compra usado al llamar a `authorize`
boolean success = transaction.reverseTransaction(buyOrderLong);
if (success) {
  // La anulación fue realizada exitosamente.
}
```

## Eliminar una inscripción Webpay OneClick

Si el usuario quiere dejar de pagar en tu comercio, es ideal que elimines la
inscripción Webpay OneClick para evitar el riesgo de contar con un token
asociado a la tarjeta del usuario.

Para eliminar la inscripción necesitas tener el `tbkUser` retornado por
`finishInscription` y el `username` corrrespondiente usado en `initInscription`.
(Puedes revisar el
[flujo de Webpay OneClick Normal](../README.md#webpay-oneclick) para recordar
esas llamadas previas). Contando con esa información, puedes remover la
inscripción de la siguiente manera:

```java
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.WebpayOneClick;
// ...

WebpayOneClick transaction =
    new Webpay(Configuration.forTestingWebpayOneClickNormal()).getOneClickTransaction();


String tbkUser = "tbkuser retornado por finishInscription";
String username = "identificador-del-usuario-en-comercio"; // El mismo usado en initInscription.
boolean success = transaction.removeUser(tbkUser, username);
if (success) {
  // La eliminación de la inscripción fue realizada exitosamente.
}
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
`WebpayNullify`) se configure correctamente el objeto `Webpay` desde donde se
obtienen dichas instancias.

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
// Ahora puedes obtener las instancias de las transacciones de Webpay OneClick
// que usarás:
WebpayOneClick onepayTransaction = webpay.getOneClickTransaction();
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
