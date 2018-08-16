/**
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2016 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2015
  * @license    GNU LGPL
  * @version    2.0.1
  *
  */

package cl.transbank.webpay.security;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.apache.cxf.helpers.IOUtils;


/**
 *
 * @author rbertuzzi
 */
public class Utils {
    public static Key loadKey(String pemContent) throws Exception {
        PrivateKeyReader reader = new PrivateKeyReader(pemContent);
        return reader.getPrivateKey();
    }
    
    public static Certificate loadCertificate(String pemContent) throws Exception{
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        InputStream is = new ByteArrayInputStream(pemContent.getBytes("UTF-8"));
        X509Certificate cert = (X509Certificate) fact.generateCertificate(is);        
        return cert;
    }
    
    public static String getFileContents(String filename) throws IOException {
        return IOUtils.toString(new FileReader(filename));
    }
}
