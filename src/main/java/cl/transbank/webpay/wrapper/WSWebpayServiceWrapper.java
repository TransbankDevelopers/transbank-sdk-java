package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.*;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

public class WSWebpayServiceWrapper extends ServiceWrapperBase {

    private WSWebpayService port;

    protected  WSWebpayServiceWrapper(Webpay.Environment environment, SoapSignature signature) throws Exception {
        super(environment, signature);
        this.port = initPort(
                WSWebpayService.class,
                WSWebpayServiceImplService.SERVICE,
                WSWebpayServiceImplService.WSWebpayServiceImplPort,
                "transbank-ws-webpay-service.wsdl"
        );
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
