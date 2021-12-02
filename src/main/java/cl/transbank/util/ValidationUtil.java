package cl.transbank.util;

import java.util.List;

public class ValidationUtil {

    public static void hasText(String value, String valueName){
        if (StringUtils.isEmpty(value))
            throw new IllegalArgumentException("'" + valueName + "'" + " can't be null or white space");
    }

    public static void hasTextWithMaxLength(String value, int length, String valueName){
        ValidationUtil.hasText(value, valueName);
        if (value.length() > length)
            throw new IllegalArgumentException ("'" + valueName + "'" + " is too long, the maximum length is " + length);
    }
    public static void hasTextTrimWithMaxLength(String value, int length, String valueName){
        ValidationUtil.hasText(value, valueName);
        if (value.length() > value.trim().length())
            throw new IllegalArgumentException ("'" + valueName + "'" + " has spaces at the begining or the end");
        if (value.length() > length)
            throw new IllegalArgumentException ("'" + valueName + "'" + " is too long, the maximum length is " + length);
    }
    public static void hasElements(List value, String valueName){
        if (value == null || value.size() == 0)
            throw new IllegalArgumentException ("list '" + valueName + "'" + " can't be null or empty");
    }
}
