/**
  * @author     Allware Ltda. (http://www.allware.cl)
  * @copyright  2015 Transbank S.A. (http://www.tranbank.cl)
  * @date       Jan 2016
  * @license    GNU LGPL
  * @version    2.0.1
  *
  */

package cl.transbank.webpay;

import cl.transbank.webpay.Webpay.Environment;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpayserver.webservices.OneClickFinishInscriptionInput;
import com.transbank.webpayserver.webservices.OneClickFinishInscriptionOutput;
import com.transbank.webpayserver.webservices.OneClickInscriptionInput;
import com.transbank.webpayserver.webservices.OneClickInscriptionOutput;
import com.transbank.webpayserver.webservices.OneClickPayInput;
import com.transbank.webpayserver.webservices.OneClickPayOutput;
import com.transbank.webpayserver.webservices.OneClickPaymentService;
import com.transbank.webpayserver.webservices.OneClickPaymentServiceImplService;
import com.transbank.webpayserver.webservices.OneClickRemoveUserInput;
import com.transbank.webpayserver.webservices.OneClickReverseInput;
import com.transbank.webpayserver.webservices.OneClickReverseOutput;
import java.math.BigDecimal;
import java.net.URL;

/**
 *
 * @author rbertuzzi
 */
public class WebpayOneClick {
    OneClickPaymentService port;
    String commerceCode;
    
    public WebpayOneClick(Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        this.commerceCode = commerceCode;
        
        URL wsdl = this.getClass().getResource("/wsdl/" + mode.name().toLowerCase() + "/oneclick.wsdl");
        //URL wsdl = new URL(WSDL.get(mode));

        OneClickPaymentServiceImplService ss = new OneClickPaymentServiceImplService(wsdl);
        this.port = ss.getOneClickPaymentServiceImplPort();
        if (signature != null){
            signature.applySignature(port);
        }       
    }
    
    
    public OneClickInscriptionOutput initInscription(String username, String email, String urlReturn){
        
        OneClickInscriptionInput input = new OneClickInscriptionInput();
        input.setUsername(username);
        input.setEmail(email);
        input.setResponseURL(urlReturn);
                
        return this.initInscription(input);
    }
    
    
    public OneClickInscriptionOutput initInscription(OneClickInscriptionInput input){
        return port.initInscription(input);
    }
       
    
    public OneClickFinishInscriptionOutput finishInscription(String token){
        
        OneClickFinishInscriptionInput input = new OneClickFinishInscriptionInput();
        input.setToken(token);
                
        return this.finishInscription(input);
    }    
    
    public OneClickFinishInscriptionOutput finishInscription(OneClickFinishInscriptionInput input){
        return port.finishInscription(input);
    }
    
    
    public OneClickPayOutput authorize(Long buyOrder, String tbkUser, String username, BigDecimal amount){
        
        OneClickPayInput input = new OneClickPayInput();
        
        input.setAmount(amount);
        input.setBuyOrder(buyOrder);
        input.setTbkUser(tbkUser);
        input.setUsername(username);        
        
        return this.authorize(input);
    }
    
    public OneClickPayOutput authorize(OneClickPayInput input){
        return port.authorize(input);
    }
    
    
    public OneClickReverseOutput codeReverseOneClick(OneClickReverseInput input){
        return port.codeReverseOneClick(input);
    }       
    
    public boolean reverseTransaction(Long buyOrder){
        
        OneClickReverseInput input = new OneClickReverseInput();
        input.setBuyorder(buyOrder);
        
        return this.reverseTransaction(input);
    }
        
    public boolean reverseTransaction(OneClickReverseInput input){
        return port.reverse(input);
    }
    
    
    public boolean removeUser(String tbkUser, String username){
        
        OneClickRemoveUserInput input = new OneClickRemoveUserInput();
        
        input.setTbkUser(tbkUser);
        input.setUsername(username);
        
        return this.removeUser(input);
    } 
    
    public boolean removeUser(OneClickRemoveUserInput input){
        return port.removeUser(input);
    } 
}
