package cl.transbank.util;

/**
 * This class provides utility methods for string operations.
 */
public class StringUtils {

  /**
   * Checks if a string is null or empty after trimming.
   * @param str The string to check.
   * @return True if the string is null or empty after trimming, false otherwise.
   */
  public static boolean isEmpty(String str) {
    return str == null || str.trim().length() == 0;
  }
}
