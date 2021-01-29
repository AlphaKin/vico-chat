package org.vico.common.utils;

import java.util.Objects;

public class CommonUtil {
    public static boolean checkEmpty(Object... objects){
        for(Object object : objects){
            if(Objects.isNull(object)) return false;
            if(Objects.requireNonNull(object) instanceof String){
                return !"".equals(object);
            }
        }
        return true;
    }
}
