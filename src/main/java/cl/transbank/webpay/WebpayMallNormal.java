package cl.transbank.webpay;

import cl.transbank.webpay.Webpay.Environment;
import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.WSWebpayServiceWrapper;
import com.transbank.webpay.wswebpay.service.WsInitTransactionInput;
import com.transbank.webpay.wswebpay.service.WsInitTransactionOutput;
import com.transbank.webpay.wswebpay.service.WsTransactionDetail;
import com.transbank.webpay.wswebpay.service.WsTransactionType;
import java.util.ArrayList;
import java.util.List;

public class WebpayMallNormal extends WSWebpayServiceWrapper {
    String commerceCode;
    
    public WebpayMallNormal(Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        super(mode, signature);
        this.commerceCode = commerceCode;
    }

    public WsInitTransactionOutput initTransaction(String buyOrder, String sessionId, String returnUrl, String finalUrl, List<WsTransactionDetail> storesTransactions) {
        WsInitTransactionInput in = new WsInitTransactionInput();
        in.setWSTransactionType(WsTransactionType.TR_MALL_WS);        
        in.setBuyOrder(buyOrder);
        in.setSessionId(sessionId);
        in.setReturnURL(returnUrl);
        in.setFinalURL(finalUrl);
        in.setCommerceId(this.commerceCode);
        in.getTransactionDetails().addAll(storesTransactions);
        return this.initTransaction(in);
    }
     
}
