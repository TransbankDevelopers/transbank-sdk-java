package cl.transbank.webpay.wrapper;

import cl.transbank.webpay.Webpay;
import cl.transbank.webpay.security.SoapSignature;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;

import javax.xml.namespace.QName;
import java.net.URL;

public abstract class ServiceWrapperBase {

    private Webpay.Environment mode;
    private SoapSignature signature;

    protected ServiceWrapperBase(Webpay.Environment mode, SoapSignature signature) {
        this.mode = mode;
        this.signature = signature;
    }

    protected <T> T initPort(Class<T> serviceClass, QName serviceName,
                             QName servicePort, String wsdlName)
            throws Exception {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceName(serviceName);
        factory.setEndpointName(servicePort);
        setFactoryWsdlURL(factory, getWsdlUrl(wsdlName));
        T port = factory.create(serviceClass);
        if (signature != null) { signature.applySignature(port); }
        return port;
    }

    private void setFactoryWsdlURL(JaxWsProxyFactoryBean factory, URL url) {
        // factory.setWsdlUrl receives a String and we need to pass on a
        // URL as the WSDL lives inside the classpath. So this is the convulted
        // way to do it:
        ReflectionServiceFactoryBean clientFactory =
                factory.getClientFactoryBean().getServiceFactory();
        clientFactory.setWsdlURL(url);
    }


    public URL getWsdlUrl(String wsdlName) {
        return this.getClass().getResource("/wsdl/" + mode.getInternalName() + "/" + wsdlName);
    }

}
