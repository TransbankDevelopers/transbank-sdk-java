package cl.transbank.webpay.webpayplus;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter @ToString
public class WebpayDetails {
    @SerializedName("amount") private double amount;
    @SerializedName("status") private String status;
    @SerializedName("authorization_code") private String authorizationCode;
    @SerializedName("payment_type_code") private String paymentTypeCode;
    @SerializedName("response_code") private byte responseCode;
    @SerializedName("installments_amount") private double installmentsAmount;
    @SerializedName("installments_number") private byte installmentsNumber;
    @SerializedName("commerce_code") private String commerceCode;
    @SerializedName("buy_order") private String buyOrder;
}



