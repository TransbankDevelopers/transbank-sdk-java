package cl.transbank.webpay.transaccioncompleta.responses;

import cl.transbank.model.CardDetail;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionStatusResponse {
    private String vci;
    private double amount;
    private String status;
    private String buyOrder;
    private String sessionId;
    private String cardNumber;
    private String accountingDate;
    private String transactionDate;
    private String authorizationCode;
    private String paymentTypeCode;
    private byte responseCode;
    private byte installmentsNumber;
    private double installmentsAmount;
    private String balance;
    private Double prepaidBalance;
    private CardDetail cardDetail;
}
