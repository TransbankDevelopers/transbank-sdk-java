package cl.transbank.util;

import java.util.List;

/**
 * This interface provides methods for JSON encoding and decoding.
 */
public interface JsonUtil {
  /**
   * Encodes an object into a JSON string.
   * @param o The object to encode.
   * @return The JSON string.
   */
  String jsonEncode(Object o);

  /**
   * Decodes a JSON string into an object of the specified class.
   * @param json The JSON string.
   * @param clazz The class of the object.
   * @param <T> The type of the object.
   * @return The decoded object.
   */
  <T> T jsonDecode(String json, Class<T> clazz);

  /**
   * Decodes a JSON string into a list of objects of the specified class.
   * @param json The JSON string.
   * @param clazz The class of the objects.
   * @param <T> The type of the objects.
   * @return The list of decoded objects.
   */
  <T> List<T> jsonDecodeToList(String json, Class<T[]> clazz);
}
