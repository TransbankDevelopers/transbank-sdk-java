# Changelog

Todos los cambios notables a este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
y este proyecto adhiere a [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [1.9.1] - 2021-06-07

### Added
- Se agregan los tributos `responseCode` y `installmentsAmount` a la clase `WebpayPlusMallTransactionStatusResponse`
- Se agregan los tributos `installmentsAmount` a la clase `WebpayPlusMallTransactionCommitResponse`

## [1.9.0] - 2020-11-23

### Added
- Se agrega soporte para Oneclick Mall con Captura diferida a través de la clase Oneclick.MallDeferredInscription y Oneclick.MallDeferredTransaction

### Changed
- Se depreca la clase OneclickMallDeferred en favor de Oneclick.MallDeferredInscription y Oneclick.MallDeferredTransaction

## [1.8.1] - 2020-11-12

### Changed
- Se deprecan métodos commit de Transacción Completa que no permiten valores nulos en campos opcionales
- Se agregan nuevas firmas de métodos commit de Transacción Completa que permiten valores nulos

## [1.8.0] - 2020-10-22

### Added

- Se agrega soporte para:
  - Webpay Plus Rest
    - modalidad normal
    - modalidad captura diferida
    - modalidad mall
    - modalidad mall captura diferida
  - Patpass by Webpay Rest
  - Patpass Comercio Rest
  - Transacción completa Rest
    - modalidad mall

## [1.7.1] - 2020-08-10

### Added

- La respuesta de autorizazión de Oneclick Mall rest no estaba entregando el `responseCode`

## [1.7.0] - 2019-12-26

### Changed

- Se agrega soporte para Oneclick Mall y Transacción Completa en sus versiones REST.

## [1.6.1] - 2019-02-12

### Changed

- Las credenciales de PatPass by Webpay para ambiente de Integración han sido actualizadas

## [1.6.0] - 2018-12-31

### Added

- Se agrega soporte para poder configurar `commerceLogoUrl` y `qrWidthHeight`. El primero entrega soporte para que el app de onepay pueda mostrar el logo de comercio, mientras que el segundo entrega la posibilidad de pedir que la imagen QR venga en un tamaño especifico (útil para la modalidad de QR directo.)

## [1.5.3] - 2018-11-05

### Changed

- Corrige [error en certificado de
  producción](https://github.com/TransbankDevelopers/transbank-sdk-java/issues/60).
  
## [1.5.2] - 2018-10-26

### Changed

- Corrige [error en URL de
  producción](https://github.com/TransbankDevelopers/transbank-sdk-java/issues/56)
  que apuntaba a `webpay3.transbank.cl` en lugar de `webpay3g.transbank.cl`.

## [1.5.1] - 2018-10-19

### Changed

- Corrige errores relacionados con Apache CXF en entornos donde existe otro
  proveedor JAX-WS seleccionado por defecto. Si te tocó ver errores que mencionaban "...cannot be cast to
  `org.apache.cxf.frontend.ClientProxy`" eso está corregido desde esta versión.

## [1.5.0] - 2018-10-04

### Added
- Incluye soporte para comercios mall que usan sólo captura diferida.

## [1.4.0] - 2018-09-06

### Added

- Incluye soporte de Webpay
- Incluye soporte experimental de PatPass by Webpay.

### Changed

- Credenciales pre-configuradas para Onepay. No es necesario configurar Api Key
ni Shared Secret para operar en ambiente `TEST`.


## [1.3.1] - 2018-08-29

### Changed

- Apunta entornos a los servidores oficiales para `TEST` y `LIVE`. De ahora en
adelante, el SDK puede ser usado para validaciones oficiales y será
interoperable con las herramientas provistas por Transbank para ayudar esa
integración y validación (como el dashboard para simular transacciones).

## [1.3.0] - 2018-08-16

### Added

- Agrega `externalUniqueNumber` como parámetro, para que el comercio pueda proveer
sus propios valores.

## [1.2.1] - 2018-08-16

### Changed

- Evita `NullPointerException` cuando `appScheme` es `null` en peticiones para canales
 `MOBILE` y `WEB`.

## [1.2.0] - 2018-08-14

### Added

- Agrega nuevo parámetro de configuración `callbackUrl` a `Onepay`.
- Agrega nuevo parámetro de configuración `appScheme` a `Onepay`
- Agrega nuevo parámetro opcional `channel` a `Transaction.create`

## [1.1.1] - 2018-08-03

### Changed

- Cambia internamente la forma de encodear en base64.

## [1.1.0] - 2018-08-03

### Changed

- Configura los setters de `cl.transbank.onepay.model.Item` para usar
*method chaining*.

## [1.0.1] - 2018-08-02

### Changed

- Evita que `cl.transbank.onepay.model.Item.id` sea serializado en JSON.
