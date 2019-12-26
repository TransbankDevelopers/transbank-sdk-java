package cl.transbank.util;

public class StringUtils {
    public static boolean isEmpty(String str) {
        return null == str || str.trim().length() == 0;
    }
}
