package com.jotunheim.thor.dao.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.jotunheim.thor.dao.UserMapper;
import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.domain.UserExample;

/**
 * Created by zhangle on 10/18/16.
 */
public class UserMapperTest extends AbstractSpringTest {

    @Autowired
    private UserMapper userMapper;

    private static final String TEST_NAME = "test-a";
    private static long TEST_ID = 1L;
    
    @Before
    public void setUp() throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(TEST_NAME);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            // insert the user
            User u = new User();
            u.setName(TEST_NAME);
            userMapper.insert(u);
        }
        User u = userMapper.selectByPrimaryKey(TEST_ID);
        if (u == null) {
            u = new User();
            userMapper.insert(u);
            TEST_ID = u.getId();
        }
    }

    @After
    public void tearDown() throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(TEST_NAME);
        userMapper.deleteByExample(example);
        userMapper.deleteByPrimaryKey(TEST_ID);
    }

    @Test
    public void deleteByPrimaryKey() throws Exception {

    }

    @Test
    public void insert() throws Exception {

    }

    @Test
    public void insertSelective() throws Exception {

    }

    @Test
    public void selectByPrimaryKey() throws Exception {
        User user = userMapper.selectByPrimaryKey(TEST_ID);
        Assert.notNull(user);
    }

    @Test
    public void selectByUsername() throws Exception {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(TEST_NAME);
        List<User> users = userMapper.selectByExample(example);
        Assert.notEmpty(users);
    }

    @Test
    public void updateByPrimaryKeySelective() throws Exception {

    }

    @Test
    public void updateByPrimaryKey() throws Exception {

    }

}