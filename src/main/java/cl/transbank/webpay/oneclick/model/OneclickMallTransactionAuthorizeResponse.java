package cl.transbank.webpay.oneclick.model;

import cl.transbank.model.CardDetail;
import lombok.*;

import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OneclickMallTransactionAuthorizeResponse {
    private String transactionDate;
    private String accountingDate;
    private CardDetail cardDetail;
    private String buyOrder;
    private List<Detail> details;

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class Detail {
        private double amount;
        private String status;
        private String authorizationCode;
        private String paymentTypeCode;
        private byte installmentsNumber;
        private String commerceCode;
        private String buyOrder;
        private byte responseCode;

        public Detail (double amount, String status, String authorizationCode, String paymentTypeCode, byte
                installmentsNumber, String commerceCode, String buyOrder){
            this.amount = amount;
            this.status = status;
            this.authorizationCode = authorizationCode;
            this.paymentTypeCode = paymentTypeCode;
            this.installmentsNumber = installmentsNumber;
            this.commerceCode = commerceCode;
            this.buyOrder = buyOrder;
        }
    }
}
