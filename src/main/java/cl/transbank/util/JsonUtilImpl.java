package cl.transbank.util;

import com.google.gson.Gson;

public final class JsonUtilImpl implements JsonUtil {
    private static volatile JsonUtilImpl instance;
    private Gson gson;

    private JsonUtilImpl() {
        super();
        gson = new Gson();
    }

    public String jsonEncode(Object o) {
        return gson.toJson(o);
    }

    public <T> T jsonDecode(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static JsonUtilImpl getInstance() {
        if (null == instance) {
            synchronized (JsonUtilImpl.class) {
                instance = new JsonUtilImpl();
            }
        }

        return instance;
    }
}
