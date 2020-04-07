package cl.transbank.webpay.exception;

public class InvalidAmountException extends WebpayException {

    public static final String DEFAULT_MESSAGE = "Invalid amount given.";
    public static final String HAS_DECIMALS_MESSAGE = "Given amount has decimals. Webpay only accepts integer amounts. Please remove decimal places.";

    public InvalidAmountException() { super(InvalidAmountException.DEFAULT_MESSAGE); }

    public InvalidAmountException(Exception e) {
        super(e);
    }

    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(int code, String message) {
        super(code, message);
    }
}
