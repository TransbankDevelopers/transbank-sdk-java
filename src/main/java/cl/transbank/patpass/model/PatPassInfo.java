package cl.transbank.patpass.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.GregorianCalendar;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class PatPassInfo {
    private String serviceId;
    private String cardHolderId;
    private String cardHolderName;
    private String cardHolderLastName1;
    private String cardHolderLastName2;
    private String cardHolderMail;
    private String cellPhoneNumber;
    private GregorianCalendar expirationDate;
}
