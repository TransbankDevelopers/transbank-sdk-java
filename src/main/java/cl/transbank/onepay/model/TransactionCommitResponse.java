package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TransactionCommitResponse {
    private String occ;
    private String authorizationCode;
    private String signature;
    private String transactionDesc;
    private String buyOrder;
    private long issuedAt;
    private long amount;
    private long installmentsAmount;
    private int installmentsNumber;
}
