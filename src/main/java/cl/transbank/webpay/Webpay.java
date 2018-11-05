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
        WEBPAY_CERTS.put(INTERNAL_NAME_INTEGRACION,
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIEDzCCAvegAwIBAgIJAMaH4DFTKdnJMA0GCSqGSIb3DQEBCwUAMIGdMQswCQYD\n" +
            "VQQGEwJDTDERMA8GA1UECAwIU2FudGlhZ28xETAPBgNVBAcMCFNhbnRpYWdvMRcw\n" +
            "FQYDVQQKDA5UUkFOU0JBTksgUy5BLjESMBAGA1UECwwJU2VndXJpZGFkMQswCQYD\n" +
            "VQQDDAIyMDEuMCwGCSqGSIb3DQEJARYfc2VndXJpZGFkb3BlcmF0aXZhQHRyYW5z\n" +
            "YmFuay5jbDAeFw0xODA4MjQxOTU2MDlaFw0yMTA4MjMxOTU2MDlaMIGdMQswCQYD\n" +
            "VQQGEwJDTDERMA8GA1UECAwIU2FudGlhZ28xETAPBgNVBAcMCFNhbnRpYWdvMRcw\n" +
            "FQYDVQQKDA5UUkFOU0JBTksgUy5BLjESMBAGA1UECwwJU2VndXJpZGFkMQswCQYD\n" +
            "VQQDDAIyMDEuMCwGCSqGSIb3DQEJARYfc2VndXJpZGFkb3BlcmF0aXZhQHRyYW5z\n" +
            "YmFuay5jbDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJN+OJgQQqMb\n" +
            "iRZDb3x+JoTfSjyYsRc5k2CWvLpTPFxXuhDyp6mbdIpWIiNYEC4vufVZo5A3THar\n" +
            "cbnJRlW/4NVv5QM3gHN9WJ4QeIsrTLtvcIPlfUJNPLNeDqy84zum2YqAFmX5LWsp\n" +
            "SF1Ls6n7el8KNJAceaU+2ooN8QZdFZ3RnMc2vrHY7EU6wYGmf/VCEaDZCKqY6ElY\n" +
            "mt6/9b2lkhpQLdBn01IqqFpGrD+5DLmYrQur4/1BDVtdNLggX0K7kPk/mkPDq4ME\n" +
            "ytkc9/RI5HfJWoQ4EDQF6qcqPqxlMFDf5KEaoLVL230EdwOl0UyvlF25S9ubRyHy\n" +
            "mKWIEFSSXe0CAwEAAaNQME4wHQYDVR0OBBYEFP3nYSPX3YKF11RArC09hxjEMMBv\n" +
            "MB8GA1UdIwQYMBaAFP3nYSPX3YKF11RArC09hxjEMMBvMAwGA1UdEwQFMAMBAf8w\n" +
            "DQYJKoZIhvcNAQELBQADggEBAFHqOPGeg5IpeKz9LviiBGsJDReGVkQECXHp1QP4\n" +
            "8RpWDdXBKQqKUi7As97wmVksweaasnGlgL4YHShtJVPFbYG9COB+ElAaaiOoELsy\n" +
            "kjF3tyb0EgZ0Z3QIKabwxsxdBXmVyHjd13w6XGheca9QFane4GaqVhPVJJIH/zD2\n" +
            "mSc1boVSpaRc1f0oiMtiZf/rcY1/IyMXA9RVxtOtNs87Wjnwq6AiMjB15fLHfT7d\n" +
            "R48O6P0ZpWLlZwScyqDWcsg/4wNCL5Kaa5VgM03SKM6XoWTzkT7p0t0FPZVoGCyG\n" +
            "MX5lzVXafBH/sPd545fBH2J3xAY3jtP764G4M8JayOFzGB0=\n" +
            "-----END CERTIFICATE-----\n"
        );
        WEBPAY_CERTS.put(INTERNAL_NAME_PRODUCCION,
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIDizCCAnOgAwIBAgIJAIXzFTyfjyBkMA0GCSqGSIb3DQEBCwUAMFwxCzAJBgNV\n" +
            "BAYTAkNMMQswCQYDVQQIDAJSTTERMA8GA1UEBwwIU2FudGlhZ28xEjAQBgNVBAoM\n" +
            "CXRyYW5zYmFuazEMMAoGA1UECwwDUFJEMQswCQYDVQQDDAIxMDAeFw0xODAzMjkx\n" +
            "NjA4MjhaFw0yMzAzMjgxNjA4MjhaMFwxCzAJBgNVBAYTAkNMMQswCQYDVQQIDAJS\n" +
            "TTERMA8GA1UEBwwIU2FudGlhZ28xEjAQBgNVBAoMCXRyYW5zYmFuazEMMAoGA1UE\n" +
            "CwwDUFJEMQswCQYDVQQDDAIxMDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoC\n" +
            "ggEBAKRqDk/pv8GeWnEaTVhfw55fThmqbFZOHEc/Un7oVWP+ExjD0kZ/aAwMJZ3d\n" +
            "9hpbBExftjoyJ0AYKJXA2CyLGxRp30LapBa2lMehzdP6tC5nrCYbDFz8r8ZyN/ie\n" +
            "4lBQ8GjfONq34cLQfM+tOxyazgDYRnZVD9tvOcqI5bFwFKqpn/yMr9Eya7gTo/OP\n" +
            "wyz69sAF8MKr0YN941n6C1Cdrzp6cRftdj83nlI75Ue//rMYih/uQYiht4XWFjAA\n" +
            "usoOG/IVVCCHhVQGE/Rp22dAF8JzWYZWCe+ICOKjEzEZPjDBqPoh9O+0eGTFVwn2\n" +
            "qZf2iSLDKBOiha1wwzpTiiJV368CAwEAAaNQME4wHQYDVR0OBBYEFDfN1Tlj7wbn\n" +
            "JIemBNO1XrUOikQpMB8GA1UdIwQYMBaAFDfN1Tlj7wbnJIemBNO1XrUOikQpMAwG\n" +
            "A1UdEwQFMAMBAf8wDQYJKoZIhvcNAQELBQADggEBACzXPSHet7aZrQvMUN03jOqq\n" +
            "w37brCWZ+L/+pbdOugVRAQRb2W+Z6gyrJ2BuUuiZLCXpjvXACSpwcSB3JesWs9KE\n" +
            "YO8E8ofF7a6ORvi2Mw0vpBbwJLqnci1gVlAj3X8r/VbX2rGbvRy+BJAF769xr43X\n" +
            "dtns0JIWwKud0xC3iRPMnewo/75HIblbN3guePfouoR2VgfBmeU72UR8O+OpjwbF\n" +
            "vpidobGqTGvZtxRV5axer69WY0rAXRhTSfkvyGTXERCJ3vdsF/v9iNKHhERUnpV6\n" +
            "KDrfvgD9uqWH12/89hfsfVN6iRH9UOE+SKoR/jHtvLMhVHpa80HVK1qdlfqUTZo=\n" +
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
