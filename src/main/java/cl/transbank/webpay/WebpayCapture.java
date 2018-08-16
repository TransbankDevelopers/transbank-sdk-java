/**
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2016 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2016
  * @license    GNU LGPL
  * @version    2.0.1
  *
  */

package cl.transbank.webpay;

import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.CaptureInput;
import com.transbank.webpay.wswebpay.service.CaptureOutput;
import com.transbank.webpay.wswebpay.service.WSCommerceIntegrationService;
import com.transbank.webpay.wswebpay.service.WSCommerceIntegrationServiceImplService;
import java.math.BigDecimal;
import java.net.URL;

/**
 *
 * @author jguerrero
 */
public class WebpayCapture {
    
    WSCommerceIntegrationService port;
    String commerceCode;
    
    public WebpayCapture(Webpay.Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        this.commerceCode = commerceCode;
        
        URL wsdl = this.getClass().getResource("/wsdl/" + mode.name().toLowerCase() + "/nullify.wsdl");

        WSCommerceIntegrationServiceImplService ss = new WSCommerceIntegrationServiceImplService(wsdl);        
        this.port = ss.getWSCommerceIntegrationServiceImplPort();
        if (signature != null){
            signature.applySignature(port);
        }       
    }    
    
    public CaptureOutput capture(String authorizationCode, BigDecimal captureAmount, String buyOrder){
        
        CaptureInput input = new CaptureInput();
        input.setAuthorizationCode(authorizationCode);
        input.setBuyOrder(buyOrder);
        input.setCaptureAmount(captureAmount);    
        input.setCommerceId(new Long(commerceCode));
        
        return capture(input);        
    }      
    
    public CaptureOutput capture(CaptureInput input){        
        return port.capture(input);        
    }       

}
