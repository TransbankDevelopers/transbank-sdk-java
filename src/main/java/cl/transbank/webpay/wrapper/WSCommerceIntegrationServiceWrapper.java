package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import com.transbank.webpay.wswebpay.service.*;

public class WSCommerceIntegrationServiceWrapper extends ServiceWrapperBase {

    private WSCommerceIntegrationService port;

    @Override
    protected String getWsdlName() {
        return "transbank-ws-commerce-integration-service.wsdl";
    }

    protected WSCommerceIntegrationServiceWrapper(Webpay.Environment environment, SoapSignature signature) throws Exception {
        super(environment, signature);
        this.port = new WSCommerceIntegrationServiceImplService(getWsdlUrl()).getWSCommerceIntegrationServiceImplPort();
        initPort(port);
    }

    public CaptureOutput capture(CaptureInput input){
        return port.capture(input);
    }

    public NullificationOutput nullify(NullificationInput input){
        return port.nullify(input);
    }
}
