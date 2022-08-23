package cl.transbank.util;

import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import cl.transbank.webpay.common.WebpayOptions;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WebpayApiResource {
    @Getter @Setter private static HttpUtil httpUtil = HttpUtilImpl.getInstance();

    public static Map<String, String> buildHeaders(Options options) {
        if (null == options)
            return null;

        Map<String, String> headers = new HashMap<>();
        headers.put(options.getHeaderCommerceCodeName(), options.getCommerceCode());
        headers.put(options.getHeaderApiKeyName(), options.getApiKey());

        return headers;
    }

    public static <T> T execute(final String endpoint, HttpUtil.RequestMethod method, final Options options, Class<T> clazz)
            throws TransbankException, IOException {
        return execute(endpoint, method, null, options, clazz);
    }

    public static <T> T execute(final String endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Options options)
            throws TransbankException, IOException {
        return execute(endpoint, method, request, options, null);
    }

    public static <T> T execute(final String endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Options options, Class<T> clazz)
            throws TransbankException, IOException {

        String urlBase = null;
        if(options instanceof WebpayOptions){
            urlBase = IntegrationTypeHelper.getWebpayIntegrationType(options.getIntegrationType());
        }
        else{
            urlBase = IntegrationTypeHelper.getPatpassIntegrationType(options.getIntegrationType());
        }
        final URL url = new URL(String.format("%s/%s", urlBase, endpoint));

        final T out = WebpayApiResource.getHttpUtil().request(url, method, request, WebpayApiResource.buildHeaders(options), clazz);

        if (null == out)
            return null;

        if (null == clazz)
            return null;

        return out;
    }
    public static <T> List<T> executeToList(final String endpoint, HttpUtil.RequestMethod method, final Options options, Class<T[]> clazz)
            throws TransbankException, IOException {
        return executeToList(endpoint, method, null, options, clazz);
    }
    public static <T> List<T> executeToList(final String endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Options options, Class<T[]> clazz)
            throws TransbankException, IOException {

        String urlBase = null;
        if(options instanceof WebpayOptions){
            urlBase = IntegrationTypeHelper.getWebpayIntegrationType(options.getIntegrationType());
        }
        else{
            urlBase = IntegrationTypeHelper.getPatpassIntegrationType(options.getIntegrationType());
        }
        final URL url = new URL(String.format("%s/%s", urlBase, endpoint));

        final List<T> out = WebpayApiResource.getHttpUtil().requestList(url, method, request, WebpayApiResource.buildHeaders(options), clazz);

        if (null == out)
            return null;

        if (null == clazz)
            return null;

        return out;
    }

}
