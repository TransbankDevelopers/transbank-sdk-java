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

    /**
     * Capture a transaction created by {@link WebpayMallNormal}, specifying the
     * store code for the transaction to capture
     * @param authorizationCode authorization code of the transaction to capture
     * @param captureAmount amount to capture
     * @param buyOrder buy order of the transaction to capture
     * @param storeCode "commerce code" of the store associated with the transaction.
     * @return the result of the capture operation
     */
    public CaptureOutput capture(String authorizationCode, BigDecimal captureAmount, String buyOrder, Long storeCode){
        CaptureInput input = new CaptureInput();
        input.setAuthorizationCode(authorizationCode);
        input.setBuyOrder(buyOrder);
        input.setCaptureAmount(captureAmount);    
        input.setCommerceId(storeCode);
        return capture(input);        
    }

    /**
     * Capture a transaction created by {@link WebpayNormal}.
     *
     * It'll use the commerce code associated with the configuration passed to
     * the {@link Webpay} instance used to create the capture transaction.
     * @param authorizationCode authorization code of the transaction to capture
     * @param captureAmount amount to capture
     * @param buyOrder
     * @return
     */
    public CaptureOutput capture(String authorizationCode, BigDecimal captureAmount, String buyOrder) {
        return capture(authorizationCode, captureAmount, buyOrder, Long.valueOf(commerceCode));
    }

}
