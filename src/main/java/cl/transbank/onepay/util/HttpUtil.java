package cl.transbank.onepay.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;

public interface HttpUtil {
    String request(URL url, OnepayHttpUtil.RequestMethod method, String query) throws IOException;
    String request(URL url, OnepayHttpUtil.RequestMethod method, String query, HttpUtil.ContentType contentType)
            throws IOException;

    @AllArgsConstructor
    enum ContentType {
        JSON("application/json");

        @Getter
        private String contentType;
    }

    enum RequestMethod {
        GET,
        POST
    }
}
