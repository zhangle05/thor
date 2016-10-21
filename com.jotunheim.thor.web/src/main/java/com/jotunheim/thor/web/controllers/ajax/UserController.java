/**
 * 
 */
package com.jotunheim.thor.web.controllers.ajax;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jotunheim.common.annotation.Login;
import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.service.UserService;
import com.jotunheim.thor.web.AbstractBaseController;
import com.jotunheim.thor.web.AjaxResult;

/**
 * @author zhangle
 *
 */
@Login
@Controller
@RequestMapping("/ajax/user")
public class UserController extends AbstractBaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("/find/{uid}")
    public @ResponseBody AjaxResult findUserById(
            @PathVariable(value = "uid") Long userId) {
        logger.info("find user by id:" + userId);
        User user = userService.findById(userId);
        return new AjaxResult(user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody AjaxResult addUser(
            @RequestBody String userJson) {
        logger.info("add user, json is:" + userJson);
        try {
            JSONObject json = JSONObject.fromObject(userJson);
            User user = new User();
            user.setName(json.optString("name"));
            userService.addUser(user);
            return new AjaxResult(user);
        } catch (Exception ex) {
            return new AjaxResult(AjaxResult.SYSTEM_ERROR, ex.getMessage());
        }
    }
}
