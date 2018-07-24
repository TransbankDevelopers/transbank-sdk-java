package cl.transbank.onepay.util;

import cl.transbank.onepay.exception.SignatureException;
import cl.transbank.onepay.model.Signable;
import lombok.NonNull;

public interface SignUtil {
    void sign(@NonNull Signable signable, @NonNull String secret) throws SignatureException;
    boolean validate(@NonNull Signable signable, String secret) throws SignatureException;
}
