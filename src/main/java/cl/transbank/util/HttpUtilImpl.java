package cl.transbank.util;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import static cl.transbank.util.HttpUtil.RequestMethod.*;

public class HttpUtilImpl implements HttpUtil {
    private static volatile HttpUtilImpl instance;

    @Setter @Getter(AccessLevel.PRIVATE) private JsonUtil jsonUtil = JsonUtilImpl.getInstance();

    public <T> T request(@NonNull URL url, RequestMethod method, @NonNull Object request, Map<String, String> headers,
                         Class<T> clazz) throws IOException {
        final String jsonIn = getJsonUtil().jsonEncode(request);
        final String jsonOut = request(url, method, jsonIn, headers);
        return getJsonUtil().jsonDecode(jsonOut, clazz);
    }

    public String request(@NonNull URL url, RequestMethod method, @NonNull String query)
            throws IOException {
        return request(url, method, query, (ContentType) null, (Map<String, String>) null);
    }

    public String request(@NonNull URL url, RequestMethod method, @NonNull String query,
                                 ContentType contentType) throws IOException {
        return request(url, method, query, contentType, null);
    }

    public String request(@NonNull URL url, RequestMethod method, @NonNull String query, Map<String, String> headers)
            throws IOException {
        return request(url, method, query, null, headers);
    }

    public String request(@NonNull URL url, RequestMethod method, @NonNull String query,
                          ContentType contentType, Map<String, String> headers) throws IOException {
        if (null == method)
            method = GET;

        if (null == contentType)
            contentType = ContentType.JSON;

        HttpURLConnection conn = null;

        try {
            switch (method) {
                case POST:
                    conn = createPOSTConnection(url, query, contentType, headers);
                    break;
                case DELETE:
                    conn = createDeleteConnection(url, query);
                    break;
                case PUT:
                    conn = createPUTConnection(url, query);
                    break;
                case GET:
                default:
                    conn = createGETConnection(url, query);
            }

            int responseCode = conn.getResponseCode();

            InputStream input = (responseCode >= 200 && responseCode < 300) ?
                    conn.getInputStream() :
                    conn.getErrorStream();
            return getResponseBody(input);
        } finally {
            if (null != conn)
                conn.disconnect();
        }
    }

    private HttpURLConnection createPOSTConnection(URL url, String query, ContentType contentType, Map<String, String> headers)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod(POST.toString());
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

    private HttpURLConnection createGETConnection(URL url, String query) throws IOException {
        String getUrl = formatUrl(url.toString(), query);
        HttpURLConnection conn = (HttpURLConnection) new URL(getUrl).openConnection();
        conn.setRequestMethod(GET.toString());

        return conn;
    }

    private HttpURLConnection createDeleteConnection(URL url, String query) throws IOException {
        String deleteUrl = formatUrl(url.toString(), query);
        HttpURLConnection conn = (HttpURLConnection) new URL(deleteUrl).openConnection();
        conn.setRequestMethod(DELETE.toString());

        return conn;
    }

    private HttpURLConnection createPUTConnection(URL url, String query) throws IOException {
        String deleteUrl = formatUrl(url.toString(), query);
        HttpURLConnection conn = (HttpURLConnection) new URL(deleteUrl).openConnection();
        conn.setRequestMethod(PUT.toString());
        conn.setDoOutput(true);

        return conn;
    }

    private String formatUrl(String url, String query) {
        if (null == query || query.trim().isEmpty())
            return url;

        String separator = url.contains("?") ? "&" : "?";
        return String.format("%s%s%s", url, separator, query);
    }

    private static String getResponseBody(InputStream responseStream) throws IOException {
        try (final Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name())) {
            final String responseBody = scanner.useDelimiter("\\A").next();
            responseStream.close();
            return responseBody;
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
