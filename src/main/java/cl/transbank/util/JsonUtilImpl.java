package cl.transbank.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonUtilImpl implements JsonUtil {
    private static volatile JsonUtilImpl instance;
    private Gson gson;

    private JsonUtilImpl() {
        super();
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public String jsonEncode(Object o) {
        return gson.toJson(o);
    }

    public <T> T jsonDecode(String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        }
        catch (Exception e){
            if (clazz == null) return null;

            throw e;
        }
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
