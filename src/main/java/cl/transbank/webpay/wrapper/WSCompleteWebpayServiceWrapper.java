package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.*;

import java.util.List;

public class WSCompleteWebpayServiceWrapper extends ServiceWrapperBase {

    private WSCompleteWebpayService port;

    @Override
    protected String getWsdlName() {
        return "transbank-ws-complete-webpay-service.wsdl";
    }

    protected WSCompleteWebpayServiceWrapper(Webpay.Environment environment, SoapSignature signature) throws Exception {
        super(environment, signature);
        this.port = new WSCompleteWebpayServiceImplService(getWsdlUrl()).getWSCompleteWebpayServiceImplPort();
        initPort(port);
    }

    public WsCompleteInitTransactionOutput initCompleteTransaction(WsCompleteInitTransactionInput input){
        return port.initCompleteTransaction(input);
    }

    public WsCompleteQuerySharesOutput queryShare(QueryShare queryShare){
        return port.queryShare(queryShare.getToken(), queryShare.getBuyOrder(), queryShare.getShareNumber());
    }


    @Deprecated
    public WsCompleteAuthorizeOutput autorize(String valor, List<WsCompletePaymentTypeInput> input){
        return authorize(valor, input);
    }

    public WsCompleteAuthorizeOutput authorize(String valor, List<WsCompletePaymentTypeInput> input){
        return port.authorize(valor, input);
    }


    public void acknowledgeCompleteTransaction(String token){
        port.acknowledgeCompleteTransaction(token);
    }


}
