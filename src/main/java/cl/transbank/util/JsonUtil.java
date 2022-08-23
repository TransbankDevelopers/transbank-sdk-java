package cl.transbank.util;

import java.util.List;

public interface JsonUtil {
    String jsonEncode(Object o);
    <T> T jsonDecode(String json, Class<T> clazz);
    <T> List<T> jsonDecodeToList(String json, Class<T[]> clazz);
}
