package org.vico.im.utils;

import java.util.Objects;

public class CommonUtil {
    public static boolean checkEmpty(Object object){
        if(Objects.isNull(object)) return false;
        if(Objects.requireNonNull(object) instanceof String){
            return "".equals(object);
        }
        return true;
    }
}
