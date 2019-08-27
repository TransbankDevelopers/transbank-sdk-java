package cl.transbank.transaccioncompleta.model;

import cl.transbank.transaccioncompleta.MallFullTransactionInstallmentRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MallFullTransactionInstallmentsDetails {
    private List<MallFullTransactionInstallmentsDetails.Detail> detailList = new ArrayList<>();

    private MallFullTransactionInstallmentsDetails() {}

    public static MallFullTransactionInstallmentsDetails build() {
        return new MallFullTransactionInstallmentsDetails();
    }

    public static MallFullTransactionInstallmentsDetails build(String commerceCode, String buyOrder,byte installmentsNumber) {
        return MallFullTransactionInstallmentsDetails.build().add(commerceCode, buyOrder, installmentsNumber);
    }

    public MallFullTransactionInstallmentsDetails add(String commerceCode, String buyOrder,byte installmentsNumber) {
        detailList.add(new MallFullTransactionInstallmentsDetails.Detail(commerceCode, buyOrder, installmentsNumber));
        return this;
    }

    public boolean remove(String commerceCode, String buyOrder,byte installmentsNumber) {
        return getDetails().remove(new MallFullTransactionInstallmentsDetails.Detail(commerceCode, buyOrder, installmentsNumber));
    }

    public List<MallFullTransactionInstallmentsDetails.Detail> getDetails() {
        return Collections.unmodifiableList(detailList);
    }


    public class Detail extends MallFullTransactionInstallmentRequest {
        Detail(String commerceCode, String buyOrder,byte installmentsNumber){
            super(commerceCode, buyOrder, installmentsNumber);
        }
    }
}
