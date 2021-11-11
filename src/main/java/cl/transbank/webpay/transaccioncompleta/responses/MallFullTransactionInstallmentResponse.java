package cl.transbank.webpay.transaccioncompleta.responses;

import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class MallFullTransactionInstallmentResponse extends FullTransactionInstallmentResponse {
    MallFullTransactionInstallmentResponse(double installmentsAmount, Long idQueryInstallments, List<DeferredPeriod> deferredPeriods){
        super(installmentsAmount, idQueryInstallments, deferredPeriods);
    }
}
