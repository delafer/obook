package net.korvin.utils;

import javax.annotation.Nullable;
import java.util.Objects;

public class Utils {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean equal(@Nullable Object src, @Nullable Object target)  {
        return Objects.equals(src, target);
    }
}
