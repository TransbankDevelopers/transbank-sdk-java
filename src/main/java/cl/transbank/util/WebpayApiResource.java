package cl.transbank.util;

import cl.transbank.common.IntegrationTypeHelper;
import cl.transbank.model.Options;
import cl.transbank.exception.TransbankException;
import cl.transbank.model.WebpayApiRequest;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
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
        final URL url = new URL(String.format("%s/%s", IntegrationTypeHelper.getWebpayIntegrationType(options.getIntegrationType()), endpoint));
        final T out = WebpayApiResource.getHttpUtil().request(url, method, request, WebpayApiResource.buildHeaders(options), clazz);

        if (null == out)
            return null;

        if (null == clazz)
            return null;

        return out;
    }
}
