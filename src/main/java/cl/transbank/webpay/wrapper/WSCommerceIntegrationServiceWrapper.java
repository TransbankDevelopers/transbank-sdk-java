package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.*;

import javax.xml.namespace.QName;

public class WSCommerceIntegrationServiceWrapper extends ServiceWrapperBase {

    private WSCommerceIntegrationService port;

    protected WSCommerceIntegrationServiceWrapper(Webpay.Environment environment, SoapSignature signature) throws Exception {
        super(environment, signature);
        // WSCommerceIntegrationServiceImplService was NOT generated using CXF
        // so we are forced to copy/paste the QNames for the service and port :(
        this.port = initPort(
                WSCommerceIntegrationService.class,
                new QName(
                        "http://service.wswebpay.webpay.transbank.com/",
                        "WSCommerceIntegrationServiceImplService"
                ),
                new QName(
                        "http://service.wswebpay.webpay.transbank.com/",
                        "WSCommerceIntegrationServiceImplPort"
                ),
                "transbank-ws-commerce-integration-service.wsdl"
        );

    }

    public CaptureOutput capture(CaptureInput input){
        return port.capture(input);
    }

    public NullificationOutput nullify(NullificationInput input){
        return port.nullify(input);
    }
}
