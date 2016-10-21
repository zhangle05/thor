/**
 * 
 */
package com.jotunheim.thor.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotunheim.common.utils.SignHelper;
import com.jotunheim.thor.dao.ClientAccessKeyMapper;
import com.jotunheim.thor.domain.ClientAccessKey;
import com.jotunheim.thor.domain.ClientAccessKeyExample;
import com.jotunheim.thor.service.ClientAccessService;
import com.jotunheim.thor.service.utils.SharedConstants;

/**
 * @author zhangle
 *
 */
@Service
public class ClientAccessServiceImpl extends AbstractBaseService implements
        ClientAccessService {

    @Autowired
    private ClientAccessKeyMapper clientAccessDao;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.jotunheim.thor.service.ClientAccessService#findAccessKeyByAppKey(
     * java.lang.String)
     */
    @Override
    public ClientAccessKey findAccessKeyByAppKey(String appKey) {
        ClientAccessKeyExample example = new ClientAccessKeyExample();
        ClientAccessKeyExample.Criteria criteria = example.createCriteria();
        criteria.andAppKeyEqualTo(appKey);
        List<ClientAccessKey> list = clientAccessDao.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public String getCorrectSign(HashMap<String, String> allKeyValues,
            String appKey) throws UnsupportedEncodingException {
        logger.debug("sign excluded");
        allKeyValues.remove("sign");
        allKeyValues.remove(SharedConstants.CLIENT_VERSION_PARAM_NAME);
        if (!allKeyValues.containsKey(SharedConstants.TIMESTAMP_PARAM_NAME)) {
            logger.warn("timestamp not in the request");
            return SharedConstants.ERROR_SIGN_TIMESTAMP_EMPTY;
        }
        String tsStr = allKeyValues
                .get(SharedConstants.TIMESTAMP_PARAM_NAME);
        try {
            long ts = Long.parseLong(tsStr);
            if (SharedConstants.TIMESTAMP_TORLERRANCE < Math.abs(System
                    .currentTimeMillis() - ts)) {
                return SharedConstants.ERROR_SIGN_TIMESTAMP_EXPIRED;
            }
        } catch (Exception ex) {
            return SharedConstants.ERROR_SIGN_TIMESTAMP_EMPTY;
        }
        ClientAccessKey key = this.findAccessKeyByAppKey(appKey);
        if (key != null) {
            return SignHelper.generateSign(allKeyValues, appKey,
                    key.getSecretKey());
        }
        return SharedConstants.ERROR_SIGN_CLIENT_NOT_FOUND;
    }

}
