package com.jotunheim.thor.web.test;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A spring base test class. Created by zhangle on 10/18/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/appContext.xml",
        "/META-INF/webmvc-config.xml", "/META-INF/appContext-services.xml",
        "/META-INF/appContext-mybatis.xml" })
public abstract class AbstractSpringTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
}