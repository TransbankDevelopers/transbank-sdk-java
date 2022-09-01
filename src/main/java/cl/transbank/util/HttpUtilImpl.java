package cl.transbank.util;

import cl.transbank.webpay.exception.TransbankHttpApiException;
import cl.transbank.webpay.exception.WebpayException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

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

import static cl.transbank.util.HttpUtil.RequestMethod.*;

public class HttpUtilImpl implements HttpUtil {
    private static Logger logger = Logger.getLogger(HttpUtilImpl.class.getName());

    private static volatile HttpUtilImpl instance;

    @Setter @Getter(AccessLevel.PRIVATE) private JsonUtil jsonUtil = JsonUtilImpl.getInstance();

    public <T> T request(@NonNull URL url, RequestMethod method, Object request, Map<String, String> headers,
                         Class<T> clazz) throws IOException, WebpayException {
        final String jsonIn = getJsonUtil().jsonEncode(request);
        final String jsonOut = request(url, method, jsonIn, headers, true);
        return getJsonUtil().jsonDecode(jsonOut, clazz);
    }

    public <T> List<T> requestList(@NonNull URL url, RequestMethod method, Object request, Map<String, String> headers,
                                   Class<T[]> clazz) throws IOException, WebpayException {
        final String jsonIn = getJsonUtil().jsonEncode(request);
        final String jsonOut = request(url, method, jsonIn, headers, true);
        return getJsonUtil().jsonDecodeToList(jsonOut, clazz);
    }

    public String request(@NonNull URL url, RequestMethod method, String query)
            throws IOException, WebpayException {
        return request(url, method, query, (ContentType) null, (Map<String, String>) null);
    }

    public String request(@NonNull URL url, RequestMethod method, String query,
                                 ContentType contentType) throws IOException, WebpayException {
        return request(url, method, query, contentType, null);
    }

    public String request(@NonNull URL url, RequestMethod method, String query, Map<String, String> headers)
            throws IOException, WebpayException {
        return request(url, method, query, null, headers);
    }

    public String request(@NonNull URL url, RequestMethod method, String query, Map<String, String> headers, boolean useException)
            throws IOException, WebpayException {
        return request(url, method, query, null, headers);
    }

    public String request(@NonNull URL url, RequestMethod method, String query,
                          ContentType contentType, Map<String, String> headers) throws IOException, WebpayException {
        if (null == method)
            method = GET;

        if (null == contentType)
            contentType = ContentType.JSON;

        HttpURLConnection conn = null;

        try {
            logger.log(Level.FINE, String.format("HTTP URL : %s", url));
            logger.log(Level.FINE, String.format("HTTP Method : %s", method));

            if (null != headers) {
                for (String key : headers.keySet()) {
                    if (!StringUtils.isEmpty(key)) {
                        String value = headers.get(key);

                        if (key.equalsIgnoreCase("Tbk-Api-Key-Secret")) {
                            value = "NOT DISPLAYED BY SECURITY REASON";
                        }

                        logger.log(Level.FINE, String.format("HTTP Header [%s] : %s", key, value));
                    }
                }
            }

            logger.log(Level.FINE, String.format("HTTP Request Query : %s", query));
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
                default:
                    conn = createGETConnection(url, query, headers);
            }

            int responseCode = conn.getResponseCode();

            logger.log(Level.FINE, String.format("HTTP Response Code : %s", responseCode));
            final boolean isHttpErrorCode = !(responseCode >= 200 && responseCode < 300);
            InputStream input = !isHttpErrorCode ?
                    conn.getInputStream() :
                    conn.getErrorStream();

            final String responseBody = getResponseBody(input);
            if (isHttpErrorCode) {
                Object errorMessage = "Could not obtain a response message from Webpay API";
                if (responseBody != null) {
                    final Map errorMap = getJsonUtil().jsonDecode(responseBody, HashMap.class);
                    errorMessage = errorMap.get("error_message");
                }

                if (null == errorMessage)
                    errorMessage = "Unspecified message by Webpay API";
                throw new TransbankHttpApiException(responseCode, errorMessage.toString());
            }

            if (responseBody != null && !responseBody.trim().startsWith("[")) {
                final Map tempMap = getJsonUtil().jsonDecode(responseBody, HashMap.class);
                if (tempMap.containsKey("error_message") && tempMap.get("error_message")!=null){
                    throw new WebpayException(tempMap.get("error_message").toString());
                }
            }

            return responseBody;
        } finally {
            if (null != conn)
                conn.disconnect();
        }
    }

    private HttpURLConnection createPOSTConnection(URL url, String query, ContentType contentType, Map<String, String> headers)
            throws IOException {
        return createSendingDataConnection(POST, url, query, contentType, headers);
    }

    private HttpURLConnection createGETConnection(URL url, String query, Map<String, String> headers) throws IOException {
        String getUrl = formatUrl(url.toString(), query);
        HttpURLConnection conn = (HttpURLConnection) new URL(getUrl).openConnection();
        conn.setRequestMethod(GET.toString());

        if (null != headers) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        return conn;
    }

    private HttpURLConnection createDeleteConnection(URL url, String query, ContentType contentType, Map<String, String> headers) throws IOException {
        return createSendingDataConnection(DELETE, url, query, contentType, headers);
    }

    private HttpURLConnection createPUTConnection(
            URL url, String query, ContentType contentType, Map<String, String> headers) throws IOException {
        return createSendingDataConnection(PUT, url, query, contentType, headers);
    }

    private HttpURLConnection createSendingDataConnection(
            RequestMethod method, URL url, String query, ContentType contentType, Map<String, String> headers) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod(method.toString());
        conn.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", String.format(
                "%s;charset=%s", contentType.getContentType(), StandardCharsets.UTF_8.name().toLowerCase()));

        if (null != headers) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                conn.setRequestProperty(header.getKey(), header.getValue());
            }
        }

        try (OutputStream out = conn.getOutputStream()) {
            out.write(query.getBytes(StandardCharsets.UTF_8));
        }

        return conn;
    }

    private String formatUrl(String url, String query) {
        if (null == query || query.trim().isEmpty())
            return url;

        String separator = url.contains("?") ? "&" : "?";
        return String.format("%s%s%s", url, separator, query);
    }

    private static String getResponseBody(InputStream responseStream) {
        try (final Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name())) {
            final String responseBody = scanner.useDelimiter("\\A").next();
            responseStream.close();

            logger.log(Level.FINE, String.format("HTTP Response Body : %s", responseBody));
            return responseBody;
        } catch (Exception e) {
            return null;
        }
    }

    private HttpUtilImpl() {
        super();
    }

    public static HttpUtilImpl getInstance() {
        if (null == instance)
            synchronized (HttpUtilImpl.class) {
                instance = new HttpUtilImpl();
            }

        return instance;
    }
}
