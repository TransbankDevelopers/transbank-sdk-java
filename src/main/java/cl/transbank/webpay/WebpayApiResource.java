package cl.transbank.webpay;

import cl.transbank.WebpayApiResponseManager;
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

    public static Map<String, String> buildHeaders(Options options) {
        if (null == options)
            return null;

        Map<String, String> headers = new HashMap<>();
        headers.put("Tbk-Api-Key-Id", options.getCommerceCode());
        headers.put("Tbk-Api-Key-Secret", options.getApiKey());

        return headers;
    }

    public static <T> T execute(final URL endpoint, HttpUtil.RequestMethod method, final Options options, Class<T> clazz)
            throws WebpayException, IOException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, NoSuchMethodException {
        return execute(endpoint, method, null, options, clazz);
    }

    public static <T> T execute(final URL endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Options options)
            throws WebpayException, IOException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, NoSuchMethodException{
        return execute(endpoint, method, request, options, null);
    }
    public static <T> T execute(final URL endpoint, HttpUtil.RequestMethod method, final WebpayApiRequest request, final Options options, Class<T> clazz)
            throws WebpayException, IOException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException, NoSuchMethodException {
        final WebpayApiResponseManager out = WebpayApiResource.getHttpUtil().request(endpoint, method, request, WebpayApiResource.buildHeaders(options), WebpayApiResponseManager.class);

        if (null == out)
            return null;

        if (null != out.getErrorMessage())
            throw new WebpayException(out.getErrorMessage());

        if (null == clazz)
            return null;

        return BeanUtils.getInstance().copyBeanData(clazz.newInstance(), out);
    }
}
