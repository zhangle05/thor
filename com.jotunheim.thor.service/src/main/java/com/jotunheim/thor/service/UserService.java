/**
 * 
 */
package com.jotunheim.thor.service;

import com.jotunheim.thor.domain.User;
import com.jotunheim.thor.domain.UserRole;

/**
 * @author zhangle
 *
 */
public interface UserService {

    public User findById(long userId);

    public User findByName(String userName);

    public int addUser(User user);

    public int deleteUser(User user);
    
    public UserRole findRoleById(long roleId);
}
