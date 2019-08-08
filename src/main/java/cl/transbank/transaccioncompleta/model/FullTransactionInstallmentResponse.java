package cl.transbank.transaccioncompleta.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FullTransactionInstallmentResponse {
    private String installmentsAmount;
    private byte idQueryInstallments;
    private List<DeferredPeriod> deferredPeriods;

    @NoArgsConstructor @AllArgsConstructor
    @Getter @Setter @ToString
    public class DeferredPeriod {
        private double amount;
        private byte period;
    }
}
