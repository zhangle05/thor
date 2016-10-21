/**
 * 
 */
package com.jotunheim.thor.web.test;

import java.util.HashMap;

import net.sf.json.JSONObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jotunheim.thor.service.ClientAccessService;
import com.jotunheim.thor.service.utils.SharedConstants;
import com.jotunheim.thor.web.AjaxResult;
import com.jotunheim.thor.web.utils.ClientAccessUtils;

/**
 * @author zhangle
 *
 */
public class ClientAccessTest extends AbstractSpringTest {

    @Autowired
    private ClientAccessUtils clientSignUtils;

    @Autowired
    private ClientAccessService clientAccessSvc;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCheckJson() throws Exception {
        JSONObject json = new JSONObject();
        json.put(SharedConstants.CLIENT_KEY_PARAM_NAME, "android");
        json.put(SharedConstants.CLIENT_SIGN_PARAM_NAME,
                "6C99263E3EF4ADEE0F15E2DF5199DA67691387AD");
        json.put(SharedConstants.TIMESTAMP_PARAM_NAME,
                System.currentTimeMillis());

        String appKey = json.optString(SharedConstants.CLIENT_KEY_PARAM_NAME);
        String correctSign = getCorrectSign(json, appKey);
        json.put(SharedConstants.CLIENT_SIGN_PARAM_NAME, correctSign);
        int resultCode = clientSignUtils.checkSign(json);
        Assert.isTrue(AjaxResult.SUCCESS == resultCode);
    }

    @Test
    public void testTimeTorlerrance() throws Exception {
        JSONObject json = new JSONObject();
        json.put(SharedConstants.CLIENT_KEY_PARAM_NAME, "android");
        json.put(SharedConstants.CLIENT_SIGN_PARAM_NAME,
                "6C99263E3EF4ADEE0F15E2DF5199DA67691387AD");
        json.put(SharedConstants.TIMESTAMP_PARAM_NAME,
                System.currentTimeMillis() - SharedConstants.TIMESTAMP_TORLERRANCE);

        String appKey = json.optString(SharedConstants.CLIENT_KEY_PARAM_NAME);
        String correctSign = getCorrectSign(json, appKey);
        json.put(SharedConstants.CLIENT_SIGN_PARAM_NAME, correctSign);
        int resultCode = clientSignUtils.checkSign(json);
        Assert.isTrue(AjaxResult.SUCCESS != resultCode);

        json.put(SharedConstants.TIMESTAMP_PARAM_NAME,
                System.currentTimeMillis() + SharedConstants.TIMESTAMP_TORLERRANCE);

        correctSign = getCorrectSign(json, appKey);
        json.put(SharedConstants.CLIENT_SIGN_PARAM_NAME, correctSign);
        resultCode = clientSignUtils.checkSign(json);
        Assert.isTrue(AjaxResult.SUCCESS == resultCode);
    }
    
    private String getCorrectSign(JSONObject json, String appKey) throws Exception {
        @SuppressWarnings("rawtypes")
        java.util.Iterator it = json.keys();
        HashMap<String, String> keyValues = new HashMap<String, String>();
        while (it.hasNext()) {
            String name = it.next().toString();
            String value = json.optString(name);
            keyValues.put(name, value);
        }
        return clientAccessSvc.getCorrectSign(keyValues, appKey);
    }
}
