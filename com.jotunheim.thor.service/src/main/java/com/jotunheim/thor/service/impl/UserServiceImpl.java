/**
 * 
 */
package com.jotunheim.thor.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jotunheim.thor.dao.UserMapper;
import com.jotunheim.thor.dao.UserRoleMapper;
import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.domain.UserExample;
import com.jotunheim.thor.domain.UserRole;
import com.jotunheim.thor.service.UserService;

/**
 * @author zhangle
 *
 */
@Service
public class UserServiceImpl extends AbstractBaseService implements UserService {

    @Autowired
    private UserMapper userDao;
    
    @Autowired
    private UserRoleMapper userRoleDao;

    @Override
    public User findByName(String userName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(userName);
        List<User> list = userDao.selectByExample(example);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public User findById(long userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public int addUser(User user) {
        return userDao.insert(user);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteByPrimaryKey(user.getId());
    }

    @Override
    public UserRole findRoleById(long roleId) {
        return userRoleDao.selectByPrimaryKey(roleId);
    }

}
