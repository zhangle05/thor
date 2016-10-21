/**
 * 
 */
package com.jotunheim.thor.web.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.LocaleResolver;

import com.jotunheim.thor.web.AjaxResult;

/**
 * @author zhangle
 *
 */
@Controller
@RequestMapping("/settings")
@SessionAttributes({ "locale" })
public class SettingsController {
    private static Log LOG = LogFactory.getLog(SettingsController.class);

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Constructor
     */
    public SettingsController() {
        LOG.debug("Creating SettingsController.");
    }

    @RequestMapping("/lang/{locale}")
    public @ResponseBody AjaxResult changeLocale(Model uiModel, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable(value = "locale") String localeStr) {
        LOG.debug("SettingsController.changeLocale");
        Locale locale = null;
        if("zh".equals(localeStr)) {
            locale = Locale.CHINESE;
        }
        else {
            locale = Locale.ENGLISH;
        }
        localeResolver.setLocale(request, response, locale);
        return AjaxResult.OK;
    }

    @RequestMapping("/check/conn")
    public @ResponseBody AjaxResult checkConn(Model uiModel, HttpServletRequest request,
            HttpServletResponse response) {
        LOG.debug("SettingsController.checkConn");
        return AjaxResult.OK;
    }
}
