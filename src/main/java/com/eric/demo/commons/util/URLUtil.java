package com.eric.demo.commons.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;

public class URLUtil {
    public static String appendParams(Map<String, String> params) {
        if (Check.NuNObj(params)) {
            return null;
        } else {
            StringBuffer sb = new StringBuffer("");
            Set<String> keys = params.keySet();
            for (String key : keys) {
                if (Check.NuNObj(params.get(key))) {
                    continue;
                }
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);

            return sb.toString();
        }
    }



    public static void main(String[] args) {
        Map<String, String> param = Maps.newHashMap();
        param.put("a", "1");
        param.put("b", "2");
        System.out.println(URLUtil.appendParams(param));
    }
}
