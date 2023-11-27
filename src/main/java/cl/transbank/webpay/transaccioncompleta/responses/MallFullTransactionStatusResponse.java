package cl.transbank.webpay.transaccioncompleta.responses;

import cl.transbank.model.CardDetail;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionStatusResponse {
    private String buyOrder;
    private String sessionId;
    private String cardNumber;
    private String expirationDate;
    private String accountingDate;
    private String transactionDate;
    private List<Detail> details;
    private Double prepaidBalance;
    private CardDetail cardDetail;

    @NoArgsConstructor @AllArgsConstructor
    @Data
    public class Detail {
        private String authorizationCode;
        private String paymentTypeCode;
        private byte responseCode;
        private double installmentsAmount;
        private byte installmentsNumber;
        private double amount;
        private String commerceCode;
        private String buyOrder;
        private String status;
        private double balance;
    }
}
