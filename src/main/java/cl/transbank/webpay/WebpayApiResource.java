package cl.transbank.webpay;

import cl.transbank.WebpayApiResponseManager;
import cl.transbank.common.Option;
import cl.transbank.exception.TransbankException;
import cl.transbank.util.BeanUtils;
import cl.transbank.util.HttpUtil;
import cl.transbank.util.HttpUtilImpl;
import cl.transbank.webpay.exception.WebpayException;
import cl.transbank.model.WebpayApiRequest;
import lombok.Getter;
import lombok.Setter;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public abstract class WebpayApiResource {
    @Getter @Setter private static HttpUtil httpUtil = HttpUtilImpl.getInstance();

    public static Map<String, String> buildHeaders(Option options) {
        if (null == options)
            return null;

        Map<String, String> headers = new HashMap<>();
        headers.put(options.getHeaderCommerceCodeName(), options.getCommerceCode());
        headers.put(options.getHeaderApiKeyName(), options.getApiKey());

        return headers;
    }

    public static <T> T execute(final URL endpoint, HttpUtil.RequestMethod method, final Option options, Class<T> clazz)
            throws TransbankException, IOException {
        return execute(endpoint, method, null, options, clazz);
    }

    public static <T> T execute(final URL endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Option options)
            throws TransbankException, IOException {
        return execute(endpoint, method, request, options, null);
    }
    public static <T> T execute(final URL endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Option options, Class<T> clazz)
            throws TransbankException, IOException {
        final WebpayApiResponseManager out = WebpayApiResource.getHttpUtil().request(endpoint, method, request, WebpayApiResource.buildHeaders(options), WebpayApiResponseManager.class);

        if (null == out)
            return null;

        if (null != out.getErrorMessage())
            throw new WebpayException(out.getErrorMessage());

        if (null == clazz)
            return null;

        try {
            return BeanUtils.getInstance().copyBeanData(clazz.newInstance(), out);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new TransbankException(e);
        }
    }
}
