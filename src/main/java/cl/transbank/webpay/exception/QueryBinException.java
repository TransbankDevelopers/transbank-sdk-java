package cl.transbank.webpay.exception;

/**
 * This class represents an exception that is thrown when a query bin operation
 * fails.
 */
public class QueryBinException extends WebpayException {

    /**
     * Constructs a new QueryBinException with no detail message.
     */
    public QueryBinException() {
        super();
    }

    /**
     * Constructs a new QueryBinException with the specified cause.
     * 
     * @param e The cause of the exception.
     */
    public QueryBinException(Exception e) {
        super(e);
    }

    /**
     * Constructs a new QueryBinException with the specified detail message.
     * 
     * @param message The detail message.
     */
    public QueryBinException(String message) {
        super(message);
    }
}
