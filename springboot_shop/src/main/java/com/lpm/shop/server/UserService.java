package com.lpm.shop.server;

import com.lpm.shop.entity.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User queryUserByName(String name);

    void addUser(String name,String password,String date);

    void addUserForMenu(String name,String password,String email,String mobile);

    void updateState(int state, int userId);

    int isAvailable(String name);

    List searchUserByName(String name);

    void updateUser(int id,String email,String mobile);

    void deleteUser(int id);

    void updateUserRole(int userId,int roleId);
}
