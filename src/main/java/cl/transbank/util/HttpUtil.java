package cl.transbank.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public interface HttpUtil {
    <T> T request(@NonNull URL url, RequestMethod method, Object request, Map<String, String> headers,
                  Class<T> clazz) throws IOException;
    String request(URL url, HttpUtilImpl.RequestMethod method, String query) throws IOException;
    String request(URL url, HttpUtilImpl.RequestMethod method, String query, HttpUtil.ContentType contentType)
            throws IOException;
    String request(@NonNull URL url, RequestMethod method, String query, Map<String, String> headers)
            throws IOException;
    String request(@NonNull URL url, RequestMethod method, String query, ContentType contentType, Map<String,
            String> headers) throws IOException;

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
        PUT
    }
}
