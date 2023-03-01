package cl.transbank.webpay;

import cl.transbank.patpass.PatPassByWebpayNormal;
import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.security.SoapSignature;

import java.util.HashMap;
import java.util.Map;

public class Webpay {
    public static final String INTERNAL_NAME_INTEGRACION = "integracion";
    public static final String INTERNAL_NAME_PRODUCCION = "produccion";

    public enum Environment {
        /*
        There are only 2 Webpay environments by 2018:
         - Produccion: The live system
         - Integracion: The test system used when integrating and making sure
                        everything works.

        We have many aliases for those two environments due to historic reasons
        and also because we are trying to get a bit of consistency with the
        terminology used with Onepay.
        */

        INTEGRACION(INTERNAL_NAME_INTEGRACION),
        CERTIFICACION(INTERNAL_NAME_INTEGRACION), // For backwards compat with libwebpay
        TEST(INTERNAL_NAME_INTEGRACION), // Alternative name consistent with onepay

        PRODUCCION(INTERNAL_NAME_PRODUCCION),
        LIVE(INTERNAL_NAME_PRODUCCION); // Alternative name consistent with onepay

        private final String internalName;
        Environment(String internalName) {
            this.internalName = internalName;
        }
        public String getInternalName() {
            return internalName;
        }

    }


    private static final Map<String, String> WEBPAY_CERTS;
    static {
        WEBPAY_CERTS = new HashMap<>();
        WEBPAY_CERTS.put(INTERNAL_NAME_INTEGRACION,"-----BEGIN CERTIFICATE-----\n" +
            "MIIC8jCCAdoCCQDZgAhqEGGRFjANBgkqhkiG9w0BAQsFADA7MQswCQYDVQQGEwJD\n" +
            "TDERMA8GA1UEBwwIU0FOVElBR08xDDAKBgNVBAoMA1RCSzELMAkGA1UEAwwCMjAw\n" +
            "HhcNMjEwODI1MTMyMjE2WhcNMzEwODIzMTMyMjE2WjA7MQswCQYDVQQGEwJDTDER\n" +
            "MA8GA1UEBwwIU0FOVElBR08xDDAKBgNVBAoMA1RCSzELMAkGA1UEAwwCMjAwggEi\n" +
            "MA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDTJsqnb+tzqySZikBaqGs9t2qV\n" +
            "Rg9c8wjPGLPJcHsjk/+K+u8G23CNH+kWyCabI6MXMZ0YdzXP/oiEM1orpCNxuKVN\n" +
            "nDkdiae4nbGNo54PbZyBH7bmKgjrZovQZq8sVdjXsXqAAeEQ08Ne3RET3w7s+TDZ\n" +
            "x1ZBe+OJ/HONTMbNDGDSDdjCS/VmZlP6xvNDhphC7R8vyBcEo9m/Q3ZuW9lRa+rR\n" +
            "5AOvw4RLzwWuJApg03FutQbu2MihnlbxHYuTHsnj0uFT+1Lm2LSqU+WRPnfKH6Gu\n" +
            "6j1sb9CiYCczPkSFXYGyNMvSSy6D+0Yd67hmELJ1iPR8vV9vSUflveiMOsfHAgMB\n" +
            "AAEwDQYJKoZIhvcNAQELBQADggEBAGTWW5W4+PDSncJgmxS6kJ5WY8Dtx2k+Hzm2\n" +
            "J6GsiW8zwuN06Ptw4PbsVlcHcCfBewIMM4YJHuoFh0uMg9C+zPUQQnKHsIUlMCvw\n" +
            "sz49WH3fgPpolfMScEgEuo7I9IHxBxILXUA6RScDNjFZpkwpntgT/M0CX0bZt8lA\n" +
            "L6SbCGqMu4KhaS+I9oVc9TLMaYZdMnpRBMYx7FyxTWvwfp+r1gKm4SRjt3QMO9gI\n" +
            "CmTrfnWrhCeHQen1atuRWm8Q674DzFMdcdEhbexgZsMJXI8TFdpB+FfFT86POJWo\n" +
            "a8KTXjkncYkTaOnpMEz+H+xF0fnJ/y9A/A9FgqVhOJIuzPSzBYI=\n" +
            "-----END CERTIFICATE-----");
        WEBPAY_CERTS.put(INTERNAL_NAME_PRODUCCION,
            "-----BEGIN CERTIFICATE-----\n"+
            "MIIDizCCAnOgAwIBAgIJALasOkDoQ+iVMA0GCSqGSIb3DQEBCwUAMFwxCzAJBgNV\n"+
            "BAYTAkNMMQswCQYDVQQIDAJSTTERMA8GA1UEBwwIU2FudGlhZ28xEjAQBgNVBAoM\n"+
            "CXRyYW5zYmFuazEMMAoGA1UECwwDUFJEMQswCQYDVQQDDAIxMDAeFw0yMzAyMTYx\n"+
            "ODM4MDJaFw0yODAyMTUxODM4MDJaMFwxCzAJBgNVBAYTAkNMMQswCQYDVQQIDAJS\n"+
            "TTERMA8GA1UEBwwIU2FudGlhZ28xEjAQBgNVBAoMCXRyYW5zYmFuazEMMAoGA1UE\n"+
            "CwwDUFJEMQswCQYDVQQDDAIxMDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoC\n"+
            "ggEBAKRqDk/pv8GeWnEaTVhfw55fThmqbFZOHEc/Un7oVWP+ExjD0kZ/aAwMJZ3d\n"+
            "9hpbBExftjoyJ0AYKJXA2CyLGxRp30LapBa2lMehzdP6tC5nrCYbDFz8r8ZyN/ie\n"+
            "4lBQ8GjfONq34cLQfM+tOxyazgDYRnZVD9tvOcqI5bFwFKqpn/yMr9Eya7gTo/OP\n"+
            "wyz69sAF8MKr0YN941n6C1Cdrzp6cRftdj83nlI75Ue//rMYih/uQYiht4XWFjAA\n"+
            "usoOG/IVVCCHhVQGE/Rp22dAF8JzWYZWCe+ICOKjEzEZPjDBqPoh9O+0eGTFVwn2\n"+
            "qZf2iSLDKBOiha1wwzpTiiJV368CAwEAAaNQME4wHQYDVR0OBBYEFDfN1Tlj7wbn\n"+
            "JIemBNO1XrUOikQpMB8GA1UdIwQYMBaAFDfN1Tlj7wbnJIemBNO1XrUOikQpMAwG\n"+
            "A1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggEBAA/TWbWDsIoKd+TnetNrGU9X\n"+
            "JOoC6RwuRGJOjMwsRrUESbxGllGHL9wCssSn8U00txibZ6hsmdUUA80ZdEKRK+WW\n"+
            "3tV3+SY8PINzvlObScUkArfkfBn1s1pbqwcGYqexIkYAcOZ4Vp9CLTsm1O6dxuu5\n"+
            "6UwhsPq8rL/tagXjDv6e+mNoZ8uYjwE8y+3vURbRHjrQRLJQxeL+OXQ8pb3K/o/K\n"+
            "8o3Fq9jvMMWuR9dzgmQpHduvZ4MhpsKCgHaeyth3koW8pL75JtaqNvdDpsNto5cD\n"+
            "k+/NDy2R+C8RRsrK2HsKcfIpP9/ovptF59wkelkOHYquErNCjkCjbmJ0ZC9ZGH4=\n"+
            "-----END CERTIFICATE-----"
        );
    }
    
