package cl.transbank.util;

import lombok.NonNull;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class HttpUtilImpl implements HttpUtil {
    private static volatile HttpUtilImpl instance;

    public String request(@NonNull URL url, RequestMethod method, @NonNull String query)
            throws IOException {
        return request(url, method, query, null);
    }

    public String request(@NonNull URL url, RequestMethod method, @NonNull String query,
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

    private HttpURLConnection createPOSTConnection(URL url, String query, ContentType contentType)
            throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setRequestMethod(RequestMethod.POST.toString());
        conn.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.name());
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", String.format(
                "%s;charset=%s", contentType.getContentType(), StandardCharsets.UTF_8.name().toLowerCase()));

        try (OutputStream out = conn.getOutputStream()) {
            out.write(query.getBytes(StandardCharsets.UTF_8));
        }

        return conn;
    }

    private HttpURLConnection createGETConnection(URL url, String query, ContentType contentType) {
        // TODO implement this method if you need it
        return null;
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
