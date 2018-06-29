package cl.transbank.onepay.util;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public final class JsonUtil {
    private static volatile JsonUtil instance;
    private Gson gson = null;

    private JsonUtil() {
        super();
        gson = new Gson();
    }

    public String jsonEncode(Object o) {
        try {
            if (o instanceof Signable)
                ((Signable) o).sign();
        } catch (Exception e) {
            throw new JsonIOException(e);
        }

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
