/**
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2016 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2015
  * @license    GNU LGPL
  * @version    2.0.1
  *
  */

package cl.transbank.webpay.security;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.common.crypto.Merlin;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

/**
 *
 * @author rbertuzzi
 */
public class SoapSignature {
    
    private String privateKey;
    private String privateKeyFile;
    
    private String publicCert;
    private String publicCertFile;
    
    private String webpayCert;
    private String webpayCertFile;
    
    private KeyStore privateKeyStore;    
    private String privateKeyStoreFile;
    private String privateKeyStorePassword = "default-password";
    private String privateKeyPassword = "default-password";
    private String privateKeyAlias = "transbank";
    
    private KeyStore webpayKeyStore;        
    private String webpayKeyStoreFile;
    private String webpayKeyStorePassword = "default-password";
    
    public void applySignature(Object port) throws Exception {
        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(port);
        org.apache.cxf.endpoint.Endpoint cxfEndpoint = client.getEndpoint();
                
        /** Firmar mensaje de salida hacia webpay */
        Map<String,Object> outProperties = getProperties(false);
        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(outProperties);        
        cxfEndpoint.getOutInterceptors().add(wssOut);   
        
        /** Validar firma de mensaje de respuesta de webpay */
        Map<String,Object> inProperties = getProperties(true);
        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(inProperties);        
        cxfEndpoint.getInInterceptors().add(wssIn);
    }
    
    protected boolean hasWebpayCert(){
        return webpayCert != null || webpayCertFile != null;
    }
    
    private Map<String,Object> getProperties(boolean input) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put(WSHandlerConstants.ACTION, "Signature");       
        map.put(WSHandlerConstants.SIG_PROP_REF_ID, "wsCryptoProperties");
        if (!input){
            map.put(WSHandlerConstants.USER, this.privateKeyAlias);
            map.put(WSHandlerConstants.SIGNATURE_PARTS, "{Element}{http://schemas.xmlsoap.org/soap/envelope/}Body");        
            map.put(WSHandlerConstants.PW_CALLBACK_REF, new PasswordCallbackHandler(this.privateKeyPassword));            
        }
        
        /** Si pasaron keystorefile usar el archivo, sino, usar Merlin y crear un keystore */
        String file = input? webpayKeyStoreFile: privateKeyStoreFile;
        if (file != null){
            map.put("wsCryptoProperties", getWSCryptoProperties(input));
        }
        else{            
            Merlin merlin = new Merlin( getWSCryptoProperties(input), Merlin.class.getClassLoader(), null);
            KeyStore keyStore = input? getWebpayKeyStore(): getPrivateKeyStore();
            merlin.setKeyStore(keyStore);
            map.put("wsCryptoProperties", merlin);            
        }
        
        return map;
    }
    
    private Properties getWSCryptoProperties(boolean input) {        
        Properties p = new Properties();
        p.setProperty("org.apache.ws.security.crypto.provider", "org.apache.ws.security.components.crypto.Merlin");
        p.setProperty("org.apache.ws.security.crypto.merlin.keystore.type", "jks");        
        p.setProperty("org.apache.ws.security.crypto.merlin.keystore.password", input? this.webpayKeyStorePassword: this.privateKeyStorePassword);
        String file = input? this.webpayKeyStoreFile: this.privateKeyStoreFile;
        if (file != null){
            p.setProperty("org.apache.ws.security.crypto.merlin.keystore.file", file);
        }
        p.setProperty("org.apache.ws.security.crypto.merlin.keystore.alias", this.privateKeyAlias);
        return p;
    }
    
    private synchronized KeyStore getPrivateKeyStore() throws Exception {
        if (this.privateKeyStore != null){
            return this.privateKeyStore;
        }
        
        String pemKeyContent = this.privateKey;
        if (pemKeyContent == null){
            pemKeyContent = Utils.getFileContents(this.privateKeyFile);
        }
        String pemCertContent = this.publicCert;
        if (pemCertContent == null){
            pemCertContent = Utils.getFileContents(this.publicCertFile);
        }
        
        this.privateKeyStore = createKeyStore(pemKeyContent, pemCertContent);
        return this.privateKeyStore;
    }
    
    private synchronized KeyStore getWebpayKeyStore() throws Exception {
        if (this.webpayKeyStore != null){
            return this.webpayKeyStore;
        }
        
        String pemContent = this.webpayCert;
        if (pemContent == null){
            pemContent = Utils.getFileContents(this.webpayCertFile);
        }
        this.webpayKeyStore = createKeyStore(null, pemContent);
        return this.webpayKeyStore;
    }
    
    private KeyStore createKeyStore(String privateKey, String publicCert) throws Exception {        
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null);
        Certificate cert = Utils.loadCertificate(publicCert);
        if (privateKey != null){
            Key key = Utils.loadKey(privateKey);            
            keyStore.setKeyEntry("transbank", key, this.privateKeyPassword.toCharArray(), new java.security.cert.Certificate[]{cert});
        }
        else{            
            keyStore.setCertificateEntry("transbank", cert);
        }
        return keyStore;
    }
    
    
    public void setPrivateCertificateFile(String privateKeyFile, String publicCertFile){
        this.privateKeyFile = privateKeyFile;
        this.publicCertFile = publicCertFile;
    }
        
    public void setPrivateCertificate(String privateKeyString, String publicCertString){
        this.privateKey = privateKeyString;
        this.publicCert = publicCertString;
    }
    
    public void setPrivateCertificateKeyStoreFile(String keyStoreFile, String keyStorePassword, String privateKeyPassword, String alias){
        this.privateKeyStoreFile = keyStoreFile;
        this.privateKeyStorePassword = keyStorePassword;
        this.privateKeyPassword = privateKeyPassword;
        this.privateKeyAlias = alias;
    }

    public void setWebpayCertificateFile(String webpayCert){
        this.webpayCertFile = webpayCert;
    }
    
    public void setWebpayCertificate(String webpayCertString){
        this.webpayCert = webpayCertString;
    }
    
    public void setWebpayCertificateKeyStore(String keyStoreFile, String keyStorePassword){
        this.webpayKeyStoreFile = keyStoreFile;
        this.webpayKeyStorePassword = keyStorePassword;
    }
    
    public static class PasswordCallbackHandler implements CallbackHandler {    
        private final String password;
        
        public PasswordCallbackHandler(String password){
            this.password = password;
        }
        
        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {            
            WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];            
            pc.setPassword(password);
        }     
     }
}
