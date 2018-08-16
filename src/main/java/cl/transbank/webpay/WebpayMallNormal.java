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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jguerrero
 */
public class WebpayMallNormal {
    WSWebpayService port;
    String commerceCode;
    
    public WebpayMallNormal(Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        this.commerceCode = commerceCode;
        
        URL wsdl = this.getClass().getResource("/wsdl/" + mode.name().toLowerCase() + "/normal.wsdl");
        //URL wsdl = new URL(WSDL.get(mode));

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
            
    public WsInitTransactionOutput initTransaction(String buyOrder, String sessionId, String returnUrl, String finalUrl, ArrayList storesTransactions){
        
        WsInitTransactionInput in = new WsInitTransactionInput();
        in.setWSTransactionType(WsTransactionType.TR_MALL_WS);        
        in.setBuyOrder(buyOrder);
        in.setSessionId(sessionId);
        in.setReturnURL(returnUrl);
        in.setFinalURL(finalUrl);
        in.setCommerceId(this.commerceCode);
                
        List<WsTransactionDetail> list = in.getTransactionDetails();
       
        for(int i=0; i<storesTransactions.size(); i++){
            WsTransactionDetail txDetail = new WsTransactionDetail();
            txDetail = (WsTransactionDetail)storesTransactions.get(i);
            list.add(txDetail);
        }
        
        return this.initTransaction(in);
    }
     
}
