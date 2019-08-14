package cl.transbank.transaccioncompleta.model;

import cl.transbank.transaccioncompleta.TransactionCommitRequest;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MallFullTransactionCommitResponse  {
    private String vci;
    private double amount;
    private String status;
    private String buyOrder;
    private String sessionId;
    private String cardNumber;
    private String expirationDate;
    private String accountingDate;
    private String transactionDate;

}
