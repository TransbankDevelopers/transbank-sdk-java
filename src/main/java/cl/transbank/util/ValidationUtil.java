package cl.transbank.util;

import java.util.List;

/**
 * This class provides utility methods for validation.
 */
public class ValidationUtil {

  /**
   * Checks if the specified string has text.
   * @param value The string to check.
   * @param valueName The name of the string.
   * @throws IllegalArgumentException If the string is null or white space.
   */
  public static void hasText(String value, String valueName) {
    if (StringUtils.isEmpty(value)) throw new IllegalArgumentException(
      "'" + valueName + "'" + " can't be null or white space"
    );
  }

  /**
   * Checks if the specified string has text and does not exceed the specified length.
   * @param value The string to check.
   * @param length The maximum length.
   * @param valueName The name of the string.
   * @throws IllegalArgumentException If the string is null, white space, or too long.
   */
  public static void hasTextWithMaxLength(
    String value,
    int length,
    String valueName
  ) {
    ValidationUtil.hasText(value, valueName);
    if (value.length() > length) throw new IllegalArgumentException(
      "'" + valueName + "'" + " is too long, the maximum length is " + length
    );
  }

  /**
   * Checks if the specified string has text, does not exceed the specified length, and does not have leading or trailing spaces.
   * @param value The string to check.
   * @param length The maximum length.
   * @param valueName The name of the string.
   * @throws IllegalArgumentException If the string is null, white space, too long, or has leading or trailing spaces.
   */
  public static void hasTextTrimWithMaxLength(
    String value,
    int length,
    String valueName
  ) {
    ValidationUtil.hasText(value, valueName);
    if (
      value.length() > value.trim().length()
    ) throw new IllegalArgumentException(
      "'" + valueName + "'" + " has spaces at the begining or the end"
    );
    if (value.length() > length) throw new IllegalArgumentException(
      "'" + valueName + "'" + " is too long, the maximum length is " + length
    );
  }

  /**
   * Checks if the specified list has elements.
   * @param value The list to check.
   * @param valueName The name of the list.
   * @throws IllegalArgumentException If the list is null or empty.
   */
  public static void hasElements(List value, String valueName) {
    if (value == null || value.isEmpty()) throw new IllegalArgumentException(
      "list '" + valueName + "'" + " can't be null or empty"
    );
  }
}
