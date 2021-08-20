package cl.transbank.transaccioncompleta.model;

import cl.transbank.transaccioncompleta.TransactionCommitRequest;
import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MallTransactionCommitDetails {
    private List<MallTransactionCommitDetails.Detail> detailList = new ArrayList<>();

    private MallTransactionCommitDetails() {}

    public static MallTransactionCommitDetails build() {
        return new MallTransactionCommitDetails();
    }

    public static MallTransactionCommitDetails build(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex, boolean gracePeriod) {
        return MallTransactionCommitDetails.build().add(commerceCode, buyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod);
    }

    public MallTransactionCommitDetails add(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex, boolean gracePeriod) {
        detailList.add(new MallTransactionCommitDetails.Detail(commerceCode, buyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod));
        return this;
    }

    public boolean remove(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex, boolean gracePeriod) {
        return getDetails().remove(new MallTransactionCommitDetails.Detail(commerceCode, buyOrder, idQueryInstallments, deferredPeriodIndex, gracePeriod));
    }

    public List<MallTransactionCommitDetails.Detail> getDetails() {
        return Collections.unmodifiableList(detailList);
    }

    @Data
    @EqualsAndHashCode(callSuper=true)
    @ToString(callSuper=true)
    @AllArgsConstructor
    public class Detail extends TransactionCommitRequest {
        @SerializedName("commerce_code")private String commerceCode;
        @SerializedName("buy_order")private String buyOrder;

        Detail(String commerceCode, String buyOrder,long idQueryInstallments,byte deferredPeriodIndex,boolean gracePeriod){
            super(idQueryInstallments, deferredPeriodIndex, gracePeriod);
            this.commerceCode = commerceCode;
            this.buyOrder = buyOrder;
        }

    }
}
