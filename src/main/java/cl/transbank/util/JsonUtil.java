package cl.transbank.util;

public interface JsonUtil {
    String jsonEncode(Object o);
    <T> T jsonDecode(String json, Class<T> clazz);
}
