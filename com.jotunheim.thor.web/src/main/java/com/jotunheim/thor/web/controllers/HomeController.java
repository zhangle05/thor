/**
 * 
 */
package com.jotunheim.thor.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangle
 *
 */
@Controller
@RequestMapping("/")
public class HomeController extends AbstractBaseController {

    /**
     * Constructor
     */
    public HomeController() {
        logger.info("HomeController created.");
    }

    @RequestMapping({ "index" })
    public String indexPage() {
        logger.info("go to home index");
        return "index";
    }

    @RequestMapping("")
    public String home() {
        logger.info("go to home");
        return "index";
    }
}
