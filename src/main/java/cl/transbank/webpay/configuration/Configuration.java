/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.transbank.webpay.configuration;

import cl.transbank.webpay.Webpay;

import java.util.ArrayList;

public class Configuration {

    private String privateKey;
    private String publicCert;
    private String webpayCert;
    private String commerceCode;
    @Deprecated
    private ArrayList storeCodes;
    private Webpay.Environment environment = Webpay.Environment.INTEGRACION;

    public Configuration() {
    }

    @Deprecated
    public Configuration(String privateKey, String publicCert, String webpayCert, String commerceCode, String environment) {
        this.privateKey = privateKey;
        this.publicCert = publicCert;
        this.webpayCert = webpayCert;
        this.commerceCode = commerceCode;
        setEnvironment(environment);
    }
    
    @Deprecated
    public Configuration(String privateKey, String publicCert, String webpayCert, String commerceCode, String environment, ArrayList storeCodes) {
        this.privateKey = privateKey;
        this.publicCert = publicCert;
        this.webpayCert = webpayCert;
        this.commerceCode = commerceCode;
        setEnvironment(environment);
        this.storeCodes = storeCodes;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicCert() {
        return publicCert;
    }

    public void setPublicCert(String public_cert) {
        this.publicCert = public_cert;
    }

    public String getWebpayCert() {
        return webpayCert;
    }

    @Deprecated
    public void setWebpayCert(String webpayCert) {
        this.webpayCert = webpayCert;
    }

    public String getCommerceCode() {
        return commerceCode;
    }

    public void setCommerceCode(String commerceCode) {
        this.commerceCode = commerceCode;
    }

    public Webpay.Environment getEnvironment() {
        return environment;
    }

    @Deprecated
    public void setEnvironment(String environment) {
        this.environment = Webpay.Environment.valueOf(environment);
    }

    public void setEnvironment(Webpay.Environment environment) {
        this.environment = environment;
    }

    @Deprecated
    public ArrayList getStoreCodes() {
        return storeCodes;
    }

    @Deprecated
    public void setStoreCodes(ArrayList storeCodes) {
        this.storeCodes = storeCodes;
    }

    /**
     * @return a configuration for commerce code 597020000541 for the TEST
     * environment to be used with {@link Webpay#getNormalTransaction()} and
     * {@link Webpay#getNullifyTransaction()}.
     */
    public static Configuration forTestingWebpayPlusNormal() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597020000541");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpQIBAAKCAQEA0ClVcH8RC1u+KpCPUnzYSIcmyXI87REsBkQzaA1QJe4w/B7g\n" +
            "6KvKV9DaqfnNhMvd9/ypmGf0RDQPhlBbGlzymKz1xh0lQBD+9MZrg8Ju8/d1k0pI\n" +
            "b1QLQDnhRgR2T14ngXpP4PIQKtq7DsdHBybFU5vvAKVqdHvImZFzqexbZjXWxxhT\n" +
            "+/sGcD4Vs673fc6B+Xj2UrKF7QyV5pMDq0HCCLTMmafWAmNrHyl6imQM+bqC12gn\n" +
            "EEAEkrJiSO6P/21m9iDJs5KQanpJby0aGW8mocYRHDMHZjtTiIP0+JAJgL9KsH+r\n" +
            "Xdk2bT7aere7TzOK/bEwhkYEXnMMt/65vV6AfwIDAQABAoIBAHnIlOn6DTi99eXl\n" +
            "KVSzIb5dA747jZWMxFruL70ifM+UKSh30FGPoBP8ZtGnCiw1ManSMk6uEuSMKMEF\n" +
            "5iboVi4okqnTh2WSC/ec1m4BpPQqxKjlfrdTTjnHIxrZpXYNucMwkeci93569ZFR\n" +
            "2SY/8pZV1mBkZoG7ocLmq+qwE1EaBEL/sXMvuF/h08nJ71I4zcclpB8kN0yFrBCW\n" +
            "7scqOwTLiob2mmU2bFHOyyjTkGOlEsBQxhtVwVEt/0AFH/ucmMTP0vrKOA0HkhxM\n" +
            "oeR4k2z0qwTzZKXuEZtsau8a/9B3S3YcgoSOhRP/VdY1WL5hWDHeK8q1Nfq2eETX\n" +
            "jnQ4zjECgYEA7z2/biWe9nDyYDZM7SfHy1xF5Q3ocmv14NhTbt8iDlz2LsZ2JcPn\n" +
            "EMV++m88F3PYdFUOp4Zuw+eLJSrBqfuPYrTVNH0v/HdTqTS70R2YZCFb9g0ryaHV\n" +
            "TRwYovu/oQMV4LBSzrwdtCrcfUZDtqMYmmZfEkdjCWCEpEi36nlG0JMCgYEA3r49\n" +
            "o+soFIpDqLMei1tF+Ah/rm8oY5f4Wc82kmSgoPFCWnQEIW36i/GRaoQYsBp4loue\n" +
            "vyPuW+BzoZpVcJDuBmHY3UOLKr4ZldOn2KIj6sCQZ1mNKo5WuZ4YFeL5uyp9Hvio\n" +
            "TCPGeXghG0uIk4emSwolJVSbKSRi6SPsiANff+UCgYEAvNMRmlAbLQtsYb+565xw\n" +
            "NvO3PthBVL4dLL/Q6js21/tLWxPNAHWklDosxGCzHxeSCg9wJ40VM4425rjebdld\n" +
            "DF0Jwgnkq/FKmMxESQKA2tbxjDxNCTGv9tJsJ4dnch/LTrIcSYt0LlV9/WpN24LS\n" +
            "0lpmQzkQ07/YMQosDuZ1m/0CgYEAu9oHlEHTmJcO/qypmu/ML6XDQPKARpY5Hkzy\n" +
            "gj4ZdgJianSjsynUfsepUwK663I3twdjR2JfON8vxd+qJPgltf45bknziYWvgDtz\n" +
            "t/Duh6IFZxQQSQ6oN30MZRD6eo4X3dHp5eTaE0Fr8mAefAWQCoMw1q3m+ai1PlhM\n" +
            "uFzX4r0CgYEArx4TAq+Z4crVCdABBzAZ7GvvAXdxvBo0AhD9IddSWVTCza972wta\n" +
            "5J2rrS/ye9Tfu5j2IbTHaLDz14mwMXr1S4L39UX/NifLc93KHie/yjycCuu4uqNo\n" +
            "MtdweTnQt73lN2cnYedRUhw9UTfPzYu7jdXCUAyAD4IEjFQrswk2x04=\n" +
            "-----END RSA PRIVATE KEY-----"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDujCCAqICCQCZ42cY33KRTzANBgkqhkiG9w0BAQsFADCBnjELMAkGA1UEBhMC\n" +
            "Q0wxETAPBgNVBAgMCFNhbnRpYWdvMRIwEAYDVQQKDAlUcmFuc2JhbmsxETAPBgNV\n" +
            "BAcMCFNhbnRpYWdvMRUwEwYDVQQDDAw1OTcwMjAwMDA1NDExFzAVBgNVBAsMDkNh\n" +
            "bmFsZXNSZW1vdG9zMSUwIwYJKoZIhvcNAQkBFhZpbnRlZ3JhZG9yZXNAdmFyaW9z\n" +
            "LmNsMB4XDTE2MDYyMjIxMDkyN1oXDTI0MDYyMDIxMDkyN1owgZ4xCzAJBgNVBAYT\n" +
            "AkNMMREwDwYDVQQIDAhTYW50aWFnbzESMBAGA1UECgwJVHJhbnNiYW5rMREwDwYD\n" +
            "VQQHDAhTYW50aWFnbzEVMBMGA1UEAwwMNTk3MDIwMDAwNTQxMRcwFQYDVQQLDA5D\n" +
            "YW5hbGVzUmVtb3RvczElMCMGCSqGSIb3DQEJARYWaW50ZWdyYWRvcmVzQHZhcmlv\n" +
            "cy5jbDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANApVXB/EQtbviqQ\n" +
            "j1J82EiHJslyPO0RLAZEM2gNUCXuMPwe4OirylfQ2qn5zYTL3ff8qZhn9EQ0D4ZQ\n" +
            "Wxpc8pis9cYdJUAQ/vTGa4PCbvP3dZNKSG9UC0A54UYEdk9eJ4F6T+DyECrauw7H\n" +
            "RwcmxVOb7wClanR7yJmRc6nsW2Y11scYU/v7BnA+FbOu933Ogfl49lKyhe0MleaT\n" +
            "A6tBwgi0zJmn1gJjax8peopkDPm6gtdoJxBABJKyYkjuj/9tZvYgybOSkGp6SW8t\n" +
            "GhlvJqHGERwzB2Y7U4iD9PiQCYC/SrB/q13ZNm0+2nq3u08ziv2xMIZGBF5zDLf+\n" +
            "ub1egH8CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAdgNpIS2NZFx5PoYwJZf8faze\n" +
            "NmKQg73seDGuP8d8w/CZf1Py/gsJFNbh4CEySWZRCzlOKxzmtPTmyPdyhObjMA8E\n" +
            "Adps9DtgiN2ITSF1HUFmhMjI5V7U2L9LyEdpUaieYyPBfxiicdWz2YULVuOYDJHR\n" +
            "n05jlj/EjYa5bLKs/yggYiqMkZdIX8NiLL6ZTERIvBa6azDKs6yDsCsnE1M5tzQI\n" +
            "VVEkZtEfil6E1tz8v3yLZapLt+8jmPq1RCSx3Zh4fUkxBTpUW/9SWUNEXbKK7bB3\n" +
            "zfB3kGE55K5nxHKfQlrqdHLcIo+vdShATwYnmhUkGxUnM9qoCDlB8lYu3rFi9w==\n" +
            "-----END CERTIFICATE-----"
        );
        return configuration;
    }

    /**
     * @return a configuration for commerce code 597020000546 for the TEST
     * environment to be used with {@link Webpay#getNormalTransaction()} plus
     * {@link Webpay#getCaptureTransaction()}.
     */
    public static Configuration forTestingWebpayPlusCapture() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597020000546");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAxjhtR6728EnnGnFOWmDzZRmQDnpvLzDpfq2pIZI9wrrc7ZTz\n" +
            "FQTUkh3m9+QjjSv8+Ef0QbbkiJt27A5lQMjU/Zpf2wH1znsQSjLGpSeiCAiVpOec\n" +
            "ySqiFn0COm9aw/mnyZY87KCphwfX5chUP+JGTTMcdAALhdRuIdDzh46jKkJcPnGQ\n" +
            "FPq/UpFmHzZXTQ37S3RWMQMH1HsNQu7R16hAQkyolt46e/1y1nsVh0uv793daK89\n" +
            "lvPFwgPlXjePJfufdUQ58K8/3U/kG8PxlmoCXOsjKjQrjQG5tXM3DEGfro98Gzuj\n" +
            "uITr4gEzRAi2UuzBvMvahTULTUcjuxYlHqdCJwIDAQABAoIBAHohslOEnmoXXumP\n" +
            "/rL5IX6dbYE+Nttgy71dyuQAc0VUVWOdbtj4jPEqs3DxhGYrQEbKLtl+kvkIsRFp\n" +
            "HUH5fCJ1x7HtV0LN2I+fEX4ZGWDRyUI94wCf4BbzFzhh/A7b+GHgy9EQfOPSFVhj\n" +
            "QmXKSX6vi0x96pue8+yqDiLr4+TYv1gEiH0vvw/ibjk0U/pu6OfIVfUJTJ2tY+J1\n" +
            "FdajWzZG+CAlAWV6Tm7f+uabioFp4887DoFqEF4IGu1kDOACwYI0n6o4sj3ZXLmr\n" +
            "AHX5GMOh8qvifFsmjL4K+lzUUmMMs+NNi8x44uRVPPrJUONKDOVIiFDhke31mj3o\n" +
            "HXezdQECgYEA+hrXlmKfAP6sTeqGg+kcc7vL/TTHbEbaamHjRUtiW73rad/GW/Xz\n" +
            "GDMsglAacDZ1z79OGfG5oxjQXN0kuJKqWol0aC8JcH/WglBT3PSDRYM0sJNBx5Mp\n" +
            "cg403dkfM3axEVDAwB8xjLpgHY/H9SeXDRORtBIUhyO5bJYVxGubLSECgYEAyuSC\n" +
            "QuxAbh6HqIbVhvdJbacH473ULstqNKthDtg9xBkL3v2CHTINk0HcrRc/8zw0xQAw\n" +
            "2DL7FNbyM76gZ1MizT7ZaykvwrHfKDXYpeITRrkiaXle7QgNiN+EYz5ABZGR1uWN\n" +
            "8H5w0VBN7IvQ5c8vxHBJdPpUaJMZ37JjiSX3/kcCgYBeGslxgUwYoLqOWqcgbQ7S\n" +
            "kR/Q9xHuML6v9oMAKLwqjsxMOvG02lcMjPy7T46TGDq931pwsp5JuuVze5X8iNrm\n" +
            "U//jz4b6uG8q+zSC19GozxR9N/sxL7MRgjzsGGz//THktQDBiTsom1vc46O2H55b\n" +
            "Qji5i3AD5TI4pEQuctqhYQKBgQCAU3VcNKcvvxmYauek/MUxmIKx9b+9dSUQeRDj\n" +
            "Xbv7SsgqWvcv1hel+vNDe0AUbREHRO6f3+bUsHryZXB4yalqXYUQdTVjFDOL8Dq9\n" +
            "+LaudawhQAXdL8m3t3+5cYb2vrKaVAipgp+ClCMlKO2QXLHeshKT7Tz0A45K4T55\n" +
            "YU1wQwKBgEY4KoVPJwBLytrncyP4Ztgs6Qy3ibwh6LmI7eK9MflOpAiDktw+rVCO\n" +
            "mRmQM1A6nXhwYSd4Hghv7P+fYFeJ23tUAgyJO3pXiQZVUQiVfsqtVZ0hpyrH1L9+\n" +
            "7YAgwQAPiOiandz4yQI84v3H9STd2QJo3jPyuWcT+VfXcUjmtA2i\n" +
            "-----END RSA PRIVATE KEY-----"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDujCCAqICCQCfLrBv34+BHDANBgkqhkiG9w0BAQsFADCBnjELMAkGA1UEBhMC\n" +
            "Q0wxETAPBgNVBAgMCFNhbnRpYWdvMRIwEAYDVQQKDAlUcmFuc2JhbmsxETAPBgNV\n" +
            "BAcMCFNhbnRpYWdvMRUwEwYDVQQDDAw1OTcwMjAwMDA1NDYxFzAVBgNVBAsMDkNh\n" +
            "bmFsZXNSZW1vdG9zMSUwIwYJKoZIhvcNAQkBFhZpbnRlZ3JhZG9yZXNAdmFyaW9z\n" +
            "LmNsMB4XDTE2MDcwNDIzNDcxOFoXDTI0MDcwMjIzNDcxOFowgZ4xCzAJBgNVBAYT\n" +
            "AkNMMREwDwYDVQQIDAhTYW50aWFnbzESMBAGA1UECgwJVHJhbnNiYW5rMREwDwYD\n" +
            "VQQHDAhTYW50aWFnbzEVMBMGA1UEAwwMNTk3MDIwMDAwNTQ2MRcwFQYDVQQLDA5D\n" +
            "YW5hbGVzUmVtb3RvczElMCMGCSqGSIb3DQEJARYWaW50ZWdyYWRvcmVzQHZhcmlv\n" +
            "cy5jbDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAMY4bUeu9vBJ5xpx\n" +
            "Tlpg82UZkA56by8w6X6tqSGSPcK63O2U8xUE1JId5vfkI40r/PhH9EG25IibduwO\n" +
            "ZUDI1P2aX9sB9c57EEoyxqUnoggIlaTnnMkqohZ9AjpvWsP5p8mWPOygqYcH1+XI\n" +
            "VD/iRk0zHHQAC4XUbiHQ84eOoypCXD5xkBT6v1KRZh82V00N+0t0VjEDB9R7DULu\n" +
            "0deoQEJMqJbeOnv9ctZ7FYdLr+/d3WivPZbzxcID5V43jyX7n3VEOfCvP91P5BvD\n" +
            "8ZZqAlzrIyo0K40BubVzNwxBn66PfBs7o7iE6+IBM0QItlLswbzL2oU1C01HI7sW\n" +
            "JR6nQicCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAIn1CPsoAtsyLiJpcQ44rASNA\n" +
            "FO4Le7c1MRNKgkWbJ/YWllC8anHhw63TbPLfukY7DJ662Yr6W7pMWtIjMXWFpE7g\n" +
            "oGV1p2uoAvD0Rq1wLFeU0KWt/7uHo4k88QQm9JzF8ElqKJ0Y+93RrDaZEDpSBCEQ\n" +
            "+rZgfodsxFftO62WDmRxtznnyu5jcyhLNz0NF4wH96KK3q6QJaTtzWxQvOoMlQ9J\n" +
            "m8n7joymHLt8YQrneaX59hVxl3c9Dqi7JPbwcUuCOMKF9eurBQ6/QVpy9kj6nJST\n" +
            "kdRxkc9RTcNXCaAydy+wgrTGoLicpb0qgHz5zdtIkbtgdKmO+JVoE6ueu8lbfA==\n" +
            "-----END CERTIFICATE-----"
        );
        return configuration;
    }

    /**
     * @return a configuration for commerce code 597020000542 (which is the
     * parent for stores 597020000543 and 597020000544) for the TEST environment
     * to be used with {@link Webpay#getMallNormalTransaction()} and
     * {@link Webpay#getNullifyTransaction()}.
     */

    public static Configuration forTestingWebpayPlusMall() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597020000542");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEApEXusrSCV0B8JwJyBM3926Wr3Zp/tY1mXwljUxN2qr9kzJ9I\n" +
            "qUMu5BumTvF0Et4EnZSCZJy3Xkig6YMi7RqyT+30d5FQlNfkU/vSE93Mtr/ArRbq\n" +
            "jtZDUU5TXwArjGMakkzypwDj5HbuNBH4tpKvGnY74KrJZZSjJGGB/RyIF4UgaTjG\n" +
            "h5DR56HPlDBat6SS+ffnJoTIL4xmbqHMHeIHzOUVjU6kK+ShNi35jl7QfzlMN13T\n" +
            "E8vX3uVxDKp8Q4Yso2CiaDrS7y030h1Hwl6A/HBwaHVoUD7LhO6M7mAzd8gR6SHA\n" +
            "LUgOwsX06aMLuhopA+0yPr9HV3c57dDwBYlLrQIDAQABAoIBAFrheZISPT3KJiVq\n" +
            "u+uejsASoseBrv+hD66qQfH3BaKnKjvuL0O9MFbwWQy5lg7OF12aiJzi+qtFoQgv\n" +
            "DYaBS37e1W3EzgDag65W1b056wR7hzv7Pp7xOOLlY0hejrknJs8jlOcBnhKKHXRf\n" +
            "MOrIsekA2lWMBsmU9sCs1T5Tp5Lip8bD/aDq9C1B0eq6BwrOMfAirei0lV6bTKW1\n" +
            "IJJ8i3j+DwRQI5QLr1AVNWqthTY/aL3sGkO8OxRGfmfYi5KLNihyFRNX6JLEsKU/\n" +
            "wrthyKs897R1zay7Pp/hAYQ8pPtsIV/YQS2QM021NocJLo45R0pnR+KOdNfd03lm\n" +
            "pj7fnUECgYEAztA2m+IDPUPsM497pcyBwTnopiradAYVEdlEMLZlaISmGIsQqGZJ\n" +
            "eG1ZU69ZHfWWMi1UKRZ65abCzjJquHafCHDxAwIAgLvW0y0+GKoXoBJu3rN7CZ93\n" +
            "OriQ0ayU9nTBjYRHbnSH8nwJiZ15gSCzLOJ+AdGpod3mEhUa467zA7ECgYEAy1er\n" +
            "m0s4qS12u44AbzWW5/03DgJVkIBdVMKKqNC0YYEaJw15f7pmHeQ3J8ZcEmGc1sT/\n" +
            "0CHxkkvH/GpJrqOK9Xxd8i7fO0UHnSpYMpFUOuGmGR8GtgcXFnwd5JFGbpWls8Ow\n" +
            "EVuKFTdceDPljLi9Jm0tc7OgKuRFTqEfDriXMr0CgYBuDDtSvXRN0GvKj+oSsnzF\n" +
            "DgRvD1SI8oeZpMv1Q8k4UYV0f+NQSIWF0GH89sxr9beDYb1r06t3skHsqMVC/NPp\n" +
            "EPgeSp1r4wgP/P4S78d8hPJ8DHNHDpTKKVXeTIBDmKM5o47DBr0kWb5VfPcfr//H\n" +
            "vYmhfChQmpwHOTXCu+BSMQKBgHvRHHtpKE1Lk7rM0tLkzMjiVP3Ayh09LJeKBiiZ\n" +
            "PN0KYcRZ3hu6gqe86SDdFf9TVM8qEaLIqHIuls3KYqdmihzE5+eqRt+uPt6ihCX2\n" +
            "fFWGRT+StuFsG9DjLsiY9Uws70Mw6ysGlGQq27GN2D8B1ptpa33CaMr1SIaCcYjj\n" +
            "OZ35AoGAT/UQVsYRFv3l+gEcerxg9PdtbPiQ88cd1cr/hsZpRFs104qNSjO79nul\n" +
            "ErLrqFaws1DcozVmv1KH5o69IrB4pJwjuBl9RVfmlXdYKjvn7ae2oJIsQgM1Ce4Q\n" +
            "0pahLmQG0n4Qfpk0ADeYHvtprATasclmHGdtH4y7wcPyWPY9ITk=\n" +
            "-----END RSA PRIVATE KEY-----"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDujCCAqICCQC79QjbBVPw0TANBgkqhkiG9w0BAQsFADCBnjELMAkGA1UEBhMC\n" +
            "Q0wxETAPBgNVBAgMCFNhbnRpYWdvMRIwEAYDVQQKDAlUcmFuc2JhbmsxETAPBgNV\n" +
            "BAcMCFNhbnRpYWdvMRUwEwYDVQQDDAw1OTcwMjAwMDA1NDIxFzAVBgNVBAsMDkNh\n" +
            "bmFsZXNSZW1vdG9zMSUwIwYJKoZIhvcNAQkBFhZpbnRlZ3JhZG9yZXNAdmFyaW9z\n" +
            "LmNsMB4XDTE2MDcwNDE2MDgwMVoXDTI0MDcwMjE2MDgwMVowgZ4xCzAJBgNVBAYT\n" +
            "AkNMMREwDwYDVQQIDAhTYW50aWFnbzESMBAGA1UECgwJVHJhbnNiYW5rMREwDwYD\n" +
            "VQQHDAhTYW50aWFnbzEVMBMGA1UEAwwMNTk3MDIwMDAwNTQyMRcwFQYDVQQLDA5D\n" +
            "YW5hbGVzUmVtb3RvczElMCMGCSqGSIb3DQEJARYWaW50ZWdyYWRvcmVzQHZhcmlv\n" +
            "cy5jbDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKRF7rK0gldAfCcC\n" +
            "cgTN/dulq92af7WNZl8JY1MTdqq/ZMyfSKlDLuQbpk7xdBLeBJ2UgmSct15IoOmD\n" +
            "Iu0ask/t9HeRUJTX5FP70hPdzLa/wK0W6o7WQ1FOU18AK4xjGpJM8qcA4+R27jQR\n" +
            "+LaSrxp2O+CqyWWUoyRhgf0ciBeFIGk4xoeQ0eehz5QwWrekkvn35yaEyC+MZm6h\n" +
            "zB3iB8zlFY1OpCvkoTYt+Y5e0H85TDdd0xPL197lcQyqfEOGLKNgomg60u8tN9Id\n" +
            "R8JegPxwcGh1aFA+y4TujO5gM3fIEekhwC1IDsLF9OmjC7oaKQPtMj6/R1d3Oe3Q\n" +
            "8AWJS60CAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAZ6unfp9XlP/2zk0bHtXHNlEm\n" +
            "W81TMIDytgNi8LHkdLboZUCLOirYgcKqGsX2kF0y6Kmv1Cd23H5Tuz1+eC/gRY7M\n" +
            "n4E7KYPZ4asjWhXJUlTi3IP34fKSh1xihK+EN9Ke1GZXIu+tZOs2U0DP7N0BTCGh\n" +
            "0FQJGI381EpGP+tYdc9ZKMLULj31urcY/P/6XjMSj9aCVutnc22FyRmPupwClLgj\n" +
            "D2t6Fqp2aT4rrCZj5bpm42h3sXQLLHvkha2k1v+8QCY45JOiLrbtByFHOP4+7Gp8\n" +
            "UOMdL/AMq4KIS4NlJI7digGw6i2CseDm23JUMloLueWmCRO+92rTm4aQ30T4uQ==\n" +
            "-----END CERTIFICATE-----"
        );
        return configuration;
    }

    /**
     * @return a configuration for commerce code 597020000547 for the TEST
     * environment to be used with {@link Webpay#getOneClickTransaction()}
     */
    public static Configuration forTestingWebpayOneClickNormal() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597020000547");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEA5Fqy3C/dhgdVsGkv5p5GLvd9OxeOv+poK41zTxIUXOLpG6NE\n" +
            "W+Hb5Qz0rTk1TEVJUCSVb0bV8nfsD0oHolD/7IU/zvvEWJ8zI5ca7VZ2uZ27Luee\n" +
            "AaszRfRGWkJ8TqRB8qE/QuHIUVJEgY9YxpEmD25hEIlZU94FRRRcHSpJ1+8o3K9D\n" +
            "Sqkbe9aWGGvs+hxzrlThO48HAUwL0F2oyMqysSQhRqZffeSZMp4qsKlE7iIr5pk8\n" +
            "373QqLrfbwm2fc56+RG0gVQ47SKTfpPx2RVM9nbJ3AoYtpoEc4HRaUnVCJ5YiGYy\n" +
            "69CsUNJPsj9ZTL1avTJK6XnygoVPol9d7X13KwIDAQABAoIBAQDPB6/rUvYbIqE8\n" +
            "nFESW+KziCwgm/4O3x1shwTI5lJR2GORbBd42i973aAjQJ+is5qBL3nP9j/YYYNC\n" +
            "ZVLAhYFR1YkBRl9AHa3GkaOXE/H13RwsrU8ioi2NOadjA64humgT6r8pCvyLRfPY\n" +
            "JrdM56HDEcassGmtULgkZg4RXxqtym6dcpPmuXtCHFZ7JEWVSKWH+STReAh7unwD\n" +
            "TYOZlsnU5FuSzYlN2IC0OtZORviTSiEkI1XMyrDm97HQWOCkwa6OizKf/xFdvX5X\n" +
            "xcksALubSX1I+2sUHqP0LbZ3rkxgG6VoaJGF3zZdsjUPfFHBy/IGnbpcMqe/YkZl\n" +
            "AduyI4KhAoGBAP5iLhQr9k/vKUXNgl/9H5wMWBqf3WvOgH4WiApUZ5i5e5JkOgav\n" +
            "6LSsGdLYhEADYAKYgRUOpwDE6IBcWobTCwe6O0R7Us9r/c1ex3zEPcqiMqULyAxx\n" +
            "LLu34O75t7ctPvhewKfNs16qzl751ZWYInBbQoZf8CkZMDSxsQGCbTIRAoGBAOXO\n" +
            "LOutW0anleTsBbQwbR1k0hIPk6CwQnutq1BcsELYBSNKh6DIA16zBqyE9oFDsS34\n" +
            "VLvXETZZKGEUoPju28DVH+Scic5E15CPNUaJie+Ief97fA/iPLJNJFSpcsJG/+Rd\n" +
            "jB1hurkcvjzp67wNk9z+WJBxcMfAo/KwSxbkNtl7AoGBAKUitycBIvThHLnjny8Q\n" +
            "8uQqX0dpYCQL+f3gQo/yGw5Z2o494i1VJIuk7V6ij7e+eSU2OxWgXWlyajxpt5qu\n" +
            "hgqOKstaA3gDcs9PJ9Em07YndRkPfN4W2iNCSxLXqRuQk8BIQmiscDSUTUP6i1yB\n" +
            "Vln55EW3IgCMCW8rqux/7sMBAoGAHkrqUvrcIFkxAic2rUUA7TIAGw9gl3sEmIcR\n" +
            "IRvGxFjzfG5zqHcVMqOIyq8QS4Pf1D569PPpue9QylNM0OOzphyyApG7/KvIeq7W\n" +
            "CAFTZHbqFgpyFSnudFaE5oAbt45iZvkJ4kmisooebassPvLPPf9tL0U056/2LKSe\n" +
            "kVrt/AcCgYAWmgeAtQ4H6mX3yPeEa4uDJevjaVKxlKYUPdd8kCD3OTjX9/Pig2GH\n" +
            "XhI5UeVd2k4EBdrY1DRVr+cJ8/fEKzcjOKyrbs0XTld8XCBeVuQKAliYS7PpG70C\n" +
            "3/jXM8HZyqbPB+apW35Ucqo84ClgPN8LUOi/tE/aP11awxmgTk9F+w==\n" +
            "-----END RSA PRIVATE KEY-----"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDujCCAqICCQCFNCTEl24W2TANBgkqhkiG9w0BAQsFADCBnjELMAkGA1UEBhMC\n" +
            "Q0wxETAPBgNVBAgMCFNhbnRpYWdvMRIwEAYDVQQKDAlUcmFuc2JhbmsxETAPBgNV\n" +
            "BAcMCFNhbnRpYWdvMRUwEwYDVQQDDAw1OTcwMjAwMDA1NDcxFzAVBgNVBAsMDkNh\n" +
            "bmFsZXNSZW1vdG9zMSUwIwYJKoZIhvcNAQkBFhZpbnRlZ3JhZG9yZXNAdmFyaW9z\n" +
            "LmNsMB4XDTE2MDYyMzE2MzcxM1oXDTI0MDYyMTE2MzcxM1owgZ4xCzAJBgNVBAYT\n" +
            "AkNMMREwDwYDVQQIDAhTYW50aWFnbzESMBAGA1UECgwJVHJhbnNiYW5rMREwDwYD\n" +
            "VQQHDAhTYW50aWFnbzEVMBMGA1UEAwwMNTk3MDIwMDAwNTQ3MRcwFQYDVQQLDA5D\n" +
            "YW5hbGVzUmVtb3RvczElMCMGCSqGSIb3DQEJARYWaW50ZWdyYWRvcmVzQHZhcmlv\n" +
            "cy5jbDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAORastwv3YYHVbBp\n" +
            "L+aeRi73fTsXjr/qaCuNc08SFFzi6RujRFvh2+UM9K05NUxFSVAklW9G1fJ37A9K\n" +
            "B6JQ/+yFP877xFifMyOXGu1Wdrmduy7nngGrM0X0RlpCfE6kQfKhP0LhyFFSRIGP\n" +
            "WMaRJg9uYRCJWVPeBUUUXB0qSdfvKNyvQ0qpG3vWlhhr7Pocc65U4TuPBwFMC9Bd\n" +
            "qMjKsrEkIUamX33kmTKeKrCpRO4iK+aZPN+90Ki6328Jtn3OevkRtIFUOO0ik36T\n" +
            "8dkVTPZ2ydwKGLaaBHOB0WlJ1QieWIhmMuvQrFDST7I/WUy9Wr0ySul58oKFT6Jf\n" +
            "Xe19dysCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAubHNHDzyMLXuNQwawdhrzJYf\n" +
            "Cvi2NsAqVBKICp+VC94OqVsdWknrqm8+Wz+1DZV1ezTvoVgagiC/ZrfHvn9DEP45\n" +
            "7JttrOt2Sbr+F2Pj3oBl1RiQ2QkIXBRaSmipKaQB/cWRd0ZiO7uT5mP7eQtO5qFJ\n" +
            "4WST6dXtks2Oz4G7eMpqnctOfFiGBi1i6omD7LZg+qpbeTFWTEgZFcAUTrViRLl2\n" +
            "PEhUMVAobvvY7zUmzeu2mAMlWVNoaJysl6sH7Gii3T/xbxHsbxV8bZgvgQwiwFVP\n" +
            "+ffp06jqVndIhoeiTOz0MXgPIXIESaDraY2dgNTgEs2GwLNjy2cMB5pkjkAZ4g==\n" +
            "-----END CERTIFICATE-----"
        );
        return configuration;
    }
}
