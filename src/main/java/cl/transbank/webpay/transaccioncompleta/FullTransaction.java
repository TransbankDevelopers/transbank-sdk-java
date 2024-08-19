package cl.transbank.webpay.transaccioncompleta;

import cl.transbank.common.*;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.TransactionCaptureRequest;
import cl.transbank.webpay.common.TransactionRefundRequest;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.transaccioncompleta.requests.*;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import java.io.IOException;

/**
 * The FullTransaction class extends the BaseTransaction class and provides methods for creating, committing,
 * checking the status of, refunding, and capturing transactions. It also provides methods for creating installments
 * for a transaction. This class also provides methods for configuring the transaction for different environments
 * such as integration, production, testing, and testing deferred.
 *
 */
public class FullTransaction extends BaseTransaction {

  /**
   * Constructor with options. Uses provided options.
   * @param options The options to use for this transaction.
   */
  public FullTransaction(Options options) {
    super(options);
  }

  /**
   * Creates a new FullTransaction.
   * @param buyOrder The buy order.
   * @param sessionId The session id.
   * @param amount The amount.
   * @param cvv The cvv of the card.
   * @param cardNumber The number of the card.
   * @param cardExpirationDate The expiration date of the card.
   * @return The response of the FullTransaction creation.
   * @throws IOException If there's an error in the communication with the server.
   * @throws TransactionCreateException If there's an error in the creation of the transaction.
   */
  public FullTransactionCreateResponse create(
    String buyOrder,
    String sessionId,
    double amount,
    short cvv,
    String cardNumber,
    String cardExpirationDate
  ) throws IOException, TransactionCreateException {
    ValidationUtil.hasTextWithMaxLength(
      buyOrder,
      ApiConstants.BUY_ORDER_LENGTH,
      "buyOrder"
    );
    ValidationUtil.hasTextWithMaxLength(
      sessionId,
      ApiConstants.SESSION_ID_LENGTH,
      "sessionId"
    );
    ValidationUtil.hasTextWithMaxLength(
      cardNumber,
      ApiConstants.CARD_NUMBER_LENGTH,
      "cardNumber"
    );
    ValidationUtil.hasTextWithMaxLength(
      cardExpirationDate,
      ApiConstants.CARD_EXPIRATION_DATE_LENGTH,
      "cardExpirationDate"
    );

    String endpoint = String.format(
      "%s/transactions",
      ApiConstants.WEBPAY_ENDPOINT
    );
    final WebpayApiRequest request = new TransactionCreateRequest(
      buyOrder,
      sessionId,
      amount,
      cardNumber,
      cvv,
      cardExpirationDate
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.POST,
        request,
        options,
        FullTransactionCreateResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionCreateException(e);
    }
  }

  /**
   * Starts the process of creating installments for the transaction.
   * @param token The token of the transaction.
   * @param installmentsNumber The number of installments.
   * @return The response of the installments creation process.
   * @throws TransactionInstallmentException If there's an error in the creation of the installments.
   * @throws IOException If there's an error in the communication with the server.
   */
  public FullTransactionInstallmentResponse installments(
    String token,
    byte installmentsNumber
  ) throws IOException, TransactionInstallmentException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      ApiConstants.TOKEN_TEXT
    );
    String endpoint = String.format(
      "%s/transactions/%s/installments",
      ApiConstants.WEBPAY_ENDPOINT,
      token
    );
    final WebpayApiRequest request = new TransactionInstallmentsRequest(
      installmentsNumber
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.POST,
        request,
        options,
        FullTransactionInstallmentResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionInstallmentException(e);
    }
  }

  /**
   * Commits the transaction.
   * @param token The token of the transaction.
   * @param idQueryInstallments The id of the query installments.
   * @param deferredPeriodIndex The index of the deferred period.
   * @param gracePeriod The grace period.
   * @return The response of the commit process.
   * @throws IOException If there's an error in the communication with the server.
   * @throws TransactionCommitException If there's an error in the commit process.
   */
  public FullTransactionCommitResponse commit(
    String token,
    Long idQueryInstallments,
    Byte deferredPeriodIndex,
    Boolean gracePeriod
  ) throws IOException, TransactionCommitException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      ApiConstants.TOKEN_TEXT
    );
    String endpoint = String.format(
      "%s/transactions/%s",
      ApiConstants.WEBPAY_ENDPOINT,
      token
    );
    final WebpayApiRequest request = new TransactionCommitRequest(
      idQueryInstallments,
      deferredPeriodIndex,
      gracePeriod
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.PUT,
        request,
        options,
        FullTransactionCommitResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionCommitException(e);
    }
  }

  /**
   * Gets the status of the transaction.
   * @param token The token of the transaction.
   * @return The status of the transaction.
   * @throws IOException If there's an error in the communication with the server.
   * @throws TransactionStatusException If there's an error in the status process.
   */
  public FullTransactionStatusResponse status(String token)
    throws IOException, TransactionStatusException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      ApiConstants.TOKEN_TEXT
    );
    String endpoint = String.format(
      "%s/transactions/%s",
      ApiConstants.WEBPAY_ENDPOINT,
      token
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.GET,
        options,
        FullTransactionStatusResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionStatusException(e);
    }
  }

  /**
   * Starts the process of refunding the transaction.
   * @param token The token of the transaction.
   * @param amount The amount to be refunded.
   * @return The response of the refund process.
   * @throws IOException If there's an error in the communication with the server.
   * @throws TransactionRefundException If there's an error in the refund process.
   */
  public FullTransactionRefundResponse refund(String token, double amount)
    throws IOException, TransactionRefundException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      ApiConstants.TOKEN_TEXT
    );
    String endpoint = String.format(
      "%s/transactions/%s/refunds",
      ApiConstants.WEBPAY_ENDPOINT,
      token
    );
    final WebpayApiRequest request = new TransactionRefundRequest(amount);
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.POST,
        request,
        options,
        FullTransactionRefundResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionRefundException(e);
    }
  }

  /**
   * Captures the transaction.
   * @param token The token of the transaction.
   * @param buyOrder The buy order of the transaction.
   * @param authorizationCode The authorization code of the transaction.
   * @param captureAmount The amount to be captured.
   * @return The response of the capture process.
   * @throws IOException If there's an error in the communication with the server.
   * @throws TransactionCaptureException If there's an error in the capture process.
   */
  public FullTransactionCaptureResponse capture(
    String token,
    String buyOrder,
    String authorizationCode,
    double captureAmount
  ) throws IOException, TransactionCaptureException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      ApiConstants.TOKEN_TEXT
    );
    ValidationUtil.hasTextWithMaxLength(
      buyOrder,
      ApiConstants.BUY_ORDER_LENGTH,
      "buyOrder"
    );
    ValidationUtil.hasTextWithMaxLength(
      authorizationCode,
      ApiConstants.AUTHORIZATION_CODE_LENGTH,
      "authorizationCode"
    );

    String endpoint = String.format(
      "%s/transactions/%s/capture",
      ApiConstants.WEBPAY_ENDPOINT,
      token
    );
    final WebpayApiRequest request = new TransactionCaptureRequest(
      buyOrder,
      authorizationCode,
      captureAmount
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.PUT,
        request,
        options,
        FullTransactionCaptureResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionCaptureException(e);
    }
  }

}
