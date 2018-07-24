package cl.transbank.onepay.model;

import cl.transbank.onepay.exception.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
    public String getHashableString() throws SignatureException {
        final String occ = getOcc();
        final String authorizationCode = getAuthorizationCode();
        final String issuedAtAsString = String.valueOf(getIssuedAt());
        final String amountAsString = String.valueOf(getAmount());
        final String installmentsAmountAsString = String.valueOf(getInstallmentsAmount());
        final String installmentsNumberAsString = String.valueOf(getInstallmentsNumber());
        final String buyOrder = getBuyOrder();

        return occ.length() + occ
                + authorizationCode.length() + authorizationCode
                + issuedAtAsString.length() + issuedAtAsString
                + amountAsString.length() + amountAsString
                + installmentsAmountAsString.length() + installmentsAmountAsString
                + installmentsNumberAsString.length() + installmentsNumberAsString
                + buyOrder.length() + buyOrder;
    }
}
