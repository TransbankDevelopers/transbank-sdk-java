package cl.transbank.webpay.oneclick;

import cl.transbank.model.WebpayApiRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter @Setter @ToString
class TransactionCaptureRequest extends WebpayApiRequest {
    //TODO VALIDATE REQUEST FOR PROPERTIES WHEN WE HAVE DOC
}
