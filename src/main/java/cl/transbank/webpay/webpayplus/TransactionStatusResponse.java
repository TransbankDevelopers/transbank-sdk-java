package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class TransactionStatusResponse {
    @SerializedName("vci") private String vci;
    @SerializedName("amount") private double amount;
    @SerializedName("status") private String status;
    @SerializedName("buy_order") private String buyOrder;
    @SerializedName("session_id") private String sessionId;
    @SerializedName("card_detail") private CardDetail cardDetail;
    @SerializedName("accounting_date") private String accountingDate;
    @SerializedName("transaction_date") private String transactionDate;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("payment_type_code") private String paymentTypeCode;
    @SerializedName("response_code") private byte responseCode;
    @SerializedName("installments_amount") private double installmentsAmount;
    @SerializedName("installments_number") private byte installmentsNumber;
    @SerializedName("balance") private double balance;
    @SerializedName("error_message") private String errorMessage;
}
