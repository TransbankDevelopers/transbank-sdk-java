package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class RefundTransactionResponse {
    @SerializedName("type") private String type;
    @SerializedName("balance") private double balance;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("response_code") private byte responseCode;
    @SerializedName("authorization_date") private String authorizationDate;
    @SerializedName("nullified_amount") private double nullifiedAmount;
    @SerializedName("error_message") private String errorMessage;
}
