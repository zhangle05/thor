/**
 * 
 */
package com.jotunheim.thor.web.controllers.ajax;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jotunheim.common.annotation.Login;
import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.service.UserService;
import com.jotunheim.thor.web.AjaxResult;
import com.jotunheim.thor.web.controllers.AbstractBaseController;

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

    @RequestMapping("/get/{uid}")
    public @ResponseBody AjaxResult getUserById(
            @PathVariable(value = "uid") Long userId) {
        logger.info("find user by id:" + userId);
        User user = userService.findById(userId);
        return new AjaxResult(user);
    }

    @RequestMapping("/find")
    public @ResponseBody AjaxResult findUser(
            @RequestParam(value = "name") String userName) {
        logger.info("find user by name:" + userName);
        User user = userService.findByName(userName);
        return new AjaxResult(user);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody AjaxResult addUser(
            @RequestBody String userJson) {
        logger.info("add user, json is:" + userJson);
        try {
            JSONObject json = JSONObject.fromObject(userJson);
            User user = new User();
            String userName = json.optString("name");
            if (StringUtils.isEmpty(userName)) {
                return new AjaxResult(AjaxResult.INPUT_ERROR, "User name is empty.");
            }
            user.setName(userName);
            userService.addUser(user);
            return new AjaxResult(user);
        } catch (Exception ex) {
            return new AjaxResult(AjaxResult.SYSTEM_ERROR, ex.getMessage());
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody AjaxResult updateUser(
            @RequestBody String userJson) {
        logger.info("update user, json is:" + userJson);
        try {
            JSONObject json = JSONObject.fromObject(userJson);
            User user = new User();
            long id = json.optLong("id");
            if (id <= 0) {
                return new AjaxResult(AjaxResult.INPUT_ERROR, "User id is empty.");
            }
            user.setName(json.optString("name"));
            userService.addUser(user);
            return new AjaxResult(user);
        } catch (Exception ex) {
            return new AjaxResult(AjaxResult.SYSTEM_ERROR, ex.getMessage());
        }
    }
}
