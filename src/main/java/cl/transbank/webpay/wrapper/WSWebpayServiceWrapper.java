package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.*;

public class WSWebpayServiceWrapper extends ServiceWrapperBase {

    private WSWebpayService port;

    @Override
    protected String getWsdlName() { return "transbank-ws-webpay-service.wsdl"; }

    protected  WSWebpayServiceWrapper(Webpay.Environment environment, SoapSignature signature) throws Exception {
        super(environment, signature);
        this.port = new WSWebpayServiceImplService(getWsdlUrl()).getWSWebpayServiceImplPort();
        initPort(port);
    }

    public WsInitTransactionOutput initTransaction(WsInitTransactionInput input){
        return port.initTransaction(input);
    }

    public TransactionResultOutput getTransactionResult(String token){
        TransactionResultOutput result = port.getTransactionResult(token);
        //Se realiza siempre un acknowledge luego de obtener el resultado de la transaccion
        this.acknowledgeTransaction(token);
        return result;
    }

    public void acknowledgeTransaction(String token){
        port.acknowledgeTransaction(token);
    }
}
