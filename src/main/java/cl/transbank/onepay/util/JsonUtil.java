package cl.transbank.onepay.util;

public interface JsonUtil {
    String jsonEncode(Object o);
    <T> T jsonDecode(String json, Class<T> clazz);
}
