package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import java.net.URL;

public abstract class ServiceWrapperBase {

    private Webpay.Environment mode;
    private SoapSignature signature;

    protected abstract String getWsdlName();

    protected ServiceWrapperBase(Webpay.Environment mode, SoapSignature signature) {
        this.mode = mode;
        this.signature = signature;
    }

    protected void initPort(Object port) throws Exception {
        if (signature != null) {
            signature.applySignature(port);
        }
    }

    public URL getWsdlUrl() {
        return this.getClass().getResource("/wsdl/" + mode.getInternalName() + "/" + getWsdlName());
    }

}
