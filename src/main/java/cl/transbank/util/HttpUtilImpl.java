package cl.transbank.util;

import static cl.transbank.util.HttpUtil.RequestMethod.*;

import cl.transbank.webpay.exception.TransbankHttpApiException;
import cl.transbank.webpay.exception.WebpayException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * This class provides utility methods for HTTP requests.
 */
public class HttpUtilImpl implements HttpUtil {

  private static Logger logger = Logger.getLogger(HttpUtilImpl.class.getName());

  private static volatile HttpUtilImpl instance;

  @Setter
  @Getter(AccessLevel.PRIVATE)
  private JsonUtil jsonUtil = JsonUtilImpl.getInstance();

  /**
   * Sends a HTTP request and returns the response.
   * This method uses the provided URL, request method, request body, headers, and response type to send the request.
   */
  public <T> T request(
    @NonNull URL url,
    RequestMethod method,
    Object request,
    Map<String, String> headers,
    Class<T> clazz
  ) throws IOException, WebpayException {
    final String jsonIn = getJsonUtil().jsonEncode(request);
    final String jsonOut = request(url, method, jsonIn, headers, true);
    return getJsonUtil().jsonDecode(jsonOut, clazz);
  }

  public <T> List<T> requestList(
    @NonNull URL url,
    RequestMethod method,
    Object request,
    Map<String, String> headers,
    Class<T[]> clazz
  ) throws IOException, WebpayException {
    final String jsonIn = getJsonUtil().jsonEncode(request);
    final String jsonOut = request(url, method, jsonIn, headers, true);
    return getJsonUtil().jsonDecodeToList(jsonOut, clazz);
  }

  /**
   * Sends a HTTP request and returns the response.
   * This method uses the provided URL, request method, request body, and response type to send the request.
   * It uses default headers.
   */
  public String request(@NonNull URL url, RequestMethod method, String query)
    throws IOException, WebpayException {
    return request(
      url,
      method,
      query,
      (ContentType) null,
      (Map<String, String>) null
    );
  }

  /**
   * Sends a HTTP request and returns the response.
   * This method uses the provided URL, request method, and response type to send the request.
   * It uses default headers and does not send a request body.
   */
  public String request(
    @NonNull URL url,
    RequestMethod method,
    String query,
    ContentType contentType
  ) throws IOException, WebpayException {
    return request(url, method, query, contentType, null);
  }

  /**
   * Sends a HTTP request and returns the response.
   * This method uses the provided URL, request method, and response type to send the request.
   * It uses default headers and a default request body.
   */
  public String request(
    @NonNull URL url,
    RequestMethod method,
    String query,
    Map<String, String> headers
  ) throws IOException, WebpayException {
    return request(url, method, query, null, headers);
  }

  /**
   * Sends a HTTP request and returns the response.
   * This method uses the provided URL and request method to send the request.
   * It uses default headers, a default response type, and does not send a request body.
   */
  public String request(
    @NonNull URL url,
    RequestMethod method,
    String query,
    Map<String, String> headers,
    boolean useException
  ) throws IOException, WebpayException {
    return request(url, method, query, null, headers);
  }

