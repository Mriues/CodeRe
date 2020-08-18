package com.lpm.shop.server.imp;

import com.lpm.shop.entity.User;
import com.lpm.shop.mapper.UserMapper;
import com.lpm.shop.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author Master
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public List findAll() {
        List<User> all = userMapper.findAll();
        List list = new ArrayList();
        for (User user : all) {
            HashMap map = new HashMap();
            map.put("id",user.getId());
            map.put("role_name",user.getRoleName());
            map.put("username",user.getName());
            map.put("create_time",user.getCreateTime());
            map.put("email",user.getEmail());
            map.put("mobile",user.getMobile());
            if (user.getState() == 1){
                map.put("mg_state",true);
            }else if (user.getState() == 0){
                map.put("mg_state",false);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public User queryUserByName(String name) {
        User user = userMapper.queryUserByName(name);
        return user;
    }

    @Override
    public void addUser(String name,String password,String date) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setCreateTime(date);
        user.setIsAdmin(0);
        user.setState(1);
        userMapper.addUser(user);
    }

    @Override
    public void addUserForMenu(String name, String password, String email, String mobile) {
        User user = new User();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format(date);
        user.setName(name);
        user.setPassword(password);
        user.setCreateTime(time);
        user.setState(1);
        user.setEmail(email);
        user.setIsAdmin(0);
        user.setMobile(mobile);
        userMapper.addUserForMenu(user);
    }

    @Override
    public void updateState(int state, int userId) {
        userMapper.updateState(state,userId);
    }

    @Override
    public int isAvailable(String name) {
        int available = userMapper.isAvailable(name);
        return available;
    }

    @Override
    public List searchUserByName(String name) {
        List<User> users = userMapper.searchUserByName(name);
        List list = new ArrayList();
        for (User user : users) {
            HashMap map = new HashMap();
            map.put("id",user.getId());
            map.put("role_name",user.getRoleName());
            map.put("username",user.getName());
            map.put("create_time",user.getCreateTime());
            map.put("email",user.getEmail());
            map.put("mobile",user.getMobile());
            if (user.getState() == 1){
                map.put("mg_state",true);
            }else if (user.getState() == 0){
                map.put("mg_state",false);
            }
            list.add(map);
        }
        return list;
    }

    @Override
    public void updateUser(int id,String email,String mobile) {
        userMapper.updateUser(id,email,mobile);
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }

    @Override
    public void updateUserRole(int userId, int roleId) {
        userMapper.updateUserRole(userId,roleId);
    }


}
