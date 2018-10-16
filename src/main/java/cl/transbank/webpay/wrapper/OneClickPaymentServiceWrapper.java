package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpayserver.webservices.*;

public class OneClickPaymentServiceWrapper extends ServiceWrapperBase {

    private OneClickPaymentService port;

    protected OneClickPaymentServiceWrapper(Webpay.Environment environment, SoapSignature signature) throws Exception {
        super(environment, signature);
        this.port = initPort(
                OneClickPaymentService.class,
                OneClickPaymentServiceImplService.SERVICE,
                OneClickPaymentServiceImplService.OneClickPaymentServiceImplPort,
                "transbank-oneclick-payment-service.wsdl"
        );
    }

    public OneClickInscriptionOutput initInscription(OneClickInscriptionInput input){
        return port.initInscription(input);
    }

    public OneClickFinishInscriptionOutput finishInscription(OneClickFinishInscriptionInput input){
        return port.finishInscription(input);
    }

    public OneClickPayOutput authorize(OneClickPayInput input){
        return port.authorize(input);
    }

    public OneClickReverseOutput codeReverseOneClick(OneClickReverseInput input){
        return port.codeReverseOneClick(input);
    }

    public boolean reverseTransaction(OneClickReverseInput input){
        return port.reverse(input);
    }

    public boolean removeUser(OneClickRemoveUserInput input){
        return port.removeUser(input);
    }

}
