# Changelog
Todos los cambios notables a este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
y este proyecto adhiere a [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [1.4.0] - 2018-09-06
### Changed
- Incluye credenciales pre-configuradas para Onepay
- Incluye soporte de Webpay
- Incluye soporte experimental de PatPass by Webpay.


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


