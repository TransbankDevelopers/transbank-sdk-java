package cl.transbank.patpass.model;

import cl.transbank.model.CardDetail;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class PatpassByWebpayTransactionStatusResponse {
    private String vci;
    private double amount;
    private String status;
    private double balance;
    private String buyOrder;
    private String sessionId;
    private CardDetail cardDetail;
    private String accountingDate;
    private String transactionDate;
    private String authorizationCode;
    private String paymentTypeCode;
    private byte installmentsNumber;
}
