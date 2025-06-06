package cl.transbank.webpay.transaccioncompleta;

import cl.transbank.common.*;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.MallTransactionCreateDetails;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.common.MallTransactionCaptureRequest;
import cl.transbank.webpay.common.MallTransactionRefundRequest;
import cl.transbank.webpay.common.WebpayOptions;
import cl.transbank.webpay.exception.*;
import cl.transbank.webpay.transaccioncompleta.model.*;
import cl.transbank.webpay.transaccioncompleta.requests.*;
import cl.transbank.webpay.transaccioncompleta.responses.*;
import java.io.IOException;

/**
 * This class provides methods to configure and perform transactions with the MallFullTransaction service.
 */
public class MallFullTransaction extends BaseTransaction {

  /**
   * Constructor with options. Uses provided options.
   * @param options The options to use for this transaction.
   */
  public MallFullTransaction(Options options) {
    super(options);
  }

  /**
   * Creates and returns an instance of `MallFullTransaction` configured for the integration environment.
   *
   * @param commerceCode The commerce code.
   * @param apiKey The API key used for authentication.
   * @return A new instance of `MallFullTransaction` configured for the test environment (IntegrationType.TEST).
   */
  public static MallFullTransaction buildForIntegration(String commerceCode, String apiKey)
  {
    return new MallFullTransaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.TEST));
  }

  /**
   * Creates and returns an instance of `MallFullTransaction` configured for the production environment.
   *
   * @param commerceCode The commerce code.
   * @param apiKey The API key used for authentication.
   * @return A new instance of `MallFullTransaction` configured for the production environment (IntegrationType.LIVE).
   */
  public static MallFullTransaction buildForProduction(String commerceCode, String apiKey)
  {
    return new MallFullTransaction(new WebpayOptions(commerceCode, apiKey, IntegrationType.LIVE));
  }

  public MallFullTransactionCreateResponse create(
    String buyOrder,
    String sessionId,
    String cardNumber,
    String cardExpirationDate,
    MallTransactionCreateDetails details
  ) throws IOException, TransactionCreateException {
    return create(
      buyOrder,
      sessionId,
      cardNumber,
      cardExpirationDate,
      details,
      null
    );
  }

  public MallFullTransactionCreateResponse create(
    String buyOrder,
    String sessionId,
    String cardNumber,
    String cardExpirationDate,
    MallTransactionCreateDetails details,
    Short cvv
  ) throws IOException, TransactionCreateException {
    ValidationUtil.hasTextWithMaxLength(
      buyOrder,
      ApiConstants.BUY_ORDER_LENGTH,
      ApiConstants.BUY_ORDER_TEXT
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
    ValidationUtil.hasElements(details.getDetails(), "details");

    for (int i = 0; i < details.getDetails().size(); i++) {
      MallTransactionCreateDetails.Detail item = details.getDetails().get(i);
      ValidationUtil.hasTextWithMaxLength(
        item.getCommerceCode(),
        ApiConstants.COMMERCE_CODE_LENGTH,
        "details.commerceCode"
      );
      ValidationUtil.hasTextWithMaxLength(
        item.getBuyOrder(),
        ApiConstants.BUY_ORDER_LENGTH,
        "details.buyOrder"
      );
    }

    String endpoint = String.format(
      "%s/transactions",
      ApiConstants.WEBPAY_ENDPOINT
    );
    final WebpayApiRequest request = new MallFullTransactionCreateRequest(
      buyOrder,
      sessionId,
      cardNumber,
      cardExpirationDate,
      details.getDetails(),
      cvv
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.POST,
        request,
        options,
        MallFullTransactionCreateResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionCreateException(e);
    }
  }

  public MallFullTransactionInstallmentsResponse installments(
    String token,
    MallFullTransactionInstallmentsDetails details
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
    MallFullTransactionInstallmentsResponse response = MallFullTransactionInstallmentsResponse.build();
    for (MallFullTransactionInstallmentsDetails.Detail detail : details.getDetails()) {
      final WebpayApiRequest request = new MallFullTransactionInstallmentsRequest(
        detail.getCommerceCode(),
        detail.getBuyOrder(),
        detail.getInstallmentsNumber()
      );
      try {
        response.add(
          WebpayApiResource.execute(
            endpoint,
            HttpUtil.RequestMethod.POST,
            request,
            options,
            MallFullTransactionInstallmentResponse.class
          )
        );
      } catch (TransbankException e) {
        throw new TransactionInstallmentException(e);
      }
    }
    return response;
  }

  public MallFullTransactionCommitResponse commit(
    String token,
    MallTransactionCommitDetails details
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
    final WebpayApiRequest request = new MallFullTransactionCommitRequest(
      details.getDetails()
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.PUT,
        request,
        options,
        MallFullTransactionCommitResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionCommitException(e);
    }
  }

  public MallFullTransactionStatusResponse status(String token)
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
        MallFullTransactionStatusResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionStatusException(e);
    }
  }

  public MallFullTransactionRefundResponse refund(
    String token,
    String buyOrder,
    String childCommerceCode,
    double amount
  ) throws IOException, TransactionRefundException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      ApiConstants.TOKEN_TEXT
    );
    ValidationUtil.hasTextWithMaxLength(
      childCommerceCode,
      ApiConstants.COMMERCE_CODE_LENGTH,
      "childCommerceCode"
    );
    ValidationUtil.hasTextWithMaxLength(
      buyOrder,
      ApiConstants.BUY_ORDER_LENGTH,
      ApiConstants.BUY_ORDER_TEXT
    );

    String endpoint = String.format(
      "%s/transactions/%s/refunds",
      ApiConstants.WEBPAY_ENDPOINT,
      token
    );
    final WebpayApiRequest request = new MallTransactionRefundRequest(
      buyOrder,
      childCommerceCode,
      amount
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.POST,
        request,
        options,
        MallFullTransactionRefundResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionRefundException(e);
    }
  }

  public MallFullTransactionCaptureResponse capture(
    String token,
    String commerceCode,
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
      commerceCode,
      ApiConstants.COMMERCE_CODE_LENGTH,
      "commerceCode"
    );
    ValidationUtil.hasTextWithMaxLength(
      buyOrder,
      ApiConstants.BUY_ORDER_LENGTH,
      ApiConstants.BUY_ORDER_TEXT
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
    final WebpayApiRequest request = new MallTransactionCaptureRequest(
      commerceCode,
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
        MallFullTransactionCaptureResponse.class
      );
    } catch (TransbankException e) {
      throw new TransactionCaptureException(e);
    }
  }

}
