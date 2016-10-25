package com.jotunheim.thor.service.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.service.UserService;

/**
 * Created by zhangle on 10/18/16.
 */
public class UserServiceTest extends AbstractSpringTest {

    @Autowired
    private UserService userService;

    private static final String TEST_NAME = "test-a";
    private static final String TEST_NAME2 = "test-b";
    private static long TEST_ID = 1L;

    @Before
    public void setUp() throws Exception {
        User user = userService.findByName(TEST_NAME);
        if (user == null) {
            // insert the user
            User u = new User();
            u.setName(TEST_NAME);
            userService.addUser(u);
        }
        user = userService.findById(TEST_ID);
        if (user == null) {
            // insert the user
            User u = new User();
            u.setName(TEST_NAME2);
            userService.addUser(u);
            TEST_ID = u.getId();
        }
    }

    @After
    public void tearDown() throws Exception {
        User user = userService.findByName(TEST_NAME);
        if (user != null) {
            userService.deleteUser(user);
        }
        user = userService.findById(TEST_ID);
        if (user != null) {
            userService.deleteUser(user);
        }
    }

    @Test
    public void testDeleteById() throws Exception {

    }

    @Test
    public void testAddUser() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {
        User user = userService.findById(TEST_ID);
        Assert.notNull(user);
    }

    @Test
    public void testAdd() throws Exception {
        int oldCount = userService.getAllUserCount();
        User user = new User();
        user.setName(TEST_NAME);
        try {
            userService.addUser(user);
        } catch (Exception ignore) {
            logger.info(ignore.getMessage());
        }
        int newCount = userService.getAllUserCount();
        Assert.isTrue(oldCount == newCount);
        user = userService.findByName(TEST_NAME);
        Assert.notNull(user);
        int result = userService.deleteUser(user);
        Assert.isTrue(result == 1);
        newCount = userService.getAllUserCount();
        Assert.isTrue(oldCount == (newCount + 1));
        userService.addUser(user);
        newCount = userService.getAllUserCount();
        Assert.isTrue(oldCount == newCount);
    }

    @Test
    public void testFindByName() throws Exception {
        User user = userService.findByName(TEST_NAME);
        Assert.notNull(user);
    }

    @Test
    public void testUpdateById() throws Exception {

    }

}