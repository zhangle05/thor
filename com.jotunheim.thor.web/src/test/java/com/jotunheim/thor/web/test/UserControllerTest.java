/**
 * 
 */
package com.jotunheim.thor.web.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jotunheim.thor.web.AjaxResult;
import com.jotunheim.thor.web.controllers.ajax.UserController;

/**
 * @author zhangle
 *
 */
public class UserControllerTest extends AbstractSpringTest {

    @Autowired
    private UserController userController;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindById() throws Exception {
        AjaxResult result = userController.getUserById(1L);
        Assert.isTrue(result.getCode() == AjaxResult.SUCCESS);
    }

}
