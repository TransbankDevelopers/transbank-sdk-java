package cl.transbank.onepay.net;

import cl.transbank.onepay.util.JsonUtil;
import cl.transbank.onepay.util.OnepayRequestBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public abstract class Channel {
    protected static OnepayRequestBuilder requestBuilder = OnepayRequestBuilder.getInstance();
    protected static JsonUtil jsonUtil = JsonUtil.getInstance();

    public static  String request(@NonNull URL url, RequestMethod method, @NonNull String query)
            throws IOException {
        return request(url, method, query, null);
    }

    public static String request(@NonNull URL url, RequestMethod method, @NonNull String query,
                                 ContentType contentType) throws IOException {
        if (null == method)
            method = RequestMethod.GET;

        if (null == contentType)
            contentType = ContentType.JSON;

        HttpURLConnection conn = null;
        try {
            conn = (method == RequestMethod.GET) ?
                    createGETConnection(url, query, contentType) :
                    createPOSTConnection(url, query, contentType);

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

    private static String getResponseBody(InputStream responseStream) throws IOException {
        try (final Scanner scanner = new Scanner(responseStream, StandardCharsets.UTF_8.name())) {
            final String responseBody = scanner.useDelimiter("\\A").next();
            responseStream.close();
            return responseBody;
        }
    }

    private static HttpURLConnection createPOSTConnection(URL url, String query, ContentType contentType)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", String.format(
                "%s;charset=%s", contentType.getContentType(), StandardCharsets.UTF_8.name().toLowerCase()));

        OutputStream out = null;
        try {
            out = conn.getOutputStream();
            out.write(query.getBytes(StandardCharsets.UTF_8));
        } finally {
            if (null != out)
                out.close();
        }

        return conn;
    }

    private static HttpURLConnection createGETConnection(URL url, String query, ContentType contentType) {
        // TODO implement this method if you need it
        return null;
    }

    public enum RequestMethod {
        GET,
        POST
    }

    @AllArgsConstructor public enum ContentType {
        JSON("application/json");

        @Getter private String contentType;
    }
}
