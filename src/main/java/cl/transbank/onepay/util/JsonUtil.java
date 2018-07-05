package cl.transbank.onepay.util;

import com.google.gson.Gson;

public final class JsonUtil {
    private static volatile JsonUtil instance;
    private Gson gson;

    private JsonUtil() {
        super();
        gson = new Gson();
    }

    public String jsonEncode(Object o) {
        return gson.toJson(o);
    }

    public <T> T jsonDecode(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static JsonUtil getInstance() {
        if (null == instance) {
            synchronized (JsonUtil.class) {
                instance = new JsonUtil();
            }
        }

        return instance;
    }
}
