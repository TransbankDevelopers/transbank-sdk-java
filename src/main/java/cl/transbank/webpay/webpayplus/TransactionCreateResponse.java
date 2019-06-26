package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@ToString
class TransactionCreateResponse {
    @SerializedName("token") private String token;
    @SerializedName("url") private String url;
    @SerializedName("error_message") private String errorMessage;
}
