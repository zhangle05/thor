/**
 * 
 */
package com.jotunheim.thor.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import com.jotunheim.thor.domain.ClientAccessKey;

/**
 * @author zhangle
 *
 */
public interface ClientAccessService {

    public ClientAccessKey findAccessKeyByAppKey(String appKey);

    public String getCorrectSign(HashMap<String, String> allKeyValues,
            String appKey) throws UnsupportedEncodingException;
}