  /**
   * Sends a HTTP request and returns the response.
   * This method uses the provided URL to send the request.
   * It uses a default request method, default headers, a default response type, and does not send a request body.
   */
  public String request(
    @NonNull URL url,
    RequestMethod method,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException, WebpayException {
    if (null == method) method = GET;
    if (null == contentType) contentType = ContentType.JSON;

    logRequestDetails(url, method, headers, query);

    HttpURLConnection conn = null;
    int responseCode = 0;
    String responseBody = "";
    try {
      conn = createConnection(url, method, query, contentType, headers);
      responseCode = conn.getResponseCode();

      logger.log(Level.FINE, "HTTP Response Code : {0}", responseCode);

      final boolean isHttpErrorCode =
        !(responseCode >= 200 && responseCode < 300);
      InputStream input = !isHttpErrorCode
        ? conn.getInputStream()
        : conn.getErrorStream();

      responseBody = getResponseBody(input);
      handleResponse(responseCode, responseBody, isHttpErrorCode);
    } catch (SSLException e) {
      throw new IOException("SSL error", e);
    }

    return responseBody;
  }

  private void logRequestDetails(
    URL url,
    RequestMethod method,
    Map<String, String> headers,
    String query
  ) {
    logger.log(Level.FINE, "HTTP URL : {0}", url);
    logger.log(Level.FINE, "HTTP Method : {0}", method);

    if (null != headers) {
      for (String key : headers.keySet()) {
        if (!StringUtils.isEmpty(key)) {
          String value = headers.get(key);

          if (key.equalsIgnoreCase("Tbk-Api-Key-Secret")) {
            value = "NOT DISPLAYED BY SECURITY REASON";
          }

          logger.log(
            Level.FINE,
            "HTTP Header [{0}] : {1}",
            new Object[] { key, value }
          );
        }
      }
    }

    logger.log(Level.FINE, "HTTP Request Query : {0}", query);
  }

  private HttpURLConnection createConnection(
    URL url,
    RequestMethod method,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException {
    HttpURLConnection conn;
    switch (method) {
      case POST:
        conn = createPOSTConnection(url, query, contentType, headers);
        break;
      case DELETE:
        conn = createDeleteConnection(url, query, contentType, headers);
        break;
      case PUT:
        conn = createPUTConnection(url, query, contentType, headers);
        break;
      case GET:
        conn = createGETConnection(url, query, headers);
        break;
      default:
        conn = createGETConnection(url, query, headers);
        break;
    }
    return conn;
  }

  private void handleResponse(
    int responseCode,
    String responseBody,
    boolean isHttpErrorCode
  ) throws WebpayException {
    if (isHttpErrorCode) {
      Object errorMessage =
        "Could not obtain a response message from Webpay API";
      if (responseBody != null) {
        @SuppressWarnings("unchecked")
        final Map<String, Object> errorMap = (Map<String, Object>) getJsonUtil()
          .jsonDecode(responseBody, HashMap.class);

        errorMessage = errorMap.get("error_message");
      }

      if (null == errorMessage) errorMessage =
        "Unspecified message by Webpay API";
      throw new TransbankHttpApiException(
        responseCode,
        errorMessage.toString()
      );
    }

    if (responseBody != null && !responseBody.trim().startsWith("[")) {
      final Map tempMap = getJsonUtil().jsonDecode(responseBody, HashMap.class);
      if (
        tempMap.containsKey("error_message") &&
        tempMap.get("error_message") != null
      ) {
        throw new WebpayException(tempMap.get("error_message").toString());
      }
    }
  }

  private HttpURLConnection createPOSTConnection(
    URL url,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException {
    return createSendingDataConnection(POST, url, query, contentType, headers);
  }

  private HttpURLConnection createGETConnection(
    URL url,
    String query,
    Map<String, String> headers
  ) throws IOException {
    String getUrl = formatUrl(url.toString(), query);
    HttpURLConnection conn = (HttpURLConnection) new URL(getUrl)
      .openConnection();
    conn.setRequestMethod(GET.toString());

    if (null != headers) {
      for (Map.Entry<String, String> header : headers.entrySet()) {
        conn.setRequestProperty(header.getKey(), header.getValue());
      }
    }

    return conn;
  }

  private HttpURLConnection createDeleteConnection(
    URL url,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException {
    return createSendingDataConnection(
      DELETE,
      url,
      query,
      contentType,
      headers
    );
  }

  private HttpURLConnection createPUTConnection(
    URL url,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException {
    return createSendingDataConnection(PUT, url, query, contentType, headers);
  }

  private HttpURLConnection createSendingDataConnection(
    RequestMethod method,
    URL url,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException {
    OutputStream out = null;
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) url.openConnection();
      conn.setUseCaches(false);
      conn.setDoOutput(true);
      conn.setRequestMethod(method.toString());
      conn.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
      conn.setRequestProperty("Accept", "application/json");
      conn.setRequestProperty(
        "Content-Type",
        String.format(
          "%s;charset=%s",
          contentType.getContentType(),
          StandardCharsets.UTF_8.name().toLowerCase()
        )
      );

      if (null != headers) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
          conn.setRequestProperty(header.getKey(), header.getValue());
        }
      }

      out = conn.getOutputStream();
      out.write(query.getBytes(StandardCharsets.UTF_8));
    } catch (SSLException e) {
      throw new IOException("SSL error", e);
    }

    return conn;
  }

  private String formatUrl(String url, String query) {
    if (null == query || query.trim().isEmpty()) return url;

    String separator = url.contains("?") ? "&" : "?";
    return String.format("%s%s%s", url, separator, query);
  }

  private static String getResponseBody(InputStream responseStream) {
    try (
      final Scanner scanner = new Scanner(
        responseStream,
        StandardCharsets.UTF_8.name()
      )
    ) {
      final String responseBody = scanner.useDelimiter("\\A").next();
      responseStream.close();

      return responseBody;
    } catch (Exception e) {
      return null;
    }
  }

  private HttpUtilImpl() {
    super();
  }

  /**
   * Returns the singleton instance of HttpUtilImpl.
   * If the instance does not exist, it is created.
   */
  public static HttpUtilImpl getInstance() {
    if (null == instance) synchronized (HttpUtilImpl.class) {
      instance = new HttpUtilImpl();
    }

    return instance;
  }
}
