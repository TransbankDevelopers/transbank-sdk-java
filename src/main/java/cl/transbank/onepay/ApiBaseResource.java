package cl.transbank.onepay;

import cl.transbank.onepay.util.*;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.HttpUtilImpl;
import lombok.NonNull;

import java.io.IOException;
import java.net.URL;

public abstract class ApiBaseResource {
    private static RequestBuilder requestBuilder = OnepayRequestBuilder.getInstance();
    private static JsonUtil jsonUtil = OnepayJsonUtil.getInstance();
    private static SignUtil signUtil = OnepaySignUtil.getInstance();
    private static HttpUtil httpUtil = HttpUtilImpl.getInstance();

    protected static RequestBuilder getRequestBuilder() {
        return requestBuilder;
    }

    public static void setRequestBuilder(RequestBuilder requestBuilder) {
        ApiBaseResource.requestBuilder = requestBuilder;
    }

    protected static JsonUtil getJsonUtil() {
        return jsonUtil;
    }

    public static void setJsonUtil(JsonUtil jsonUtil) {
        ApiBaseResource.jsonUtil = jsonUtil;
    }

    protected static SignUtil getSignUtil() {
        return signUtil;
    }

    public static void setSignUtil(SignUtil signUtil) {
        ApiBaseResource.signUtil = signUtil;
    }

    protected static HttpUtil getHttpUtil() {
        return httpUtil;
    }

    public static void setHttpUtil(HttpUtil httpUtil) {
        ApiBaseResource.httpUtil = httpUtil;
    }

    protected static String request(@NonNull URL url, HttpUtilImpl.RequestMethod method, @NonNull String query)
            throws IOException {
        return getHttpUtil().request(url, method, query);
    }

    public String request(@NonNull URL url, HttpUtilImpl.RequestMethod method, @NonNull String query,
                          HttpUtil.ContentType contentType) throws IOException {
        return getHttpUtil().request(url, method, query, contentType);
    }
}
