package cl.transbank.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides utility methods for JSON encoding and decoding.
 */
public final class JsonUtilImpl implements JsonUtil {

  private static volatile JsonUtilImpl instance;
  private Gson gson;

  /**
   * Private constructor. Initializes the Gson object with specific settings.
   */
  private JsonUtilImpl() {
    super();
    gson =
      new GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create();
  }

  /**
   * Encodes an object into a JSON string.
   * @param o The object to encode.
   * @return The JSON string representation of the object.
   */
  public String jsonEncode(Object o) {
    return gson.toJson(o);
  }

  /**
   * Decodes a JSON string into an object of the specified class.
   * @param json The JSON string to decode.
   * @param clazz The class of the object.
   * @return The object decoded from the JSON string.
   */
  public <T> T jsonDecode(String json, Class<T> clazz) {
    return gson.fromJson(json, clazz);
  }

  /**
   * Decodes a JSON string into a list of objects of the specified class.
   * @param json The JSON string to decode.
   * @param clazz The class of the objects.
   * @return The list of objects decoded from the JSON string.
   */
  public <T> List<T> jsonDecodeToList(String json, Class<T[]> clazz) {
    return Arrays.asList(gson.fromJson(json, clazz));
  }

  /**
   * This method provides a global access point to the instance of the JsonUtilImpl class.
   * It follows the singleton pattern.
   * @return The single instance of JsonUtilImpl.
   */
  public static JsonUtilImpl getInstance() {
    if (null == instance) {
      synchronized (JsonUtilImpl.class) {
        instance = new JsonUtilImpl();
      }
    }

    return instance;
  }
}
