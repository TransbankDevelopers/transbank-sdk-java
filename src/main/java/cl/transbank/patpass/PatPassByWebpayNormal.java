package cl.transbank.patpass;

import cl.transbank.webpay.configuration.Configuration;
import cl.transbank.webpay.security.SoapSignature;
import cl.transbank.webpay.wrapper.WSWebpayServiceWrapper;
import com.transbank.webpay.wswebpay.service.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.math.BigDecimal;
import java.util.List;

public class PatPassByWebpayNormal extends WSWebpayServiceWrapper {
    private Configuration config;

    public enum Currency {
        DEFAULT, // pesos o UF
        UF;
    }



    public PatPassByWebpayNormal(Configuration config, SoapSignature signature) throws Exception {
        super(config.getEnvironment(), signature);
        this.config = config;
    }

    public WsInitTransactionOutput initTransaction(double amount, String buyOrder, String sessionId, String returnUrl, String finalUrl, PatPassInfo info) {
        WsInitTransactionInput in = new WsInitTransactionInput();
        in.setWSTransactionType(WsTransactionType.TR_NORMAL_WS_WPM);        
        in.setBuyOrder(buyOrder);
        in.setSessionId(sessionId);
        in.setReturnURL(returnUrl);
        in.setFinalURL(finalUrl);
        List<WsTransactionDetail> list = in.getTransactionDetails();
        WsTransactionDetail detail = new WsTransactionDetail();
        detail.setAmount(new BigDecimal(amount));
        detail.setBuyOrder(buyOrder);
        detail.setCommerceCode(config.getCommerceCode());
        list.add(detail);
        
        WpmDetailInput wpmDetail = new WpmDetailInput();
        wpmDetail.setServiceId(info.getServiceId());
        wpmDetail.setCardHolderId(info.getCardHolderId());
        wpmDetail.setCardHolderName(info.getCardHolderName());
        wpmDetail.setCardHolderLastName1(info.getCardHolderLastName1());
        wpmDetail.setCardHolderLastName2(info.getCardHolderLastName2());
        wpmDetail.setCardHolderMail(info.getCardHolderMail());
        wpmDetail.setCellPhoneNumber(info.getCellPhoneNumber());
        wpmDetail.setUfFlag(Currency.UF.equals(config.getPatPassCurrency()));

        try {
            wpmDetail.setExpirationDate(
                    DatatypeFactory.newInstance().newXMLGregorianCalendar(
                            info.getExpirationDate()));
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        wpmDetail.setCommerceMail(config.getCommerceMail());
        in.setWPMDetail(wpmDetail);
        return this.initTransaction(in);
    }
          
}
