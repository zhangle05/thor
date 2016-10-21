/**
 * 
 */
package com.jotunheim.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhangle
 *
 */
public class SignHelper {

    public static String generateSign(HashMap<String, String> keyValues, String appKey, String secretKey) throws UnsupportedEncodingException {
        String paramStr = "";
        List<String> sortedKeys = new ArrayList<String>(keyValues.size());
        sortedKeys.addAll(keyValues.keySet());
        Collections.sort(sortedKeys);
        for(int i=0; i<sortedKeys.size(); i++) {
            String key = sortedKeys.get(i);
            String value = keyValues.get(key);
            if(null == key || "".equals(key) || null == value || "".equals(value)) {
                continue;
            }
            paramStr += key + "=" + value;
        }
        String secretText = secretKey + "from=" + appKey + paramStr;
        SHA1Utils sha1 = new SHA1Utils();
        return sha1.getDigestOfString(secretText.getBytes("UTF-8"));
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        String appKey = "android";
        String secretKey = "m3Qy43HbMqef78TMLT9yCbQpYJwkl06X";

        try {
            HashMap<String, String> keyValues = new HashMap<String, String>();
            keyValues.put("number", "13811138857");
            keyValues.put("key", "android");
            String sign = generateSign(keyValues, appKey, secretKey);
            System.out.println(sign);
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
