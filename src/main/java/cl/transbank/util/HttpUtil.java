package cl.transbank.util;

import cl.transbank.webpay.exception.WebpayException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * This interface provides methods for HTTP requests.
 */
public interface HttpUtil {
  <T> T request(
    @NonNull URL url,
    RequestMethod method,
    Object request,
    Map<String, String> headers,
    Class<T> clazz
  ) throws IOException, WebpayException;
  <T> List<T> requestList(
    @NonNull URL url,
    RequestMethod method,
    Object request,
    Map<String, String> headers,
    Class<T[]> clazz
  ) throws IOException, WebpayException;
  String request(URL url, HttpUtilImpl.RequestMethod method, String query)
    throws IOException, WebpayException;
  String request(
    URL url,
    HttpUtilImpl.RequestMethod method,
    String query,
    HttpUtil.ContentType contentType
  ) throws IOException, WebpayException;
  String request(
    @NonNull URL url,
    RequestMethod method,
    String query,
    Map<String, String> headers
  ) throws IOException, WebpayException;
  String request(
    @NonNull URL url,
    RequestMethod method,
    String query,
    ContentType contentType,
    Map<String, String> headers
  ) throws IOException, WebpayException;

  @AllArgsConstructor
  enum ContentType {
    JSON("application/json");

    @Getter
    private String contentType;
  }

  enum RequestMethod {
    GET,
    POST,
    DELETE,
    PUT,
  }
}
