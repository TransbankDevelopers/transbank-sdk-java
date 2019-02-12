/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.transbank.webpay.configuration;

import cl.transbank.patpass.PatPassByWebpayNormal;
import cl.transbank.webpay.Webpay;

import java.util.ArrayList;

public class Configuration {

    private String privateKey;
    private String publicCert;
    private String webpayCert;
    private String commerceCode;

    private String commerceMail;

    @Deprecated
    private ArrayList storeCodes;
    private Webpay.Environment environment = Webpay.Environment.INTEGRACION;
    private PatPassByWebpayNormal.Currency patPassCurrency = PatPassByWebpayNormal.Currency.DEFAULT;

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

    public void setPublicCert(String publicCert) {
        this.publicCert = publicCert;
    }

    public String getWebpayCert() {
        return webpayCert;
    }

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

    public String getCommerceMail() {
        return commerceMail;
    }

    public void setCommerceMail(String commerceMail) {
        this.commerceMail = commerceMail;
    }

    public PatPassByWebpayNormal.Currency getPatPassCurrency() {
        return patPassCurrency;
    }

    public void setPatPassCurrency(PatPassByWebpayNormal.Currency patPassCurrency) {
        this.patPassCurrency = patPassCurrency;
    }

    /**
     * @return a configuration for commerce code 597020000540 for the TEST
     * environment to be used with {@link Webpay#getNormalTransaction()} and
     * {@link Webpay#getNullifyTransaction()}.
     */
    public static Configuration forTestingWebpayPlusNormal() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597020000540");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAvuNgBxMAOBlNI7Fw5sHGY1p6DB6EMK83SL4b1ZILSJs/8/MC\n" +
            "X8Pkys3CvJmSIiKU7fnWkgXchEdqXJV+tzgoED/y99tXgoMssi0ma+u9YtPvpT7B\n" +
            "a5rk5HpLuaFNeuE3l+mpkXDZZKFSZJ1fV/Hyn3A1Zz+7+X2qiGrAWWdjeGsIkz4r\n" +
            "uuMFLQVdPVrdAxEWoDRybEUhraQJ1kwmx92HFfRlsbNAmEljG9ngx/+/JLA28cs9\n" +
            "oULy4/M7fVUzioKsBJmjRJd6s4rI2YIDpul6dmgloWgEfzfLNnAsZhJryJNBr2Wb\n" +
            "E6DL5x/U2XQchjishMbDIPjmDgS0HLLMjRCMpQIDAQABAoIBAEkSwa/zliHjjaQc\n" +
            "SRwNEeT2vcHl7LS2XnN6Uy1uuuMQi2rXnBEM7Ii2O9X28/odQuXWvk0n8UKyFAVd\n" +
            "NSTuWmfeEyTO0rEjhfivUAYAOH+coiCf5WtL4FOWfWaSWRaxIJcG2+LRUGc1WlUp\n" +
            "6VXBSR+/1LGxtEPN13phY0DWUz3FEfGBd4CCPLpzq7HyZWEHUvbaw89xZJSr/Zwh\n" +
            "BDZZyTbuwSHc9X9LlQsbaDuW/EyOMmDvSxmSRJO10FRMxyg8qbE4edtUK4jd61i0\n" +
            "kGFqdDu9sj5k8pDxOsN2F270SMlIwejZ1uunB87w9ezIcR9YLq9aa22cT8BZdOxb\n" +
            "uZ3PAAECgYEA6xfgRtcvpJUBWBVNsxrSg6Ktx2848eQne9NnbWHdZuNjH8OyN7SW\n" +
            "Fn0r4HsTw59/NJ1L5F3co5L5baEtRbRLWRpD72xjrXsQSsoKliCik1xgDIplMvOh\n" +
            "teA2GdeSv9wglqnotGcj5B/8+vn3tEzMjy+UUsyFn0fIaDC3zK3W2qUCgYEAz90g\n" +
            "va+FCcU8cnykb5Yn1u1izdK1c6S++v1bQFf6590ZMNy3p0uGrwAk/MzuBkJ421GK\n" +
            "p4pInUvO/Mb2BCcoHtr3ON3v0DCLl6Ae2Gb7lG0dLgcZ1EK7MDpMvKCqNHAv8Qu8\n" +
            "QBZOA08L8buVkkRt7jxJrPuOFDI5JAaWCmMOSgECgYEA3GvzfZgu9Go862B2DJL+\n" +
            "hCuYMiCHTM01c/UfyT/z/Y7/ln2+8FniS02rQPtE6ar28tb0nDahM8EPGon/T5ae\n" +
            "+vkUbzy6LKLxAJ501JPeurnm2Hs+LUqe+U8yioJD9p2m9Hx0UglOborLgGm0pRlI\n" +
            "xou+zu8x7ci5D292NXNcun0CgYAVKV378bKJnBrbTPUwpwjHSMOWUK1IaK1IwCJa\n" +
            "GprgoBHAd7f6wCWmC024ruRMntfO/C4xgFKEMQORmG/TXGkpOwGQOIgBme+cMCDz\n" +
            "xwg1xCYEWZS3l1OXRVgqm/C4BfPbhmZT3/FxRMrigUZo7a6DYn/drH56b+KBWGpO\n" +
            "BGegAQKBgGY7Ikdw288DShbEVi6BFjHKDej3hUfsTwncRhD4IAgALzaatuta7JFW\n" +
            "NrGTVGeK/rE6utA/DPlP0H2EgkUAzt8x3N0MuVoBl/Ow7y5sqIQKfEI7h0aRdXH5\n" +
            "ecefOL6iiJWQqX2+237NOd0fJ4E1+BCMu/+HnyCX+cFM2FgoE6tC\n" +
            "-----END RSA PRIVATE KEY-----\n"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDeDCCAmACCQDjtGVIe/aeCTANBgkqhkiG9w0BAQsFADB+MQswCQYDVQQGEwJj\n" +
            "bDENMAsGA1UECAwEc3RnbzENMAsGA1UEBwwEc3RnbzEMMAoGA1UECgwDdGJrMQ0w\n" +
            "CwYDVQQLDARjY3JyMRUwEwYDVQQDDAw1OTcwMjAwMDA1NDAxHTAbBgkqhkiG9w0B\n" +
            "CQEWDmNjcnJAZ21haWwuY29tMB4XDTE4MDYwODEzNDYwNloXDTIyMDYwNzEzNDYw\n" +
            "NlowfjELMAkGA1UEBhMCY2wxDTALBgNVBAgMBHN0Z28xDTALBgNVBAcMBHN0Z28x\n" +
            "DDAKBgNVBAoMA3RiazENMAsGA1UECwwEY2NycjEVMBMGA1UEAwwMNTk3MDIwMDAw\n" +
            "NTQwMR0wGwYJKoZIhvcNAQkBFg5jY3JyQGdtYWlsLmNvbTCCASIwDQYJKoZIhvcN\n" +
            "AQEBBQADggEPADCCAQoCggEBAL7jYAcTADgZTSOxcObBxmNaegwehDCvN0i+G9WS\n" +
            "C0ibP/PzAl/D5MrNwryZkiIilO351pIF3IRHalyVfrc4KBA/8vfbV4KDLLItJmvr\n" +
            "vWLT76U+wWua5OR6S7mhTXrhN5fpqZFw2WShUmSdX1fx8p9wNWc/u/l9qohqwFln\n" +
            "Y3hrCJM+K7rjBS0FXT1a3QMRFqA0cmxFIa2kCdZMJsfdhxX0ZbGzQJhJYxvZ4Mf/\n" +
            "vySwNvHLPaFC8uPzO31VM4qCrASZo0SXerOKyNmCA6bpenZoJaFoBH83yzZwLGYS\n" +
            "a8iTQa9lmxOgy+cf1Nl0HIY4rITGwyD45g4EtByyzI0QjKUCAwEAATANBgkqhkiG\n" +
            "9w0BAQsFAAOCAQEAhX2/fZ6+lyoY3jSU9QFmbL6ONoDS6wBU7izpjdihnWt7oIME\n" +
            "a51CNssla7ZnMSoBiWUPIegischx6rh8M1q5SjyWYTvnd3v+/rbGa6d40yZW3m+W\n" +
            "p/3Sb1e9FABJhZkAQU2KGMot/b/ncePKHvfSBzQCwbuXWPzrF+B/4ZxGMAkgxtmK\n" +
            "WnWrkcr2qakpHzERn8irKBPhvlifW5sdMH4tz/4SLVwkek24Sp8CVmIIgQR3nyR9\n" +
            "8hi1+Iz4O1FcIQtx17OvhWDXhfEsG0HWygc5KyTqCkVBClVsJPRvoCSTORvukcuW\n" +
            "18gbYO3VlxwXnvzLk4aptC7/8Jq83XY8o0fn+A==\n" +
            "-----END CERTIFICATE-----\n"
        );
        return configuration;
    }

    /**
     * @return a configuration for commerce code 597044444404 for the TEST
     * environment to be used with {@link Webpay#getNormalTransaction()} plus
     * {@link Webpay#getCaptureTransaction()}.
     */
    public static Configuration forTestingWebpayPlusCapture() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597044444404");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpQIBAAKCAQEAq62F11DVV2ciL/S/zKr8NmesZNUoI3t/9EbZ5m97LjH/R1s2\n" +
            "0MxJWjmy0f+I64PvJkGOvQauBdJoGiiCeiV3qY+PppgOQmMo8xXaPzErrVr0F9bx\n" +
            "3gbqSuqi/uwJNJRWUs3tYmlQ/WQrKHSMxpRkVthWoIpyR3UEBpr9N9MhAdDarJCv\n" +
            "/df9Hyu3RpE7ULFCnCHi/y3DVPYi3TnXa4xo9fE3iVuxMNjCUO93GqETYVCRoIW/\n" +
            "237frd8tgZ3biNmYRqbxO6jIv/1uEJs2mxPaW+FmdpW4+yM1tK8e8mWV/OrqpslZ\n" +
            "O0mqUVY16phJJW7ad4fi0V3+TCRwgY0MC6x6uwIDAQABAoIBAQCktQoflXHZNR1b\n" +
            "nRkWp0TqfXSsGMU1pZsRJZiQuIwZueYM87oXgKcvZQPm7Z7TNfUPYv4q5Gm5NDCk\n" +
            "SBFGVwQDLbTIREIJ91CmR2ToE6iv3P8qkBHkzgWicpKGuLXsOBTJxL/nFtuY/61Y\n" +
            "Vtlo+514pH4X8DvLyrxeCfy+vlSAg+mcs+35wnxC+qvAESYXQKODorGljmthkqyd\n" +
            "P/ONKef+PsyJuUevne2YSkiqaDywGBe5JRS+Ij74UH7d+/+hDS9AMwj+nrlVE/Bj\n" +
            "Y1zCw0BijKNuwnoF9oHMZvshfV2GkpS237tmXDbn+fJOqWfonb/CkBtWsF86N79h\n" +
            "/x+gJQ8hAoGBANeQ8/fDA8azfh6z6Ar6C0XIxgZppLtGPac65JhyKCYtvH1kzhJU\n" +
            "dnBqcMOxj9K3zbK3A21Y3gBMZvNS9Wja8Pk7r0R6aHU7eMOlEiOoEdeoVtlOpj4p\n" +
            "Y87Yc8vojd0nnuWJpxw6R8MvzJBm750tIE0/rv2+vfDeaxiWUexhrq0FAoGBAMvh\n" +
            "I5saf9OTJLvBfu8gqldLGdlkG1+1eyxnJbQuHq6o+WsaxjFEwS5a8MOlD7my0tom\n" +
            "zm98fiFHpGX3HGoG9RQuvRKmWRCdj5qb7Bep3gWb9HOKYG+6DKGPPpmJ6fdjV9zh\n" +
            "o5ru5iFJoWHcE60/kVbOJsh6ugVXFzF6DJaBkRS/AoGBAK3Bv4VkgjS2FeD1rwK6\n" +
            "DkAP197vZMM3mRalGAHxcn9jul1w1dJclqOCiKaVB5MYaQu3DWIkkb231/wmUH5W\n" +
            "jIq5G0udR3nHmE5LTlXDca5dmLPM+597iWH/g0dHiqJK/3+R90t/hrzEWKXE8zvE\n" +
            "VhcuUAVkrIHtJnJJKHvbOQtpAoGAXEqPVrAZO0p5r3C5KECOO7PogKs7ZQj/OCt9\n" +
            "OuJBy2j8d0qIe1cXaAeMw9PdmX9kyZIVkww1AJWwyuOg/jImETvTJTUeTlI05pU8\n" +
            "u72OntVpREBYxVrgSuZQPSrcObvD015lNEZ+8ISnRGhek+eZwETT857yxGYXPrN0\n" +
            "LVF7vnsCgYEApWvaUImePJDN50+nExK6TWHGFNnlZPlqFuyEHCSIlC0moGkcdl1D\n" +
            "uILlje5JjmCI6hUreePcpFbyuiWcFcRJPdTgPWAcuWpPqFk3TyMVg8RQjZ4nIb4e\n" +
            "TB3lRTP7u4t4emHHeNZhSeakRMOUYWiylCgSLmmf1OKd/bWTQ6G7lk0=\n" +
            "-----END RSA PRIVATE KEY-----\n"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDrDCCApQCCQDxS6RHDwxUnjANBgkqhkiG9w0BAQsFADCBlzELMAkGA1UEBhMC\n" +
            "Q0wxEzARBgNVBAgMClNvbWUtU3RhdGUxETAPBgNVBAcMCHNhbnRpYWdvMSEwHwYD\n" +
            "VQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQxFTATBgNVBAMMDDU5NzA0NDQ0\n" +
            "NDQwNDEmMCQGCSqGSIb3DQEJARYXYW1hbGRvbmFkb0B0cmFuc2JhbmsuY2wwHhcN\n" +
            "MTgwOTA0MTQxMDQ3WhcNMjIwMzI3MTQxMDQ3WjCBlzELMAkGA1UEBhMCQ0wxEzAR\n" +
            "BgNVBAgMClNvbWUtU3RhdGUxETAPBgNVBAcMCHNhbnRpYWdvMSEwHwYDVQQKDBhJ\n" +
            "bnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQxFTATBgNVBAMMDDU5NzA0NDQ0NDQwNDEm\n" +
            "MCQGCSqGSIb3DQEJARYXYW1hbGRvbmFkb0B0cmFuc2JhbmsuY2wwggEiMA0GCSqG\n" +
            "SIb3DQEBAQUAA4IBDwAwggEKAoIBAQCrrYXXUNVXZyIv9L/Mqvw2Z6xk1Sgje3/0\n" +
            "Rtnmb3suMf9HWzbQzElaObLR/4jrg+8mQY69Bq4F0mgaKIJ6JXepj4+mmA5CYyjz\n" +
            "Fdo/MSutWvQX1vHeBupK6qL+7Ak0lFZSze1iaVD9ZCsodIzGlGRW2FaginJHdQQG\n" +
            "mv030yEB0NqskK/91/0fK7dGkTtQsUKcIeL/LcNU9iLdOddrjGj18TeJW7Ew2MJQ\n" +
            "73caoRNhUJGghb/bft+t3y2BnduI2ZhGpvE7qMi//W4QmzabE9pb4WZ2lbj7IzW0\n" +
            "rx7yZZX86uqmyVk7SapRVjXqmEklbtp3h+LRXf5MJHCBjQwLrHq7AgMBAAEwDQYJ\n" +
            "KoZIhvcNAQELBQADggEBACo42LiT6Da6Dq6kLrz3ja8dBge2SfCu/gnA+57lENAx\n" +
            "D1Nq3lMqOE2dAoQXM+qwkBvduPaqFUzb4HV1b11PoAgeWR3ksoaKiWwjY5+p/snl\n" +
            "Z/EITwHYhfl9cmuVQFC09AQC/3brrP62fYzKP03CkGrxfVFP0Q0eLzP3w8x4XMzG\n" +
            "9VmMMHFICFYEEyUiQT22X8SpFtUakNCfJzK65zXGAxJqZKTVYhjcYB+HBIAqitGS\n" +
            "hF+F68G9XN7twijNIuseJt/I98R7UazON7EeP7kAz/UylVNOVmYq+pQbU4fG9QjQ\n" +
            "CZ8F118V03v3IQYXwTmOHge9moBwyTkcnI5nql346jg=\n" +
            "-----END CERTIFICATE-----\n"
        );
        return configuration;
    }

    /**
     * @return a configuration for commerce code 597044444401 (which is the
     * parent for stores 597044444402 and 597044444403) for the TEST environment
     * to be used with {@link Webpay#getMallNormalTransaction()} and
     * {@link Webpay#getNullifyTransaction()}.
     */

    public static Configuration forTestingWebpayPlusMall() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597044444401");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEowIBAAKCAQEAq5x8P9EIq8xT6UtRAL6pmNpcgYuIXHUvtPuY+Ao28LtbsJQV\n" +
            "gPXJ2CrMYtq3GH1kPAajdF0tdfMOSQgxTnnWsMFdY6jel2vhF1vfKvm79yLMrqIR\n" +
            "X7l/fZbldWJdoSuq1b3xTPYBKKGFhe/SvYpO88dvOuH4WIiAfRT1gFXkEW9xyA70\n" +
            "vK/4RZR93f220ELh8sHBMwP39XnNp7c52A+f1fkJVP5F8G5UTAC/g/jDZMCrtnCw\n" +
            "xu37jyXTEpQATXUN1XrsjJirpvNBfIvXQlk4AeXEj5a7PYE4nASZfNwSc1kpDm7G\n" +
            "6PheDkUXN1JEFscC/x9BpVvZIAjaXML/QCFV7wIDAQABAoIBAF/oGoBHwELS9GpD\n" +
            "D0gNRhcIof48Dr8tNrY8jebBPqcW7k0m1UW3F1DZylPMy9rB6Qyq4RqdIFT0ux0R\n" +
            "mQy0hslNp3WU4KFbRvaY/4Wy/9tD9YP7Sx5mOtvjQuVxTcZO8zB08LAEI+2jJ04N\n" +
            "E4eeDjWrVXxg4TwJPVWqKvHIDqe26CfMlKohSpcCpmq3HQknnFfuxGGlNGdrX4YR\n" +
            "v4BeoSsAG8Ak+cCkGBJ2LcrZpw+GJjs0SkvOVO1+G+vixYPDcor1moB1AnQ/tkrz\n" +
            "gSrRIl+Et3nq5XmmxQejOgMMWaXR2RXutdgXq4w3s4FSwABv5Zw1zAA/yapfk1uH\n" +
            "zJ/OpuECgYEA22kVGXhoR0onMSKKHnbtO3s3tmrgLwVQAMaEwYy8KNnIWkCLszlT\n" +
            "KtJ7nmEDdMysbHpb1EeNAoKg/DKY0YgneWrmmh3JozUp18dXEeHqEVPH1X9XT017\n" +
            "M24nqe65deFu9SKhZv9SQdj69iJLnRxPHSae/p5wb2ORr/XG+9ZX6OkCgYEAyDrK\n" +
            "95yH2b5CcZXvT+9laIO8OZvppTP923a8stofPfBXRmqRZdhLLOVMJhBiQtRSGz4w\n" +
            "Tk0T1LC9FN9Y5y4HLbyYDuYxqda+MqdBoYgsvep4ozVNyE7UDdRTKOjin8xIArAn\n" +
            "mPvhjVBtpvQE+r9A4CfLe2smyHUtW48nAxgugRcCgYAjgfgGLTRDBT8edoZ/s6Nk\n" +
            "0uYLQXSSZ3uxBG+LmykAO25vHK7/DDHnZjTXRr/2cQEedRbTXdj2JQnEhrOwhSZO\n" +
            "QfybyGJPZVUmNH5kyHjG4RYf+QG6NcHQau1EVPvylc8NINOaBYvcWC8VEivGe0Ra\n" +
            "ZVupvR5ZCHYVUeMn8mI7sQKBgQDG5cgq8Z3tSVbdWBAyOl9k07+NBniwt5XLhQZr\n" +
            "L8trDqzTcRbfsVzzyw66nPnO4vRwxXTcwyoY1DvvWPIKKynMYBQ4cKgSyxOCY60J\n" +
            "VakEOr79ePy8Jrn0xt6Yu8Yq8JTzvqKHEGZ8ptFVz/6GSqeaQ02ZWtZauDOHSQt6\n" +
            "wnGnnwKBgFbTBEwXl89uZZ/25z1mA5D9nqHTj/A0GYc9762xc/bvkvi59DeYbNSH\n" +
            "J7jKHS50kE4sS/E4p7e9/G4jZTe/nvEsstfFZprRF31xlVY9Y/1OPysGYIJPOTAg\n" +
            "EBEKSypPpswFcn/jSeIGii7aEb5h6OyNpnSMbBvFxhhUJ1PPpUQG\n" +
            "-----END RSA PRIVATE KEY-----\n"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDrDCCApQCCQCDHU0/ZL/yojANBgkqhkiG9w0BAQsFADCBlzELMAkGA1UEBhMC\n" +
            "Y2wxEzARBgNVBAgMClNvbWUtU3RhdGUxETAPBgNVBAcMCHNhbnRpYWdvMSEwHwYD\n" +
            "VQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQxFTATBgNVBAMMDDU5NzA0NDQ0\n" +
            "NDQwMTEmMCQGCSqGSIb3DQEJARYXYW1hbGRvbmFkb0B0cmFuc2JhbmsuY2wwHhcN\n" +
            "MTgwOTA0MTQwMzE4WhcNMjIwMzI3MTQwMzE4WjCBlzELMAkGA1UEBhMCY2wxEzAR\n" +
            "BgNVBAgMClNvbWUtU3RhdGUxETAPBgNVBAcMCHNhbnRpYWdvMSEwHwYDVQQKDBhJ\n" +
            "bnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQxFTATBgNVBAMMDDU5NzA0NDQ0NDQwMTEm\n" +
            "MCQGCSqGSIb3DQEJARYXYW1hbGRvbmFkb0B0cmFuc2JhbmsuY2wwggEiMA0GCSqG\n" +
            "SIb3DQEBAQUAA4IBDwAwggEKAoIBAQCrnHw/0QirzFPpS1EAvqmY2lyBi4hcdS+0\n" +
            "+5j4Cjbwu1uwlBWA9cnYKsxi2rcYfWQ8BqN0XS118w5JCDFOedawwV1jqN6Xa+EX\n" +
            "W98q+bv3IsyuohFfuX99luV1Yl2hK6rVvfFM9gEooYWF79K9ik7zx2864fhYiIB9\n" +
            "FPWAVeQRb3HIDvS8r/hFlH3d/bbQQuHywcEzA/f1ec2ntznYD5/V+QlU/kXwblRM\n" +
            "AL+D+MNkwKu2cLDG7fuPJdMSlABNdQ3VeuyMmKum80F8i9dCWTgB5cSPlrs9gTic\n" +
            "BJl83BJzWSkObsbo+F4ORRc3UkQWxwL/H0GlW9kgCNpcwv9AIVXvAgMBAAEwDQYJ\n" +
            "KoZIhvcNAQELBQADggEBACv0krFTiPCwsw0pwKfHJUqhP+k2B7FkdSFhpdd8OiRX\n" +
            "50E9aY9oiasuojyYA0mdrWDZvyKsxvMGuSzxrxgg42Wsb2DPR5Uc99V2+9rpODFV\n" +
            "nPWeuhAgBUfNK3rZ+qIz1FyrzYUTPcK0BzStbpdclb+LEh7I0wTegSj7skctm8M2\n" +
            "BQmFaS67DUmr0ReI4ZHvWMkDjqjlK8mzx0f7nOdarq3Cxhg3QMqOilfGtvrZrtos\n" +
            "q8/WPGded+bP8kBZ2Rs6oUEBBQfVnAPI50YRXZJjyAzqSwx8MhFztAgE/LaYbvZs\n" +
            "xNB2I18V5oNmOCXHhfqneSstxMBWt3W8rd/0+JSfWLc=\n" +
            "-----END CERTIFICATE-----\n"
        );
        return configuration;
    }

    /**
     * @return a configuration for commerce code 597044444405 for the TEST
     * environment to be used with {@link Webpay#getOneClickTransaction()}
     */
    public static Configuration forTestingWebpayOneClickNormal() {
        Configuration configuration = new Configuration();
        configuration.setCommerceCode("597044444405");
        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEA48S877GUtjlYrqIQDlZAMqg5kfNrrZlCZckC1LGzCfcPu7Lm\n" +
            "za2c2F1Q7zKELqVTo1wuZUS1inhYmtg4PexDKak/uryARR3uy2kqq3yG3IQNIdQY\n" +
            "RpAUl8SvNyJvUVtA0N6nwjaqsTv1EacOLsfR+9HNvRXaWSitXNFa7i2G0iN/pnXb\n" +
            "a796mUMVMwJPkV6Si6608566mvqkSnvzulAaC/eev1KIyH5oF22yjyWRIM+K9ZNx\n" +
            "7Cosnxgmt6e/8AazGBxA7gXxV6DQtOvL6k4AottUro2hMXLUZRS3x4GjtJDUjpLj\n" +
            "UIHpK5Tz66D8dGN+Kv/f/Y7bAWMHUQqrPHkAcQIDAQABAoIBAEEcqeJfwqKBLE/n\n" +
            "9m0SzRFuM978VmjvKiMM3qlND2Cm5zGCSCa+HdtgedWXfGAVVR6bKIw8oyUtmC7T\n" +
            "5ugU1XJgdt58KrMXhn9R1ifl1mvNuC9WgYYShECKsogxaN86FgJf1FdZ51v5Ruq/\n" +
            "9fb7mpTBem42hA/+5+hj1PnHvAXwI9/erX8nZIixtj9PVJOzUW/izlJVm9DoT2vM\n" +
            "JZAt/HXZqovvsSet5tRfTPTuFH+NTXnfpHbDGPDqQrC+fqTaZCFJXU0lgWemBdM2\n" +
            "HIafstxFhY5cbgYKl6nW8SQNQQUG7ifJ5ttz7jNqZ3X9hk2yIvK+0Dd1NKTlJoAS\n" +
            "5V8FL00CgYEA96jBRG4I2P0rbQl7Elf5A4xS9ruFgQtS6//9g5ezLmxtiVQhryl4\n" +
            "4PKKWY1nRPhQBQaQdp6H9w8rU5ccyAFjW4hDfD8jwTCE1On1dSfBWcrBir4LL/Dm\n" +
            "WbOgrh2jotvAmInkl6GohwRZIF77kRQXc+Zv2Xle2o+JMv4keBmuk+sCgYEA63B9\n" +
            "yoV0qhuTnrFNW99rQTQ8WnfUYdmau/4W2CSkNVgZ04j5xKe2FzBkQHnvCDX5THkW\n" +
            "YVsq0J/F3BBApKvKwACVdri19odJRi2JgbJarMZb+xro8kqAZ3KvXkXTYpxkBk7S\n" +
            "hkHBrj6sXe+I3usNN3iH80z6ZTsg2dBU7cp4khMCgYEA0amCBKaKwviBnQubr0ne\n" +
            "vAw96DeUrEyj5LuRKahxvul4Sfx5j8qyVO3ABlRZiuAVDkwiXOmU/alP6rJRYVkb\n" +
            "fk/7oeHqKREkQwPWE1qWq6ek9goxemKdAG+7pevdrnAPupNyfJQVEkiyE1+V5Zp2\n" +
            "yF3bbub7UFiXtB3HTg2f6AUCgYEAt9DQ9TAywKjdHRXyfts1IuhEgqFx2J+LNmEP\n" +
            "ttsMtXU+XLBhyQ7jlME8VDaez/tL00/qkEIccFt9n+20epyBpFwQVWuq7Xn8VGUz\n" +
            "KWZ1ctU/dRIycxCFpb6dem3rtt7BHUenCBkIvSDDdGeSpfBAFmHtSqB8ElMSt9v2\n" +
            "otzkO68CgYBhIDz4rTMuOxmt9yily7rcd9J7RrbC7hPHMjM5EwPyOmawAYjTRM8X\n" +
            "rxe1w4MtKrXxn11EncMJuH0hLQ12MwmrTui7gllTi8IPkfe+e7yhgHzzB5Mnhax0\n" +
            "92jL5rYiTenvy/wIjmF3TDHwyX6dB6QuozltdYBE2rv/oXOrE19qCQ==\n" +
            "-----END RSA PRIVATE KEY-----\n"
        );
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDrDCCApQCCQCLlKISnBv1OTANBgkqhkiG9w0BAQsFADCBlzELMAkGA1UEBhMC\n" +
            "Y2wxEzARBgNVBAgMClNvbWUtU3RhdGUxETAPBgNVBAcMCHNhbnRpYWdvMSEwHwYD\n" +
            "VQQKDBhJbnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQxFTATBgNVBAMMDDU5NzA0NDQ0\n" +
            "NDQwNTEmMCQGCSqGSIb3DQEJARYXYW1hbGRvbmFkb0B0cmFuc2JhbmsuY2wwHhcN\n" +
            "MTgwOTA0MTQxNDE0WhcNMjIwMzI3MTQxNDE0WjCBlzELMAkGA1UEBhMCY2wxEzAR\n" +
            "BgNVBAgMClNvbWUtU3RhdGUxETAPBgNVBAcMCHNhbnRpYWdvMSEwHwYDVQQKDBhJ\n" +
            "bnRlcm5ldCBXaWRnaXRzIFB0eSBMdGQxFTATBgNVBAMMDDU5NzA0NDQ0NDQwNTEm\n" +
            "MCQGCSqGSIb3DQEJARYXYW1hbGRvbmFkb0B0cmFuc2JhbmsuY2wwggEiMA0GCSqG\n" +
            "SIb3DQEBAQUAA4IBDwAwggEKAoIBAQDjxLzvsZS2OViuohAOVkAyqDmR82utmUJl\n" +
            "yQLUsbMJ9w+7subNrZzYXVDvMoQupVOjXC5lRLWKeFia2Dg97EMpqT+6vIBFHe7L\n" +
            "aSqrfIbchA0h1BhGkBSXxK83Im9RW0DQ3qfCNqqxO/URpw4ux9H70c29FdpZKK1c\n" +
            "0VruLYbSI3+mddtrv3qZQxUzAk+RXpKLrrTznrqa+qRKe/O6UBoL956/UojIfmgX\n" +
            "bbKPJZEgz4r1k3HsKiyfGCa3p7/wBrMYHEDuBfFXoNC068vqTgCi21SujaExctRl\n" +
            "FLfHgaO0kNSOkuNQgekrlPProPx0Y34q/9/9jtsBYwdRCqs8eQBxAgMBAAEwDQYJ\n" +
            "KoZIhvcNAQELBQADggEBAGQWdgUHhWXnGIiqE/0b1N2YPr2l1CTu86edj/3ySp1A\n" +
            "7yIeWOiI1NUhMVXTt/uGw4C1WC4Ir3nhB1E0yrc65VF0GboE8m9ilj98NbkQKz0/\n" +
            "dr3/TFb9Fc37WIMFoKsG2tnEXhLZxaahglEDdBcoL+I78K+JFtL92N7+Sns5zAmy\n" +
            "DDKf/7bKvTBF5vXQzgYWkrGFweppVU1xfgCn5KFdqQSJZzvcu1xuCRSngLbcpBEW\n" +
            "JlzTNzZ7K+siy5V9cKQtUW3h/KyMeP9KCE0YUnXvtiGr2yeqUviUAqDiSzcCmQDR\n" +
            "XE4/CA2Yzlv/+n9JVsvFBTAyIvYfG3mqr8KdkL238sc=\n" +
            "-----END CERTIFICATE-----\n"
        );
        return configuration;
    }

    public static Configuration forTestingPatPassByWebpayNormal(String commerceMail) {
        Configuration configuration = new Configuration();

        configuration.setCommerceCode("597044444432");
        configuration.setPublicCert(
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDCjCCAfICCQC54X7OTc5fYTANBgkqhkiG9w0BAQsFADBHMQswCQYDVQQGEwJD\n" +
            "TDETMBEGA1UECAwKU29tZS1TdGF0ZTEMMAoGA1UECgwDVEJLMRUwEwYDVQQDDAw1\n" +
            "OTcwNDQ0NDQ0MzIwHhcNMTgxMjIwMjA0ODQ0WhcNMjIwNzEyMjA0ODQ0WjBHMQsw\n" +
            "CQYDVQQGEwJDTDETMBEGA1UECAwKU29tZS1TdGF0ZTEMMAoGA1UECgwDVEJLMRUw\n" +
            "EwYDVQQDDAw1OTcwNDQ0NDQ0MzIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\n" +
            "AoIBAQDGERkKWGu9vrp6CTGk+qVT/G5GY66dt0v2yB5x9304FICU3sGxJOwtFGvR\n" +
            "G2giZlEwfCpqKOZVFGDPapMMGaH4ciPJuZwELwc39B/aGyUqR4Nii3Ojod2hS3Zk\n" +
            "oR4wril2Vk/eJsiYBAbzflak9MV2TgzjCWQPGToMMCjOSxyK+2NXJ5qivJgMZwbN\n" +
            "XgOFDZbHoMZci4SsUmO0+/vArdXWWF4oHUvR+1jei+V67vN4vr/Tq0+shWtqfHIL\n" +
            "FPzBV5Qz1LbnstAJx6FmJ0bruGwevROjb/vhSeQ0mjg8rYQfFI6ZZaqsYohUDCKn\n" +
            "sC3Hl0T81jTbQKsHY81O93OUFSRrAgMBAAEwDQYJKoZIhvcNAQELBQADggEBADV7\n" +
            "N7GxdLEkny79bhrV5ERRMkGp1utuML8Atq6rifEpxf1HOfVXXO37yStw1In97ZBB\n" +
            "6mx0SoHwzdp2S5FSXaHqewdurWZCLNCQ7a9wKVt97/CxYkyN+O7iCbI7zjLHZ7oF\n" +
            "4pOZavkUH1nogDWT22Jvpo+VgnbafspvVE9dS7m1fnkh7VaB0SL5tbOEB8e/rA2E\n" +
            "MbrDG+OJsxaJT1tbzB9cmJJiHh8Vhz9DJS4OJvCYLdQDqyTq3QP8pLXRVvx/xMsH\n" +
            "Nm1X1RiPWTtDEGZl6CTjR2vhcYNM/XvhQedutKhKqJ8EbjLNL+Pxf43KnqvbMGpE\n" +
            "2QYU66sIU1r0quY4fh0=\n" +
            "-----END CERTIFICATE-----"
        );

        configuration.setPrivateKey(
            "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAxhEZClhrvb66egkxpPqlU/xuRmOunbdL9sgecfd9OBSAlN7B\n" +
            "sSTsLRRr0RtoImZRMHwqaijmVRRgz2qTDBmh+HIjybmcBC8HN/Qf2hslKkeDYotz\n" +
            "o6HdoUt2ZKEeMK4pdlZP3ibImAQG835WpPTFdk4M4wlkDxk6DDAozkscivtjVyea\n" +
            "oryYDGcGzV4DhQ2Wx6DGXIuErFJjtPv7wK3V1lheKB1L0ftY3ovleu7zeL6/06tP\n" +
            "rIVranxyCxT8wVeUM9S257LQCcehZidG67hsHr0To2/74UnkNJo4PK2EHxSOmWWq\n" +
            "rGKIVAwip7Atx5dE/NY020CrB2PNTvdzlBUkawIDAQABAoIBAFNTUBiFGFaMDcFX\n" +
            "atZASBtsICxmtUhOm5unlv9VIg3cujdm+099/An/BFjFUere5psiMWnV6Qiz+QJp\n" +
            "kJFPm099HUc5WcR80Gmev2OvddQyn7hueGjHqQi5SYaOLbhHHNRW/WS1JkCluRLo\n" +
            "/KSLASODNHKzObAMadpEzHX1LX3eBLJ3xPNGLkdiUd6NIjWWdpTWexxJp70kbtPA\n" +
            "cB0xT+Sf+9l7prCt8HD/bB+Mn8dxXcLMwS5nVr6+60ScYWp1/RNZZSt6Y17jryaq\n" +
            "yB4z661qqIC7DGCT0Vwqty7Bmp2ip9L+X8duJmLvljNZJZep98l7OVsFZ4EeewL6\n" +
            "+ef0GBECgYEA7XYLRo190fXJhn+63iWZ6+hQ67y7s7QaTGvROGa+BntDX1rxUm35\n" +
            "S19HXWpT5lT96rV/Z6pFGTn5Vu5g0Rr/lppKcvfJjPJJx42qheCjiqZYA2ThPyK2\n" +
            "dFEemVjm0Da7zSiQ5nkmQUffz4+4avtZwnnHL1NkiaCgyVgUHGmQ0WMCgYEA1Ye1\n" +
            "jo9tRx5OmmoKWjBvXex6RzdDdlIk1Xr/cKI1SiuuwKIcrhN0Kmu+C3MWAteDBQJK\n" +
            "9LWZB5nyoRwrI/aSmkOx3vGwUV6kmO4c7KMWf1nvvMk+iAurH71Ww8jQbhvxC75X\n" +
            "aP6GRbgPO6iZUDF/ol1xzrfnP8loMk+6aammE1kCgYEAmpehG5t98DTdsCxz0bAV\n" +
            "Fxo1pGNIp+N2w43ltZxd1gZlhCzGsu1ZwRIUeeKBIJDwhd8HRMv7FEND6q5iZX/4\n" +
            "KWnlUhezJYmPMtUt0N7AWcGVxw/j/KByvCMbkqXoDmGzvgrM2t7AYvuR36UnYK6m\n" +
            "bw+s90iJSXhDdRFcdmrblxUCgYBP/ORQIP5Ik9vSEEMMgfRV9M3he5wz6JWPuuGR\n" +
            "adIYlMCDvLaNpw/vDClG1Le8HMshxzgY7yYCKPMw0wJ+GFHWo+EvqM3pzVBPUXNY\n" +
            "WceFsUUTLTfVcrmJzD2CXelmNeVYBtdrp1zZJRffu0qjNcegJO0LCO0BggscPa2R\n" +
            "A1IJEQKBgQCKmEeDkz5zT1QusA3QXReB3df8Nnm9AHQZd5Ro5VMyryP5RsKnLh8v\n" +
            "6sKyYZipL1XouwcomzX9WKH0/Pc4e2Sq4IZUg083E6xs1STDiwgTPcnQiIOPpiO4\n" +
            "T6sTj9JMqm5tECWqkDz1gJTTZqfXl9uq9PAAfK+jipv9KnVmO3YQcA==\n" +
            "-----END RSA PRIVATE KEY-----"
        );
        configuration.setCommerceMail(commerceMail);
        return configuration;
    }
}
