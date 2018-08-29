# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [1.3.1] - 2018-08-29
### Changed
- Environments are now pointing to the official TEST and LIVE servers. From now
on, the SDK can be used for official testing and will be interoperable with
tools provided by Transbank to assist the integration (such as the dashboard
to simulate transactions).

## [1.3.0] - 2018-08-16
### Added
- Add externalUniqueNumber as a param, so the commerce can provide its own

## [1.2.1] - 2018-08-16
### Changed
- Prevent NullPointerException when appScheme is null on request for MOBILE and WEB

## [1.2.0] - 2018-08-14
### Added
- Add new `callbackUrl` config param to `Onepay`. You could set it with the following setter:
    `cl.transbank.onepay.Onepay.setCallbackUrl("a-callback-endpoint")`
- Add new `appScheme` config param to `Onepay`
- Add new optional param `channel` to `Transaction.create`

## [1.1.1] - 2018-08-03
### Added
- `cl.transbank.onepay.util.Base64Coder`
### Changed
- Now use the new `Base64Coder.encode` in order to encode to base64
### Removed
- `cl.transbank.onepay.util.BASE64Encoder`
- `cl.transbank.onepay.util.CharacterEncoder`

## [1.1.0] - 2018-08-03
### Changed
- Configure the setters of `cl.transbank.onepay.model.Item` as method chaining.


## [1.0.1] - 2018-08-02
### Changed
- Set `cl.transbank.onepay.model.Item.id` as `transient` so it is not serialized to json.


