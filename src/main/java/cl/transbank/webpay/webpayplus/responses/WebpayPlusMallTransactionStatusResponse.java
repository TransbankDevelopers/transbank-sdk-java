package cl.transbank.webpay.webpayplus.responses;

import cl.transbank.model.CardDetail;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class WebpayPlusMallTransactionStatusResponse {
    private String vci;
    private String buyOrder;
    private String sessionId;
    private CardDetail cardDetail;
    private String accountingDate;
    private String transactionDate;
    private List<Detail> details;

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class Detail {
        private double amount;
        private String status;
        private String authorizationCode;
        private String paymentTypeCode;
        private byte responseCode;
        private double installmentsAmount;
        private byte installmentsNumber;
        private String commerceCode;
        private String buyOrder;
    }
}
