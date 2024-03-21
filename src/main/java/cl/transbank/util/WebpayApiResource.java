package cl.transbank.util;

import cl.transbank.common.ApiConstants;
import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.Options;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.common.WebpayOptions;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

/**
 * This abstract class represents a resource for the Webpay API.
 */
public abstract class WebpayApiResource {

  @Getter
  @Setter
  private static HttpUtil httpUtil = HttpUtilImpl.getInstance();

  /**
   * Builds the headers for a Webpay API request.
   * @param options The options for the request.
   * @return A map of headers for the request.
   */
  public static Map<String, String> buildHeaders(Options options) {
    if (null == options) return Collections.emptyMap();

    Map<String, String> headers = new HashMap<>();
    headers.put(
      ApiConstants.HEADER_COMMERCE_CODE_NAME,
      options.getCommerceCode()
    );
    headers.put(ApiConstants.HEADER_API_KEY_NAME, options.getApiKey());

    return headers;
  }

  /**
   * Executes a Webpay API request.
   * @param endpoint The endpoint for the request.
   * @param method The HTTP method for the request.
   * @param options The options for the request.
   * @param clazz The class of the response.
   * @return The response to the request.
   * @throws TransbankException If an error occurs during the request.
   */
  public static <T> T execute(
    final String endpoint,
    HttpUtil.RequestMethod method,
    final Options options,
    Class<T> clazz
  ) throws TransbankException, IOException {
    return execute(endpoint, method, null, options, clazz);
  }

  /**
   * Executes a Webpay API request with a request body.
   * @param endpoint The endpoint for the request.
   * @param method The HTTP method for the request.
   * @param request The request body.
   * @param options The options for the request.
   * @return The response to the request.
   * @throws TransbankException If an error occurs during the request.
   */
  public static <T> T execute(
    final String endpoint,
    HttpUtil.RequestMethod method,
    final WebpayApiRequest request,
    final Options options
  ) throws TransbankException, IOException {
    return execute(endpoint, method, request, options, null);
  }

  /**
   * Executes a Webpay API request with a request body.
   * @param endpoint The endpoint for the request.
   * @param method The HTTP method for the request.
   * @param request The request body.
   * @param options The options for the request.
   * @param clazz The class of the response.
   * @return The response to the request.
   * @throws TransbankException If an error occurs during the request.
   */
  public static <T> T execute(
    final String endpoint,
    HttpUtil.RequestMethod method,
    final WebpayApiRequest request,
    final Options options,
    Class<T> clazz
  ) throws TransbankException, IOException {
    String urlBase = null;
    if (options instanceof WebpayOptions) {
      urlBase =
        IntegrationTypeHelper.getWebpayIntegrationType(
          options.getIntegrationType()
        );
    } else {
      urlBase =
        IntegrationTypeHelper.getPatpassIntegrationType(
          options.getIntegrationType()
        );
    }
    final URL url = new URL(String.format("%s/%s", urlBase, endpoint));

    final T out = WebpayApiResource
      .getHttpUtil()
      .request(
        url,
        method,
        request,
        WebpayApiResource.buildHeaders(options),
        clazz
      );

    if (null == out) return null;

    if (null == clazz) return null;

    return out;
  }

  /**
   * Executes a Webpay API request and returns a list of responses.
   * @param endpoint The endpoint for the request.
   * @param method The HTTP method for the request.
   * @param options The options for the request.
   * @param clazz The class of the response.
   * @return A list of responses to the request.
   * @throws TransbankException If an error occurs during the request.
   */
  public static <T> List<T> executeToList(
    final String endpoint,
    HttpUtil.RequestMethod method,
    final Options options,
    Class<T[]> clazz
  ) throws TransbankException, IOException {
    return executeToList(endpoint, method, null, options, clazz);
  }

  /**
   * Executes a Webpay API request with a request body and returns a list of responses.
   * @param endpoint The endpoint for the request.
   * @param method The HTTP method for the request.
   * @param request The request body.
   * @param options The options for the request.
   * @param clazz The class of the response.
   * @return A list of responses to the request.
   * @throws TransbankException If an error occurs during the request.
   */
  public static <T> List<T> executeToList(
    final String endpoint,
    HttpUtil.RequestMethod method,
    final WebpayApiRequest request,
    final Options options,
    Class<T[]> clazz
  ) throws TransbankException, IOException {
    String urlBase = null;
    if (options instanceof WebpayOptions) {
      urlBase =
        IntegrationTypeHelper.getWebpayIntegrationType(
          options.getIntegrationType()
        );
    } else {
      urlBase =
        IntegrationTypeHelper.getPatpassIntegrationType(
          options.getIntegrationType()
        );
    }
    final URL url = new URL(String.format("%s/%s", urlBase, endpoint));

    final List<T> out = WebpayApiResource
      .getHttpUtil()
      .requestList(
        url,
        method,
        request,
        WebpayApiResource.buildHeaders(options),
        clazz
      );

    if (null == out) return Collections.emptyList();

    if (null == clazz) return Collections.emptyList();

    return out;
  }
}
