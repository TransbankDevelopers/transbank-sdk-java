package cl.transbank.webpay.transaccioncompleta.responses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MallFullTransactionInstallmentsResponse {
    private List<MallFullTransactionInstallmentResponse> responseList = new ArrayList<>();

    private MallFullTransactionInstallmentsResponse() {}

    public static MallFullTransactionInstallmentsResponse build() {
        return new MallFullTransactionInstallmentsResponse();
    }

    public static MallFullTransactionInstallmentsResponse build(MallFullTransactionInstallmentResponse response) {
        return MallFullTransactionInstallmentsResponse.build().add(response);
    }

    public MallFullTransactionInstallmentsResponse add(MallFullTransactionInstallmentResponse response) {
        responseList.add(response);
        return this;
    }

    public boolean remove(MallFullTransactionInstallmentResponse response) {
        return getResponseList().remove(response);
    }

    public List<MallFullTransactionInstallmentResponse> getResponseList() {
        return Collections.unmodifiableList(responseList);
    }

}
