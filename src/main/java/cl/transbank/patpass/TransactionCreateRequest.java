package cl.transbank.patpass;

import cl.transbank.model.WebpayApiRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@NoArgsConstructor @RequiredArgsConstructor
@Getter @Setter @ToString
class TransactionCreateRequest extends WebpayApiRequest {
    @SerializedName("buy_order") @NonNull private String buyOrder;
    @SerializedName("session_id") @NonNull private String sessionId;
    @SerializedName("amount") @NonNull private double amount;
    @SerializedName("return_url") @NonNull private String returnUrl;
    @SerializedName("wpm_detail") private Detail wpmDetail;

    void setDetails(String serviceId, String cardHolderId, String cardHolderName, String cardHolderLastName1,
                   String cardHolderLastName2, String cardHolderMail, String cellphoneNumber, String expirationDate, String commerceMail, boolean ufFlag) {
        wpmDetail = new Detail(serviceId, cardHolderId, cardHolderName, cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag);
    }

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class Detail {
        @SerializedName("service_id") private String serviceId;
        @SerializedName("card_holder_id") private String cardHolderId;
        @SerializedName("card_holder_name") private String cardHolderName;
        @SerializedName("card_holder_last_name1") private String cardHolderLastName1;
        @SerializedName("card_holder_last_name2") private String cardHolderLastName2;
        @SerializedName("card_holder_mail") private String cardHolderMail;
        @SerializedName("cellphone_number") private String cellphoneNumber;
        @SerializedName("expiration_date") private String expirationDate;
        @SerializedName("commerce_mail") private String commerceMail;
        @SerializedName("uf_flag") private boolean ufFlag;
    }
}
