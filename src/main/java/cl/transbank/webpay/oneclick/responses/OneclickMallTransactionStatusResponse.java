package cl.transbank.webpay.oneclick.responses;

import cl.transbank.model.CardDetail;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OneclickMallTransactionStatusResponse {
    private String buyOrder;
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
