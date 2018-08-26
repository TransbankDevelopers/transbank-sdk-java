package cl.transbank.webpay;

import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.WSCommerceIntegrationServiceWrapper;
import com.transbank.webpay.wswebpay.service.CaptureInput;
import com.transbank.webpay.wswebpay.service.CaptureOutput;
import java.math.BigDecimal;

public class WebpayCapture extends WSCommerceIntegrationServiceWrapper {
    String commerceCode;
    
    public WebpayCapture(Webpay.Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        super(mode, signature);
        this.commerceCode = commerceCode;
    }
    
    public CaptureOutput capture(String authorizationCode, BigDecimal captureAmount, String buyOrder){
        CaptureInput input = new CaptureInput();
        input.setAuthorizationCode(authorizationCode);
        input.setBuyOrder(buyOrder);
        input.setCaptureAmount(captureAmount);    
        input.setCommerceId(new Long(commerceCode));
        return capture(input);        
    }      


}
