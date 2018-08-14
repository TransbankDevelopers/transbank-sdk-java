# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [1.0.1] - 2018-08-02
### Changed
- Set `cl.transbank.onepay.model.Item.id` as `transient` so it is not serialized to json.

## [1.1.0] - 2018-08-03
### Changed
- Configure the setters of `cl.transbank.onepay.model.Item` as method chaining.

## [1.1.1] - 2018-08-03
### Added
- `cl.transbank.onepay.util.Base64Coder`
### Changed
- Now use the new `Base64Coder.encode` in order to encode to base64
### Removed
- `cl.transbank.onepay.util.BASE64Encoder`
- `cl.transbank.onepay.util.CharacterEncoder`

## [1.2.0] - 2018-08-03
### Added
- Add new callbackUrl config param to Onepay. You could set it with the following setter:
    `cl.transbank.onepay.Onepay.setCallbackUrl("a-callback-endpoint")`
- Add new optional param `channel` to `Transaction.create`