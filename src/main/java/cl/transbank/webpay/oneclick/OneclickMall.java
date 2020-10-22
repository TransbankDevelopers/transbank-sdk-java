package cl.transbank.webpay.oneclick;

import cl.transbank.common.IntegrationType;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.common.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.util.HttpUtil;
import cl.transbank.webpay.WebpayOptions;
import cl.transbank.webpay.WebpayApiResource;
import cl.transbank.webpay.exception.*;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.oneclick.model.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

@Deprecated
public class OneclickMall extends Oneclick {

    @Deprecated
    public static class Inscription extends MallInscription {
    }

    @Deprecated
    public static class Transaction extends  MallTransaction {
    }
}
