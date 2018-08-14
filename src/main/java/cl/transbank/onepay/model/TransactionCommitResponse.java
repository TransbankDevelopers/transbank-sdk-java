package cl.transbank.onepay.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString(callSuper = true)
public class TransactionCommitResponse implements Signable {
    private String occ;
    private String authorizationCode;
    private String signature;
    private String transactionDesc;
    private String buyOrder;
    private long issuedAt;
    private long amount;
    private long installmentsAmount;
    private int installmentsNumber;

    @Override
    public String getHashableString() {
        final String occ = Objects.toString(getOcc(), "");
        final String authorizationCode = Objects.toString(getAuthorizationCode(), "");
        final String issuedAtAsString = String.valueOf(getIssuedAt());
        final String amountAsString = String.valueOf(getAmount());
        final String installmentsAmountAsString = String.valueOf(getInstallmentsAmount());
        final String installmentsNumberAsString = String.valueOf(getInstallmentsNumber());
        final String buyOrder = Objects.toString(getBuyOrder(), "");

        return occ.length() + occ
                + authorizationCode.length() + authorizationCode
                + issuedAtAsString.length() + issuedAtAsString
                + amountAsString.length() + amountAsString
                + installmentsAmountAsString.length() + installmentsAmountAsString
                + installmentsNumberAsString.length() + installmentsNumberAsString
                + buyOrder.length() + buyOrder;
    }
}
