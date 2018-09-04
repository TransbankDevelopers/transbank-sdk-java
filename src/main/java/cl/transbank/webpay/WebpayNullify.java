package cl.transbank.webpay;

import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.WSCommerceIntegrationServiceWrapper;
import com.transbank.webpay.wswebpay.service.NullificationInput;
import com.transbank.webpay.wswebpay.service.NullificationOutput;
import com.transbank.webpay.wswebpay.service.NullifyResponse;

import java.math.BigDecimal;

public class WebpayNullify extends WSCommerceIntegrationServiceWrapper {
    
    String commerceCode;
    
    public WebpayNullify(Webpay.Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        super(mode, signature);
        this.commerceCode = commerceCode;
    }

    public NullificationOutput nullify(String authorizationCode, BigDecimal authorizedAmount, String buyOrder,BigDecimal nullifyAmount) {
        return nullify(authorizationCode, authorizedAmount, buyOrder, nullifyAmount, null);
    }

    public NullificationOutput nullify(String authorizationCode, BigDecimal authorizedAmount, String buyOrder,BigDecimal nullifyAmount, Long commercecode){
        
            NullificationInput input = new NullificationInput();
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
}
