/**
 * 
 */
package com.jotunheim.thor.web.interceptors;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jotunheim.common.annotation.Login;
import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.domain.UserRole;
import com.jotunheim.thor.service.UserService;
import com.jotunheim.thor.service.utils.SharedConstants;
import com.jotunheim.thor.web.AjaxResult;
import com.jotunheim.thor.web.utils.CookieUtils;
import com.jotunheim.thor.web.utils.ClientAccessUtils;

/**
 * @author dannyzha
 *
 */
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    private static Log LOG = LogFactory.getLog(AuthenticationInterceptor.class);

    @Autowired
    private CookieUtils cookieUtils;

    @Autowired
    private ClientAccessUtils clientSignUtils;

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
        LOG.info("AuthenticationInterceptor: Pre-handle, request URI is:"
                + request.getRequestURI());
        checkCookie(request, response);
        if (handler instanceof HandlerMethod) {
            HandlerMethod hmethod = (HandlerMethod) handler;
            initAttribute(hmethod, request, response);
            if (!checkLogin(hmethod, request, response)) {
                return false;
            }
        }
        return true;
    }

    private void checkCookie(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            // 判断是否有登录用户，无登录用户尝试从Cookie登录
            User user = (User) request.getSession().getAttribute(
                    SharedConstants.SESSION_USER_KEY);
            if (user == null) {
                Cookie cookie = cookieUtils.getCookie(request,
                        SharedConstants.USER_COOKIE_KEY);
                LOG.debug("cookie object is:" + cookie);
                // if (cookie != null) {
                // String userCookie = cookie.getValue();
                // LoginResult ld = accountService.loginByCookie(userCookie);
                // if(ld.statusCode == AccountService.ACCOUNT_LOGIN_SUCCEED) {
                // user = ld.realUser;
                // request.getSession().setAttribute("loginUser", user);
                // // renew the cookie
                // cookieUtils.removeCookie(response,
                // SharedConstants.USER_COOKIE_KEY);
                // cookieUtils.setCookie(response,
                // SharedConstants.USER_COOKIE_KEY, userCookie,
                // SharedConstants.COOKIE_EXPIRE_TIME);
                // }
                // }
            }
            UserRole role = (UserRole) request.getSession().getAttribute(
                    SharedConstants.SESSION_USER_ROLE_KEY);
            if (role == null && user != null) {
                role = userService.findRoleById(user.getRoleId());
                request.getSession().setAttribute(
                        SharedConstants.SESSION_USER_ROLE_KEY, role);
            }
        } catch (Exception ex) {
            LOG.error("read user from cookie failed. error is:"
                    + ex.getMessage());
            ex.printStackTrace();
        }
    }

    /**
     * 初始化request Attribute属性
     * 
     * @param request
     * @param response
     */
    private void initAttribute(HandlerMethod hmethod,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String partner = request.getParameter("partner");
        if (StringUtils.isNotEmpty(partner)) {
            // 退出浏览器 parnter失效
            cookieUtils.setCookieWithPrefix(response, "partner", partner, -1);
            request.setAttribute("partner", partner);
        } else {
            partner = cookieUtils.getCookieValueWithPrefix(request, "partner");
            if (StringUtils.isNotEmpty(partner)) {
                request.setAttribute("partner", partner);
            } else {
                request.setAttribute("partner", "");
            }
        }

        LOG.info("Partner is " + partner);

        String userAgent = request.getHeader("User-Agent");
        if (userAgent != null
                && userAgent.toLowerCase().indexOf("micromessenger") != -1) {
            request.setAttribute(SharedConstants.IS_WECHAT_KEY, true);
        } else {
            request.setAttribute(SharedConstants.IS_WECHAT_KEY, false);
        }

        boolean isMobile = false;
        if (userAgent != null && userAgent.indexOf("Mobile") > -1) {
            isMobile = true;
        }
        request.setAttribute(SharedConstants.IS_MOBILE_KEY, isMobile);
    }

    private boolean checkLogin(HandlerMethod hmethod,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Method method = hmethod.getMethod();
        // 用户登录
        Login login = hmethod.getBean().getClass().getAnnotation(Login.class);
        if (login == null || !login.required()) {
            login = method.getAnnotation(Login.class);
        }
        if (login != null && login.required()) {
            LOG.info("login required.");
            User user = (User) request.getSession().getAttribute(
                    SharedConstants.SESSION_USER_KEY);
            boolean isWechat = (Boolean) request
                    .getAttribute(SharedConstants.IS_WECHAT_KEY);
            boolean isMobile = (Boolean) request
                    .getAttribute(SharedConstants.IS_MOBILE_KEY);
            boolean isJson = method.getAnnotation(ResponseBody.class) != null;
            String partner = (String) request.getAttribute("partner");
            if (user == null) {
                LOG.info("AuthenticationInterceptor: recirecting "
                        + request.getRequestURI() + " to /account/login");
                LOG.debug("###request uri is:" + request.getRequestURI());
                if (isWechat) {
                    // user = autoLoginWithOpenID(request);
                }
                if (user == null) {
                    return issue403(request, response, isMobile, isJson,
                            partner);
                }
            } else {
                LOG.debug("user is:" + user.getNickname());
                int pageRank = login.rank();
                UserRole role = userService.findRoleById(user.getRoleId());
                LOG.debug("role is:" + role + ", level is:" + role.getRank());
                if (role != null) {
                    request.getSession().setAttribute(
                            SharedConstants.SESSION_USER_ROLE_KEY, role);
                }
                LOG.debug("page accessLevel is:" + pageRank
                        + ", role accessLevel is:" + role.getRank());
                if (pageRank > role.getRank()) {
                    return issue401(role, request, response, isMobile, isJson);
                }
            }
        }
        LOG.info("AuthenticationInterceptor: Pass the interceptor, no recirection.");
        return true;
    }

    private boolean issue403(HttpServletRequest request,
            HttpServletResponse response, boolean isMobile, boolean isJson,
            String partner) throws IOException {
        // user in session is empty
        int checkSignCode = clientSignUtils.checkSign(request);
        if (checkSignCode == AjaxResult.SUCCESS) {
            return true;
        } else {
            errorResponse(checkSignCode, request, response, isMobile, isJson, partner);
            return false;
        }
    }

    private void errorResponse(int errorCode, HttpServletRequest request,
            HttpServletResponse response, boolean isMobile, boolean isJson,
            String partner) throws IOException {
        String redirectUrl = "/wap/account/login";
        if (!isMobile) {
            redirectUrl = "/web/account/login";
        }
        if (isJson) {
            AjaxResult result = new AjaxResult(errorCode,
                    "Request is forbidden.");
            JSONObject obj = JSONObject.fromObject(result);
            obj.put(SharedConstants.REDIRECT_URL_KEY, redirectUrl);
            PrintWriter write = response.getWriter();
            write.write(obj.toString());
            write.flush();
        } else {
            StringBuilder builder = new StringBuilder(request.getRequestURL()
                    .toString());
            String qs = request.getQueryString();
            if (!StringUtils.isEmpty(qs)) {
                builder.append("?");
                builder.append(qs);
            }
            String returnurl = URLEncoder.encode(builder.toString(), "UTF-8");
            LOG.debug(returnurl);
            if (isMobile) {
                response.sendRedirect(String.format(
                        "%s?partner=%s&returnurl=%s", redirectUrl, partner,
                        returnurl));
            } else {
                response.sendRedirect(String.format(
                        "%s?partner=%s&returnurl=%s", redirectUrl, partner,
                        returnurl));
            }
        }
    }

    private boolean issue401(UserRole role, HttpServletRequest request,
            HttpServletResponse response, boolean isMobile, boolean isJson)
            throws IOException {
        int checkSignCode = clientSignUtils.checkSign(request);
        if (AjaxResult.SUCCESS == checkSignCode) {
            return true;
        }
        errorResponseWithRole(checkSignCode, role, response, isMobile, isJson);
        return false;
    }

    private void errorResponseWithRole(int errorCode, UserRole role, HttpServletResponse response,
            boolean isMobile, boolean isJson) throws IOException {
        // user level smaller than the page level
        String redirctUrl = "/";
        // if (isMobile) {
        // switch (role.getAccessLevel()) {
        // case RoleAccessLevel.ADMIN:
        // break;
        // case RoleAccessLevel.SUPERVISOR:
        // break;
        // case RoleAccessLevel.USER:
        // redirctUrl = "/wap";
        // break;
        // default:
        // break;
        // }
        // } else {
        // switch (role.getAccessLevel()) {
        // case RoleAccessLevel.ADMIN:
        // break;
        // case RoleAccessLevel.SUPERVISOR:
        // break;
        // case RoleAccessLevel.USER:
        // redirctUrl = "/";
        // break;
        // default:
        // break;
        // }
        // }
        if (isJson) {
            AjaxResult result = new AjaxResult(errorCode,
                    "Unauthorized request.");
            JSONObject obj = JSONObject.fromObject(result);
            obj.put(SharedConstants.REDIRECT_URL_KEY, redirctUrl);
            PrintWriter write = response.getWriter();
            write.write(obj.toString());
            write.flush();
        } else {
            response.sendRedirect(redirctUrl);
        }
    }

}
