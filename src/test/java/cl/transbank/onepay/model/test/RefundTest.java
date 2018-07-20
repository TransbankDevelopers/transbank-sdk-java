package cl.transbank.onepay.model.test;

import cl.transbank.onepay.exception.TransbankException;
import cl.transbank.onepay.model.Options;
import cl.transbank.onepay.model.Refund;
import cl.transbank.onepay.model.RefundCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RefundTest {
    private Logger log = LoggerFactory.getLogger(RefundTest.class);

    public void testRefund() throws IOException, TransbankException {
        // Setting comerce data
        Options options = new Options()
                .setApiKey("mUc0GxYGor6X8u-_oB3e-HWJulRG01WoC96-_tUA3Bg")
                .setSharedSecret("P4DCPS55QB2QLT56SQH6#W#LV76IAPYX");

        RefundCreateResponse response = Refund.create(27500, "1807983490979289", "f506a955-800c-4185-8818-4ef9fca97aae",
                "623245", options);

        log.debug(response.toString());
    }

    public static void main(String[] args) throws TransbankException, IOException {
        new RefundTest().testRefund();
    }
}
