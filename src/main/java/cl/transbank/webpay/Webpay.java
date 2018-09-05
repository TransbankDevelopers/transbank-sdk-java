package cl.transbank.webpay;

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
        WEBPAY_CERTS.put(INTERNAL_NAME_INTEGRACION,
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDKTCCAhECBFZl7uIwDQYJKoZIhvcNAQEFBQAwWTELMAkGA1UEBhMCQ0wxDjAMBgNVBAgMBUNo\n" +
            "aWxlMREwDwYDVQQHDAhTYW50aWFnbzEMMAoGA1UECgwDa2R1MQwwCgYDVQQLDANrZHUxCzAJBgNV\n" +
            "BAMMAjEwMB4XDTE1MTIwNzIwNDEwNloXDTE4MDkwMjIwNDEwNlowWTELMAkGA1UEBhMCQ0wxDjAM\n" +
            "BgNVBAgMBUNoaWxlMREwDwYDVQQHDAhTYW50aWFnbzEMMAoGA1UECgwDa2R1MQwwCgYDVQQLDANr\n" +
            "ZHUxCzAJBgNVBAMMAjEwMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAizJUWTDC7nfP\n" +
            "3jmZpWXFdG9oKyBrU0Bdl6fKif9a1GrwevThsU5Dq3wiRfYvomStNjFDYFXOs9pRIxqX2AWDybjA\n" +
            "X/+bdDTVbM+xXllA9stJY8s7hxAvwwO7IEuOmYDpmLKP7J+4KkNH7yxsKZyLL9trG3iSjV6Y6SO5\n" +
            "EEhUsdxoJFAow/h7qizJW0kOaWRcljf7kpqJAL3AadIuqV+hlf+Ts/64aMsfSJJA6xdbdp9ddgVF\n" +
            "oqUl1M8vpmd4glxlSrYmEkbYwdI9uF2d6bAeaneBPJFZr6KQqlbbrVyeJZqmMlEPy0qPco1TIxrd\n" +
            "EHlXgIFJLyyMRAyjX9i4l70xjwIDAQABMA0GCSqGSIb3DQEBBQUAA4IBAQBn3tUPS6e2USgMrPKp\n" +
            "sxU4OTfW64+mfD6QrVeBOh81f6aGHa67sMJn8FE/cG6jrUmX/FP1/Cpbpvkm5UUlFKpgaFfHv+Kg\n" +
            "CpEvgcRIv/OeIi6Jbuu3NrPdGPwzYkzlOQnmgio5RGb6GSs+OQ0mUWZ9J1+YtdZc+xTga0x7nsCT\n" +
            "5xNcUXsZKhyjoKhXtxJm3eyB3ysLNyuL/RHy/EyNEWiUhvt1SIePnW+Y4/cjQWYwNqSqMzTSW9TP\n" +
            "2QR2bX/W2H6ktRcLsgBK9mq7lE36p3q6c9DtZJE+xfA4NGCYWM9hd8pbusnoNO7AFxJZOuuvLZI7\n" +
            "JvD7YLhPvCYKry7N6x3l\n" +
            "-----END CERTIFICATE-----"
        );
        WEBPAY_CERTS.put(INTERNAL_NAME_PRODUCCION,
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDNDCCAhwCCQCJEQxY1moacjANBgkqhkiG9w0BAQsFADBcMQswCQYDVQQGEwJD\n" +
            "TDELMAkGA1UECBMCUk0xETAPBgNVBAcTCFNhbnRpYWdvMRIwEAYDVQQKEwl0cmFu\n" +
            "c2JhbmsxDDAKBgNVBAsTA1BSRDELMAkGA1UEAxMCMTAwHhcNMTQwNTA4MjEwNjIy\n" +
            "WhcNMTgwNTA4MjEwNjIyWjBcMQswCQYDVQQGEwJDTDELMAkGA1UECBMCUk0xETAP\n" +
            "BgNVBAcTCFNhbnRpYWdvMRIwEAYDVQQKEwl0cmFuc2JhbmsxDDAKBgNVBAsTA1BS\n" +
            "RDELMAkGA1UEAxMCMTAwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCk\n" +
            "ag5P6b/BnlpxGk1YX8OeX04ZqmxWThxHP1J+6FVj/hMYw9JGf2gMDCWd3fYaWwRM\n" +
            "X7Y6MidAGCiVwNgsixsUad9C2qQWtpTHoc3T+rQuZ6wmGwxc/K/Gcjf4nuJQUPBo\n" +
            "3zjat+HC0HzPrTscms4A2EZ2VQ/bbznKiOWxcBSqqZ/8jK/RMmu4E6Pzj8Ms+vbA\n" +
            "BfDCq9GDfeNZ+gtQna86enEX7XY/N55SO+VHv/6zGIof7kGIobeF1hYwALrKDhvy\n" +
            "FVQgh4VUBhP0adtnQBfCc1mGVgnviAjioxMxGT4wwaj6IfTvtHhkxVcJ9qmX9oki\n" +
            "wygTooWtcMM6U4oiVd+vAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAEqW5DtWdAUP\n" +
            "iSBpExhPgSnm+X6eiDmM3q0S8gWls3hnZCQ9RfhVROj93OS4Zaqg82RLGiU3GsWF\n" +
            "pj4YRw0flCC7bCxo7Mt4Lvv6ihQYdsWxA97HN55HQOVv853kQAu6/vnCxoTtMt6W\n" +
            "+zuiQY7hhabLhOCNJcrFpabj0wCO62IrWv65AZlikcsNKLAwQrstY7Y1VU5DOcXy\n" +
            "FfE5niUGxH0mARXMxq1Z3CBqJ3GKKMmngqCMxX8ZFjIvz0z0VsOJQheX4Hl8prAR\n" +
            "ZlVlkH02xlKKLIO2tcnXik1eW5VCpzuF6z9W3WqcvpaltfspJPx3kN3k5NHATNgk\n" +
            "IypDl0jmq2w=\n" +
            "-----END CERTIFICATE-----"
        );
    }
    
    SoapSignature signature;
    Environment mode;
    String commerceCode;
    
    WebpayNormal normalTransaction;
    WebpayOneClick oneClickTransaction;
    WebpayMallNormal mallNormalTransaction;
    WebpayComplete completeTransaction;
    WebpayCapture captureTransaction;
    WebpayNullify nullifyTransaction;

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
        this.mode = conf.getEnvironment();
        this.commerceCode = conf.getCommerceCode();
        
        SoapSignature sig = new SoapSignature();
        sig .setPrivateCertificate(conf.getPrivateKey(), conf.getPublicCert());
        if (conf.getWebpayCert() != null) {
            // For backwards compatibility with the old libwebpay:
            sig.setWebpayCertificate(conf.getWebpayCert());
        } else {
            sig.setWebpayCertificate(getWebPayCertificate(mode));
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
            normalTransaction = new WebpayNormal(mode, commerceCode, signature);
        }
        return normalTransaction;
    }
    
    public synchronized WebpayOneClick getOneClickTransaction() throws Exception {
        if (oneClickTransaction == null){
            oneClickTransaction = new WebpayOneClick(mode, commerceCode, signature);
        }
        return oneClickTransaction;
    }
    
    public synchronized WebpayMallNormal getMallNormalTransaction() throws Exception {
        if (mallNormalTransaction == null){
            mallNormalTransaction = new WebpayMallNormal(mode, commerceCode, signature);
        }
        return mallNormalTransaction;
    }
    
    public synchronized WebpayComplete getCompleteTransaction() throws Exception {
        if (completeTransaction == null){
            completeTransaction = new WebpayComplete(mode, commerceCode, signature);
        }
        return completeTransaction;
    }
    
    public synchronized WebpayCapture getCaptureTransaction() throws Exception {
        if (captureTransaction == null){
            captureTransaction = new WebpayCapture(mode, commerceCode, signature);
        }
        return captureTransaction;
    }
    
    public synchronized WebpayNullify getNullifyTransaction() throws Exception {
        if (nullifyTransaction == null){
            nullifyTransaction = new WebpayNullify(mode, commerceCode, signature);
        }
        return nullifyTransaction;
    }
    
}
