package cl.transbank.patpass.responses;

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
         private String serviceId;
        private String cardHolderId;
        private String cardHolderName;
        private String cardHolderLastName1;
        private String cardHolderLastName2;
        private String cardHolderMail;
        private String cellphoneNumber;
        private String expirationDate;
        private String commerceMail;
        private boolean ufFlag;
    }
}
