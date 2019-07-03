package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class TransactionCaptureResponse {
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("authorization_date") private String authorizationDate;
    @SerializedName("captured_amount") private double capturedAmount;
    @SerializedName("response_code") private byte responseCode;
    @SerializedName("error_message") private String errorMessage;
}
