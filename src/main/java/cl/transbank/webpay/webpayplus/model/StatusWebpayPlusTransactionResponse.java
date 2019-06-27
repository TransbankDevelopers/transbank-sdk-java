package cl.transbank.webpay.webpayplus.model;

import cl.transbank.webpay.model.CardDetail;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
public class StatusWebpayPlusTransactionResponse {
    private String vci;
    private double amount;
    private String status;
    private String buyOrder;
    private String sessionId;
    private CardDetail cardDetail;
    private String accountingDate;
    private String transactionDate;
    private String authorizationCode;
    private String paymentTypeCode;
    private byte responseCode;
    private double installmentsAmount;
    private byte installmentsNumber;
    private double balance;
}