    SoapSignature signature;
    Configuration configuration;
    
    WebpayNormal normalTransaction;
    WebpayOneClick oneClickTransaction;
    WebpayMallNormal mallNormalTransaction;
    WebpayComplete completeTransaction;
    WebpayCapture captureTransaction;
    WebpayNullify nullifyTransaction;
    PatPassByWebpayNormal patPassByWebpayTransaction;

    @Deprecated
    public Webpay(Environment env, String commerceCode, SoapSignature signature){
        this(env, commerceCode);
        setSignature(signature);
    }

    @Deprecated
    public Webpay(Environment env, String commerceCode){
        this(newConfigurationFromEnvAndCommerceCode(env, commerceCode));
    }

    private static Configuration newConfigurationFromEnvAndCommerceCode(Environment env, String commerceCode) {
        Configuration config = new Configuration();
        config.setEnvironment(env);
        config.setCommerceCode(commerceCode);
        return config;
    }
    
    public Webpay(Configuration conf){
        this.configuration = conf;

        SoapSignature sig = new SoapSignature();
        sig .setPrivateCertificate(conf.getPrivateKey(), conf.getPublicCert());
        if (conf.getWebpayCert() != null) {
            // For backwards compatibility with the old libwebpay and in case
            // someone wants to override the certificate
            // (perhaps they don't want to update the SDK because of a code
            // freeze or something like that)
            sig.setWebpayCertificate(conf.getWebpayCert());
        } else {
            sig.setWebpayCertificate(getWebPayCertificate(conf.getEnvironment()));
        }
        setSignature(sig);
        
    }

    private String getWebPayCertificate(Environment environment) {
        return WEBPAY_CERTS.get(environment.getInternalName());
    }
    
    public void setSignature(SoapSignature signature){
        this.signature = signature;
    }
    
    public synchronized WebpayNormal getNormalTransaction() throws Exception {
        if (normalTransaction == null){
            normalTransaction = new WebpayNormal(
                    configuration.getEnvironment(),
                    configuration.getCommerceCode(), signature);
        }
        return normalTransaction;
    }
    
    public synchronized WebpayOneClick getOneClickTransaction() throws Exception {
        if (oneClickTransaction == null){
            oneClickTransaction = new WebpayOneClick(
                    configuration.getEnvironment(),
                    configuration.getCommerceCode(), signature);
        }
        return oneClickTransaction;
    }
    
    public synchronized WebpayMallNormal getMallNormalTransaction() throws Exception {
        if (mallNormalTransaction == null){
            mallNormalTransaction = new WebpayMallNormal(
                    configuration.getEnvironment(),
                    configuration.getCommerceCode(), signature);
        }
        return mallNormalTransaction;
    }
    
    public synchronized WebpayComplete getCompleteTransaction() throws Exception {
        if (completeTransaction == null){
            completeTransaction = new WebpayComplete(
                    configuration.getEnvironment(),
                    configuration.getCommerceCode(), signature);
        }
        return completeTransaction;
    }
    
    public synchronized WebpayCapture getCaptureTransaction() throws Exception {
        if (captureTransaction == null){
            captureTransaction = new WebpayCapture(
                    configuration.getEnvironment(),
                    configuration.getCommerceCode(), signature);
        }
        return captureTransaction;
    }
    
    public synchronized WebpayNullify getNullifyTransaction() throws Exception {
        if (nullifyTransaction == null){
            nullifyTransaction = new WebpayNullify(
                    configuration.getEnvironment(),
                    configuration.getCommerceCode(), signature);
        }
        return nullifyTransaction;
    }

    public synchronized PatPassByWebpayNormal getPatPassByWebpayTransaction() throws Exception {
        if (patPassByWebpayTransaction == null){
            patPassByWebpayTransaction = new PatPassByWebpayNormal(configuration, signature);
        }
        return patPassByWebpayTransaction;
    }

}
