package com.zhang.utils;

import java.util.Objects;

/**
 * @author zhang
 * &#064;date  2024/2/10
 * &#064;Description  用于校验数据为空
 */
public class DataUtils {
    public static String validation(String o){
        if(o.isEmpty()) o="0";
        return o;
    }
    public static boolean judge(String o){
        return !Objects.equals(o, "0");
    }
}
