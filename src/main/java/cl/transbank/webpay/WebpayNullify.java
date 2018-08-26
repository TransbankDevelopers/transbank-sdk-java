/**
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2015 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2016
  * @license    GNU LGPL
  * @version    2.0.1
  *
  */

package cl.transbank.webpay;

import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.NullificationInput;
import com.transbank.webpay.wswebpay.service.NullificationOutput;
import com.transbank.webpay.wswebpay.service.NullifyResponse;
import com.transbank.webpay.wswebpay.service.WSCommerceIntegrationService;
import com.transbank.webpay.wswebpay.service.WSCommerceIntegrationServiceImplService;
import java.math.BigDecimal;
import java.net.URL;

/**
 *
 * @author jguerrero
 */
public class WebpayNullify {
    
    WSCommerceIntegrationService port;
    //WSWebpayService portNormal;
    String commerceCode;
    
    public WebpayNullify(Webpay.Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        this.commerceCode = commerceCode;
        
        //Nullify
        URL wsdlNullify = this.getClass().getResource("/wsdl/" + mode.getInternalName() + "/nullify.wsdl");
        WSCommerceIntegrationServiceImplService ssNullify = new WSCommerceIntegrationServiceImplService(wsdlNullify);        
        this.port = ssNullify.getWSCommerceIntegrationServiceImplPort();        
        if (signature != null){
            signature.applySignature(port);
        }       
    }
    
    
    public NullificationOutput nullify (String authorizationCode, BigDecimal authorizedAmount, String buyOrder,BigDecimal nullifyAmount, Long commercecode){
        
            NullificationInput input = new NullificationInput();
            NullifyResponse response =  new NullifyResponse();

            /** Código de autorización de la transacción que se requiere anular. Para el caso que se esté anulando una transacción de captura en línea,
             *  este código corresponde al código de autorización de la captura */
            input.setAuthorizationCode(authorizationCode);

            /** Monto autorizado de la transacción que se requiere anular. 
             * Para el caso que se esté anulando una transacción de captura en línea, 
             * este monto corresponde al monto de la captura */
            input.setAuthorizedAmount(authorizedAmount); // decimal
            input.setBuyOrder(buyOrder); // string

            if (commercecode == null){
                input.setCommerceId(new Long(commerceCode.trim()));
            } else {
                input.setCommerceId(commercecode);
            }            
            input.setNullifyAmount(nullifyAmount);
            
        return this.nullify(input);
    }
           

    public NullificationOutput nullify(NullificationInput input){
        return port.nullify(input);
    }    
}
