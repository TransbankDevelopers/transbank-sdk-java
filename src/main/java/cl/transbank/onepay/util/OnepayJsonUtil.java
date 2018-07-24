package cl.transbank.onepay.util;

import com.google.gson.Gson;

public final class OnepayJsonUtil implements JsonUtil {
    private static volatile OnepayJsonUtil instance;
    private Gson gson;

    private OnepayJsonUtil() {
        super();
        gson = new Gson();
    }

    public String jsonEncode(Object o) {
        return gson.toJson(o);
    }

    public <T> T jsonDecode(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static OnepayJsonUtil getInstance() {
        if (null == instance) {
            synchronized (OnepayJsonUtil.class) {
                instance = new OnepayJsonUtil();
            }
        }

        return instance;
    }
}
