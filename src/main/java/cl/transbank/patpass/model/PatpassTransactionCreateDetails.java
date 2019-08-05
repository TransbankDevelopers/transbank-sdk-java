package cl.transbank.patpass.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatpassTransactionCreateDetails {
    private List<Detail> detailList = new ArrayList<>();

    private PatpassTransactionCreateDetails() {}

    public static PatpassTransactionCreateDetails build() {
        return new PatpassTransactionCreateDetails();
    }

    public static PatpassTransactionCreateDetails build(
            String serviceId, String cardHolderId, String cardHolderName, String cardHolderLastName1, String cardHolderLastName2,
            String cardHolderMail, String cellphoneNumber, String expirationDate, String commerceMail, boolean ufFlag) {
        return PatpassTransactionCreateDetails.build().add(
                serviceId, cardHolderId, cardHolderName, cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag);
    }

    public PatpassTransactionCreateDetails add(
            String serviceId, String cardHolderId, String cardHolderName, String cardHolderLastName1, String cardHolderLastName2,
            String cardHolderMail, String cellphoneNumber, String expirationDate, String commerceMail, boolean ufFlag) {
        detailList.add(new Detail(
                serviceId, cardHolderId, cardHolderName, cardHolderLastName1, cardHolderLastName2, cardHolderMail, cellphoneNumber, expirationDate, commerceMail, ufFlag));
        return this;
    }

    public List<Detail> getDetails() {
        return Collections.unmodifiableList(detailList);
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
