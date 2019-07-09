package cl.transbank.webpay.webpayplus.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class CreateMallTransactionDetails {
    private List<Detail> detailList = new ArrayList<>();

    public static CreateMallTransactionDetails build() {
        return new CreateMallTransactionDetails();
    }

    public static CreateMallTransactionDetails build(double amount, String commerceCode, String buyOrder) {
        return CreateMallTransactionDetails.build().add(amount, commerceCode, buyOrder);
    }

    public CreateMallTransactionDetails add(double amount, String commerceCode, String buyOrder) {
        getDetails().add(new Detail(amount, commerceCode, buyOrder));
        return this;
    }

    public boolean remove(double amount, String commerceCode, String buyOrder) {
        return getDetails().remove(new Detail(amount, commerceCode, buyOrder));
    }

    public List<Detail> getDetails() {
        return detailList;
    }

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class Detail {
        @SerializedName("amount") private double amount;
        @SerializedName("commerce_code")private String commerceCode;
        @SerializedName("buy_order")private String buyOrder;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Detail))
                return false;

            Detail another = (Detail) obj;
            return getAmount() == another.getAmount() && getCommerceCode().equals(another.getBuyOrder()) &&
                    getBuyOrder().equals(another.getBuyOrder());
        }
    }
}
