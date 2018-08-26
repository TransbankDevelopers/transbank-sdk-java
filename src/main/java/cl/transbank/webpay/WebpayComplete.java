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
import com.transbank.webpay.wswebpay.service.CompleteCardDetail;
import com.transbank.webpay.wswebpay.service.QueryShare;
import com.transbank.webpay.wswebpay.service.WSCompleteWebpayService;
import com.transbank.webpay.wswebpay.service.WSCompleteWebpayServiceImplService;
import com.transbank.webpay.wswebpay.service.WsCompleteAuthorizeOutput;
import com.transbank.webpay.wswebpay.service.WsCompleteInitTransactionInput;
import com.transbank.webpay.wswebpay.service.WsCompleteInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsCompletePaymentTypeInput;
import com.transbank.webpay.wswebpay.service.WsCompleteQueryShareInput;
import com.transbank.webpay.wswebpay.service.WsCompleteQuerySharesOutput;
import com.transbank.webpay.wswebpay.service.WsCompleteTransactionDetail;
import com.transbank.webpay.wswebpay.service.WsCompleteTransactionType;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jguerrero
 */
public class WebpayComplete {

WSCompleteWebpayService port;
    String commerceCode;
    
    public WebpayComplete(Webpay.Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        this.commerceCode = commerceCode;
        
        URL wsdl = this.getClass().getResource("/wsdl/" + mode.getInternalName() + "/complete.wsdl");

        WSCompleteWebpayServiceImplService ss = new WSCompleteWebpayServiceImplService(wsdl);        
        this.port = ss.getWSCompleteWebpayServiceImplPort();
        if (signature != null){
            signature.applySignature(port);
        }       
    }
   
    
    public WsCompleteInitTransactionOutput initCompleteTransaction(WsCompleteInitTransactionInput input){
        return port.initCompleteTransaction(input);
    }
            
    
    public WsCompleteInitTransactionOutput initCompleteTransaction(double amount, String buyOrder, String sessionId,  String cardExpirationDate, String cvv, String cardNumber){
        
        WsCompleteInitTransactionInput in = new WsCompleteInitTransactionInput();
        
        in.setBuyOrder(buyOrder);      
        in.setCommerceId(this.commerceCode);
        in.setSessionId(sessionId);
        in.setTransactionType(WsCompleteTransactionType.TR_COMPLETA_WS);
        
        WsCompleteTransactionDetail txDetail = new WsCompleteTransactionDetail();
        txDetail.setAmount(new BigDecimal(amount));
        txDetail.setBuyOrder(buyOrder);
        txDetail.setCommerceCode(this.commerceCode);
        
        CompleteCardDetail cardDetail = new CompleteCardDetail();
        cardDetail.setCardExpirationDate(cardExpirationDate);
        cardDetail.setCardNumber(cardNumber);
        cardDetail.setCvv(new Integer(cvv));
        
        in.setCardDetail(cardDetail);
        in.getTransactionDetails().add(txDetail);
                
        return this.initCompleteTransaction(in);
    }
    
    public WsCompleteQuerySharesOutput queryShare(String token, String buyOrder, int shareNumber){
        
        WsCompleteQuerySharesOutput qsresponse = new WsCompleteQuerySharesOutput();
        
        QueryShare queryShare = new QueryShare();
        queryShare.setBuyOrder(buyOrder);
        queryShare.setShareNumber(shareNumber);
        queryShare.setToken(token);

        qsresponse = this.queryShare(queryShare);        
        return qsresponse;
    }
    
    public WsCompleteQuerySharesOutput queryShare(QueryShare queryShare){          
        return port.queryShare(queryShare.getToken(), queryShare.getBuyOrder(), queryShare.getShareNumber());
    }  
    
    
    public WsCompleteAuthorizeOutput autorize(String valor, List<WsCompletePaymentTypeInput> input){

        return port.authorize(valor, input);
    }
    
    public WsCompleteAuthorizeOutput autorize(String token, String buyOrder, boolean gracePeriod,  int idQueryShare, int deferredPeriodIndex){
        
        WsCompleteAuthorizeOutput result = new WsCompleteAuthorizeOutput();
        WsCompletePaymentTypeInput input = new WsCompletePaymentTypeInput();
        input.setBuyOrder(buyOrder);
        input.setCommerceCode(this.commerceCode);
        
        input.setGracePeriod(gracePeriod);
        
        WsCompleteQueryShareInput qsinput = new WsCompleteQueryShareInput();
        
        if (deferredPeriodIndex != 0 ){
            qsinput.setDeferredPeriodIndex(deferredPeriodIndex);  
        }
        
        qsinput.setIdQueryShare(idQueryShare);
        
        input.setQueryShareInput(qsinput);
        
        ArrayList<WsCompletePaymentTypeInput> list = new ArrayList<>();
        list.add(input);
        
        result = port.authorize(token, list);
        acknowledgeCompleteTransaction(token);
                
        return result;
    }   
    
    
    public void acknowledgeCompleteTransaction(String token){
        port.acknowledgeCompleteTransaction(token);
    }
        
}
