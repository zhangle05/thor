/**
 * 
 */
package com.jotunheim.thor.service.utils;

import com.jotunheim.common.utils.CipherHelper;

/**
 * @author zhangle
 *
 */
public final class SharedConstants {

    public static final String USER_COOKIE_KEY = CipherHelper
            .md5sum("Thor-UserCookie");

    public static final int COOKIE_EXPIRE_TIME = 3600 * 24 * 15;

    public static final String IS_WECHAT_KEY = "isWechat";

    public static final String IS_MOBILE_KEY = "isMobile";

    public static final String CLIENT_VERSION_PARAM_NAME = "client_version";

    public static final String TIMESTAMP_PARAM_NAME = "ts";

    public static final String CLIENT_KEY_PARAM_NAME = "key";

    public static final String CLIENT_SIGN_PARAM_NAME = "sign";

    public static final String ERROR_SIGN_TIMESTAMP_EMPTY = "请求时间戳为空，请重试";

    public static final String ERROR_SIGN_CLIENT_NOT_FOUND = "client not found";

    public static final String ERROR_SIGN_TIMESTAMP_EXPIRED = "本机时间错误，请先调整时间";

    public static final int TIMESTAMP_TORLERRANCE = 1000 * 60 * 30;

    public static final String SESSION_USER_ROLE_KEY = "userRole";

    public static final String SESSION_USER_KEY = "loginUser";

    public static final String REDIRECT_URL_KEY = "url";

    public static final long MILS_5_MINUTES = 1000 * 60 * 5L;

    public static final long MILS_5_HALF_HOUR = 1000 * 60 * 30L;
}
