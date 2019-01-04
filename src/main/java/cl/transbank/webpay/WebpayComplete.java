package cl.transbank.webpay;

import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.WSCompleteWebpayServiceWrapper;
import com.transbank.webpay.wswebpay.service.CompleteCardDetail;
import com.transbank.webpay.wswebpay.service.QueryShare;
import com.transbank.webpay.wswebpay.service.WsCompleteAuthorizeOutput;
import com.transbank.webpay.wswebpay.service.WsCompleteInitTransactionInput;
import com.transbank.webpay.wswebpay.service.WsCompleteInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsCompletePaymentTypeInput;
import com.transbank.webpay.wswebpay.service.WsCompleteQueryShareInput;
import com.transbank.webpay.wswebpay.service.WsCompleteQuerySharesOutput;
import com.transbank.webpay.wswebpay.service.WsCompleteTransactionDetail;
import com.transbank.webpay.wswebpay.service.WsCompleteTransactionType;
import java.math.BigDecimal;
import java.util.ArrayList;

public class WebpayComplete extends WSCompleteWebpayServiceWrapper {

    String commerceCode;
    
    public WebpayComplete(Webpay.Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        super(mode, signature);
        this.commerceCode = commerceCode;
    }

    public WsCompleteInitTransactionOutput initCompleteTransaction(double amount, String buyOrder, String sessionId,  String cardExpirationDate, String cvv, String cardNumber){
        
        WsCompleteInitTransactionInput in = new WsCompleteInitTransactionInput();
        
        in.setBuyOrder(buyOrder);      
        in.setCommerceId(this.commerceCode);
        in.setSessionId(sessionId);
        in.setTransactionType(WsCompleteTransactionType.TR_COMPLETA_WS);
        
        WsCompleteTransactionDetail txDetail = new WsCompleteTransactionDetail();
        txDetail.setAmount(BigDecimal.valueOf(amount));
        txDetail.setBuyOrder(buyOrder);
        txDetail.setCommerceCode(this.commerceCode);
        
        CompleteCardDetail cardDetail = new CompleteCardDetail();
        cardDetail.setCardExpirationDate(cardExpirationDate);
        cardDetail.setCardNumber(cardNumber);
        cardDetail.setCvv(Integer.valueOf(cvv));
        
        in.setCardDetail(cardDetail);
        in.getTransactionDetails().add(txDetail);
                
        return this.initCompleteTransaction(in);
    }
    
    public WsCompleteQuerySharesOutput queryShare(String token, String buyOrder, int shareNumber){
        QueryShare queryShare = new QueryShare();
        queryShare.setBuyOrder(buyOrder);
        queryShare.setShareNumber(shareNumber);
        queryShare.setToken(token);
        return this.queryShare(queryShare);
    }

    @Deprecated
    public WsCompleteAuthorizeOutput autorize(String token, String buyOrder, boolean gracePeriod,  int idQueryShare, int deferredPeriodIndex) {
        return authorize(token, buyOrder, gracePeriod, idQueryShare, deferredPeriodIndex);
    }

    public WsCompleteAuthorizeOutput authorize(String token, String buyOrder, boolean gracePeriod,  int idQueryShare, int deferredPeriodIndex){
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

        WsCompleteAuthorizeOutput result = authorize(token, list);
        acknowledgeCompleteTransaction(token);
                
        return result;
    }
}
