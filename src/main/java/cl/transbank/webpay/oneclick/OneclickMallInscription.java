package cl.transbank.webpay.oneclick;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.BaseTransaction;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.ValidationUtil;
import cl.transbank.util.WebpayApiResource;
import cl.transbank.webpay.exception.InscriptionDeleteException;
import cl.transbank.webpay.exception.InscriptionFinishException;
import cl.transbank.webpay.exception.InscriptionStartException;
import cl.transbank.webpay.oneclick.requests.InscriptionDeleteRequest;
import cl.transbank.webpay.oneclick.requests.InscriptionStartRequest;
import cl.transbank.webpay.oneclick.responses.OneclickMallInscriptionFinishResponse;
import cl.transbank.webpay.oneclick.responses.OneclickMallInscriptionStartResponse;
import java.io.IOException;

/**
 * This abstract class represents the OneclickMallInscription and provides methods to handle Oneclick Mall Inscriptions.
 */
abstract class OneclickMallInscription extends BaseTransaction {

  /**
   * This abstract class represents the OneclickMallInscription and provides methods to handle Oneclick Mall Inscriptions.
   */
  public OneclickMallInscription(Options options) {
    this.options = options;
  }

  /**
   * Starts a new Oneclick Mall Inscription.
   * @param username The username of the user.
   * @param email The email of the user.
   * @param responseUrl The URL to redirect the user to after the inscription is completed.
   * @return The response from the start inscription request.
   * @throws IOException If there is an error during the execution of the request.
   * @throws InscriptionStartException If there is an error during the start of the inscription.
   */
  public OneclickMallInscriptionStartResponse start(
    String username,
    String email,
    String responseUrl
  ) throws IOException, InscriptionStartException {
    ValidationUtil.hasTextTrimWithMaxLength(
      username,
      ApiConstants.USER_NAME_LENGTH,
      "username"
    );
    ValidationUtil.hasTextTrimWithMaxLength(
      email,
      ApiConstants.EMAIL_LENGTH,
      "email"
    );
    ValidationUtil.hasTextWithMaxLength(
      responseUrl,
      ApiConstants.RETURN_URL_LENGTH,
      "responseUrl"
    );

    final WebpayApiRequest request = new InscriptionStartRequest(
      username,
      email,
      responseUrl
    );
    String endpoint = String.format(
      "%s/inscriptions",
      ApiConstants.ONECLICK_ENDPOINT
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.POST,
        request,
        options,
        OneclickMallInscriptionStartResponse.class
      );
    } catch (TransbankException e) {
      throw new InscriptionStartException(e);
    }
  }

  public OneclickMallInscriptionFinishResponse finish(String token)
    throws IOException, InscriptionFinishException {
    ValidationUtil.hasTextWithMaxLength(
      token,
      ApiConstants.TOKEN_LENGTH,
      "token"
    );
    String endpoint = String.format(
      "%s/inscriptions/%s",
      ApiConstants.ONECLICK_ENDPOINT,
      token
    );
    try {
      return WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.PUT,
        options,
        OneclickMallInscriptionFinishResponse.class
      );
    } catch (TransbankException e) {
      throw new InscriptionFinishException(e);
    }
  }

  public void delete(String tbkUser, String username)
    throws IOException, InscriptionDeleteException {
    ValidationUtil.hasTextTrimWithMaxLength(
      username,
      ApiConstants.USER_NAME_LENGTH,
      "username"
    );
    ValidationUtil.hasTextWithMaxLength(
      tbkUser,
      ApiConstants.TBK_USER_LENGTH,
      "tbkUser"
    );
    WebpayApiRequest request = new InscriptionDeleteRequest(username, tbkUser);
    String endpoint = String.format(
      "%s/inscriptions",
      ApiConstants.ONECLICK_ENDPOINT
    );
    try {
      WebpayApiResource.execute(
        endpoint,
        HttpUtil.RequestMethod.DELETE,
        request,
        options
      );
    } catch (TransbankException e) {
      throw new InscriptionDeleteException(e);
    }
  }
}
