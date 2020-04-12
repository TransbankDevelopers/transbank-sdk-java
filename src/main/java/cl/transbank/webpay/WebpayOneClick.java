package cl.transbank.webpay;

import cl.transbank.webpay.Webpay.Environment;
import cl.transbank.webpay.exception.InvalidAmountException;
import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.OneClickPaymentServiceWrapper;
import com.transbank.webpayserver.webservices.OneClickFinishInscriptionInput;
import com.transbank.webpayserver.webservices.OneClickFinishInscriptionOutput;
import com.transbank.webpayserver.webservices.OneClickInscriptionInput;
import com.transbank.webpayserver.webservices.OneClickInscriptionOutput;
import com.transbank.webpayserver.webservices.OneClickPayInput;
import com.transbank.webpayserver.webservices.OneClickPayOutput;
import com.transbank.webpayserver.webservices.OneClickRemoveUserInput;
import com.transbank.webpayserver.webservices.OneClickReverseInput;

import java.math.BigDecimal;

public class WebpayOneClick extends OneClickPaymentServiceWrapper {
    String commerceCode;
    
    public WebpayOneClick(Environment mode, String commerceCode, SoapSignature signature) throws Exception {
        super(mode, signature);
        this.commerceCode = commerceCode;
    }
    
    public OneClickInscriptionOutput initInscription(String username, String email, String urlReturn){
        OneClickInscriptionInput input = new OneClickInscriptionInput();
        input.setUsername(username);
        input.setEmail(email);
        input.setResponseURL(urlReturn);
        return this.initInscription(input);
    }

    public OneClickFinishInscriptionOutput finishInscription(String token){
        OneClickFinishInscriptionInput input = new OneClickFinishInscriptionInput();
        input.setToken(token);
        return this.finishInscription(input);
    }    

    public OneClickPayOutput authorize(Long buyOrder, String tbkUser, String username, BigDecimal amount) throws InvalidAmountException {
        OneClickPayInput input = new OneClickPayInput();
        input.setAmount(amount);
        input.setBuyOrder(buyOrder);
        input.setTbkUser(tbkUser);
        input.setUsername(username);
        return this.authorize(input);
    }

    public boolean reverseTransaction(Long buyOrder){
        OneClickReverseInput input = new OneClickReverseInput();
        input.setBuyorder(buyOrder);
        return this.reverseTransaction(input);
    }

    public boolean removeUser(String tbkUser, String username){
        OneClickRemoveUserInput input = new OneClickRemoveUserInput();
        input.setTbkUser(tbkUser);
        input.setUsername(username);
        return this.removeUser(input);
    }
}
