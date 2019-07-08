package cl.transbank.webpay;

import cl.transbank.util.HttpUtil;
import cl.transbank.util.HttpUtilImpl;
import lombok.Getter;
import lombok.Setter;

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
}
