package cl.transbank.webpay.webpayplus.model;

import cl.transbank.webpay.model.CardDetail;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class CommitWebpayPlusMallTransactionResponse {
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
        private byte installmentsNumber;
        private String commerceCode;
        private String buyOrder;
    }
}
