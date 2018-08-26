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
import com.transbank.webpay.wswebpay.service.TransactionResultOutput;
import com.transbank.webpay.wswebpay.service.WSWebpayService;
import com.transbank.webpay.wswebpay.service.WSWebpayServiceImplService;
import com.transbank.webpay.wswebpay.service.WsInitTransactionInput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetail;
import com.transbank.webpay.wswebpay.service.WsTransactionType;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;

/**

 *
 * @author rbertuzzi
 */
public class WebpayNormal {
    WSWebpayService port;
    String commerceCode;
    
    public WebpayNormal(Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        this.commerceCode = commerceCode;
        
        URL wsdl = this.getClass().getResource("/wsdl/" + mode.getInternalName() + "/normal.wsdl");

        WSWebpayServiceImplService ss = new WSWebpayServiceImplService(wsdl);        
        this.port = ss.getWSWebpayServiceImplPort();
        if (signature != null){
            signature.applySignature(port);
        }       
    }
    
    
    public WsInitTransactionOutput initTransaction(WsInitTransactionInput input){
        return port.initTransaction(input);
    }
    
    public TransactionResultOutput getTransactionResult(String token){
        TransactionResultOutput result = port.getTransactionResult(token);
        //Se realiza siempre un acknowledge luego de obtener el resultado de la transaccion
        this.acknowledgeTransaction(token);        
        return result;
    }
    
    public void acknowledgeTransaction(String token){
        port.acknowledgeTransaction(token);
    }
            
    public WsInitTransactionOutput initTransaction(double amount, String sessionId, String buyOrder, String returnUrl, String finalUrl){
        WsInitTransactionInput in = new WsInitTransactionInput();
        in.setWSTransactionType(WsTransactionType.TR_NORMAL_WS);        
        in.setBuyOrder(buyOrder);
        in.setSessionId(sessionId);
        in.setReturnURL(returnUrl);
        in.setFinalURL(finalUrl);
        List<WsTransactionDetail> list = in.getTransactionDetails();
        WsTransactionDetail detail = new WsTransactionDetail();
        detail.setAmount(new BigDecimal(amount));
        detail.setBuyOrder(buyOrder);
        detail.setCommerceCode(this.commerceCode);
        
        list.add(detail);
        
        return this.initTransaction(in);
    }
    
    
            
}
