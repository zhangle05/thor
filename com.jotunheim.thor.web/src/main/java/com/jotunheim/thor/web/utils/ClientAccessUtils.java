/**
 * 
 */
package com.jotunheim.thor.web.utils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jotunheim.thor.service.ClientAccessService;
import com.jotunheim.thor.service.utils.SharedConstants;
import com.jotunheim.thor.web.AjaxResult;
import com.jotunheim.thor.web.filters.BodyCacheRequestWrapper;

/**
 * @author zhangle
 *
 */
public class ClientAccessUtils {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ClientAccessService clientAccessService;

    /**
     * 检查request参数签名
     * @return: result code defined in AjaxResult
     */
    public int checkSign(HttpServletRequest request) {
        if ("POST".equals(request.getMethod())) {
            return checkPostSign(request);
        }
        String appKey = request.getParameter(SharedConstants.CLIENT_KEY_PARAM_NAME);
        String sign = request.getParameter(SharedConstants.CLIENT_SIGN_PARAM_NAME);
        if (StringUtils.isEmpty(appKey)) {
            return AjaxResult.INPUT_ERROR;
        }
        if (StringUtils.isEmpty(sign)) {
            return AjaxResult.INPUT_ERROR;
        }
        try {
            String correctSign = getCorrectSign(request, appKey);
            return compareSign(sign, correctSign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return AjaxResult.SYSTEM_ERROR;
        }
    }

    private int checkPostSign(HttpServletRequest request) {
        if (!(request instanceof BodyCacheRequestWrapper)) {
            logger.info("requst body cannot be read twice!");
            return AjaxResult.FORBIDDEN;
        }
        BodyCacheRequestWrapper requestWrapper = (BodyCacheRequestWrapper)request;
        String body = requestWrapper.getBody();
        try {
            JSONObject json = JSONObject.fromObject(body);
            return checkSign(json);
        } catch (Exception ex) {
            ex.printStackTrace();
            return AjaxResult.INPUT_ERROR;
        }
    }

    /**
     * 检查json参数签名
     *
     * @return: result code defined in AjaxResult
     */
    public int checkSign(JSONObject paramJson) {
        String appKey = paramJson.optString(SharedConstants.CLIENT_KEY_PARAM_NAME);
        String sign = paramJson.optString(SharedConstants.CLIENT_SIGN_PARAM_NAME);
        if (StringUtils.isEmpty(appKey)) {
            return AjaxResult.INPUT_ERROR;
        }
        if (StringUtils.isEmpty(sign)) {
            return AjaxResult.INPUT_ERROR;
        }
        @SuppressWarnings("rawtypes")
        java.util.Iterator it = paramJson.keys();
        HashMap<String, String> keyValues = new HashMap<String, String>();
        while (it.hasNext()) {
            String name = it.next().toString();
            String value = paramJson.optString(name);
            keyValues.put(name, value);
        }
        try {
            String correctSign = clientAccessService.getCorrectSign(keyValues,
                    appKey);
            return compareSign(sign, correctSign);
        } catch (Exception ex) {
            ex.printStackTrace();
            return AjaxResult.SYSTEM_ERROR;
        }
    }

    protected String getCorrectSign(HttpServletRequest request, String appKey)
            throws UnsupportedEncodingException {
        HashMap<String, String> keyValues = new HashMap<String, String>();
        @SuppressWarnings("rawtypes")
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = em.nextElement().toString();
            String value = request.getParameter(name);
            keyValues.put(name, value);
        }
        Object clientVersion = request
                .getAttribute(SharedConstants.CLIENT_VERSION_PARAM_NAME);
        if (clientVersion != null) {
            keyValues.put(SharedConstants.CLIENT_VERSION_PARAM_NAME,
                    clientVersion.toString());
        }
        return clientAccessService.getCorrectSign(keyValues, appKey);
    }

    private int compareSign(String sign, String correctSign) {
        logger.debug("correct sign is:" + correctSign);
        if (SharedConstants.ERROR_SIGN_CLIENT_NOT_FOUND.equals(correctSign)) {
            return AjaxResult.FORBIDDEN;
        }
        if (SharedConstants.ERROR_SIGN_TIMESTAMP_EMPTY
                        .equals(correctSign)) {
            return AjaxResult.INPUT_ERROR;
        }
        if (SharedConstants.ERROR_SIGN_TIMESTAMP_EXPIRED
                        .equals(correctSign)) {
            return AjaxResult.REQUEST_EXPIRED;
        }
        if (!sign.equals(correctSign)) {
            return AjaxResult.CLIENT_SIGN_ERROR;
        }
        return AjaxResult.SUCCESS;
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
        System.out.println(System.currentTimeMillis());
    }
}
