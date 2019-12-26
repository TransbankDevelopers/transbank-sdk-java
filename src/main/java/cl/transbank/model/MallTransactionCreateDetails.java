package cl.transbank.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MallTransactionCreateDetails {
    private List<MallTransactionCreateDetails.Detail> detailList = new ArrayList<>();

    private MallTransactionCreateDetails() {}

    public static MallTransactionCreateDetails build() {
        return new MallTransactionCreateDetails();
    }

    public static MallTransactionCreateDetails build(double amount, String commerceCode, String buyOrder) {
        return MallTransactionCreateDetails.build().add(amount, commerceCode, buyOrder);
    }

    public MallTransactionCreateDetails add(double amount, String commerceCode, String buyOrder) {
        detailList.add(new MallTransactionCreateDetails.Detail(amount, commerceCode, buyOrder));
        return this;
    }

    public boolean remove(double amount, String commerceCode, String buyOrder) {
        return getDetails().remove(new MallTransactionCreateDetails.Detail(amount, commerceCode, buyOrder));
    }

    public List<MallTransactionCreateDetails.Detail> getDetails() {
        return Collections.unmodifiableList(detailList);
    }

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class Detail {
        @SerializedName("amount") private double amount;
        @SerializedName("commerce_code")private String commerceCode;
        @SerializedName("buy_order")private String buyOrder;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MallTransactionCreateDetails.Detail))
                return false;

            MallTransactionCreateDetails.Detail another = (MallTransactionCreateDetails.Detail) obj;
            return getAmount() == another.getAmount() && getCommerceCode().equals(another.getBuyOrder()) &&
                    getBuyOrder().equals(another.getBuyOrder());
        }
    }
}

