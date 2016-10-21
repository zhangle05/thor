package com.jotunheim.thor.web.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jotunheim.thor.web.controllers.HomeController;

/**
 * Created by zhangle on 10/18/16.
 */
public class HomeControllerTest extends AbstractSpringTest {

    @Autowired
    private HomeController homeController;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGoHome() throws Exception {
        String homePage = homeController.home();
        Assert.isTrue("index".equals(homePage));
    }

}