package com.lpm.shop.mapper;

import com.lpm.shop.entity.User;

import java.util.List;

public interface UserMapper {

    List<User> findAll();

    User queryUserByName(String name);

    void addUser(User user);

    void addUserForMenu(User user);

    void updateState(int state, int userId);

    int isAvailable(String name);

    List<User> searchUserByName(String name);

    void updateUser(int userId,String email,String mobile);

    void deleteUser(int id);

    void updateUserRole(int userId,int roleId);

}
