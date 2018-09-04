package cl.transbank.webpay;

import cl.transbank.webpay.Webpay.Environment;
import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.WSWebpayServiceWrapper;
import com.transbank.webpay.wswebpay.service.*;

import java.math.BigDecimal;
import java.util.List;

public class WebpayNormal extends WSWebpayServiceWrapper {
    String commerceCode;

    public WebpayNormal(Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        super(mode, signature);
        this.commerceCode = commerceCode;
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
        detail.setAmount(BigDecimal.valueOf(amount));
        detail.setBuyOrder(buyOrder);
        detail.setCommerceCode(this.commerceCode);
        list.add(detail);
        return this.initTransaction(in);
    }
}
