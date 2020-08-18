package com.lpm.shop.mapper;

import com.lpm.shop.entity.Role;

import java.util.List;

public interface RolesMapper {

    List<Role> getRoleList();

    void addRole(String name,String desc);

    Role getRoleInfoById(int id);

    void updateRoleById(int id,String name,String desc);

    void deleteRoleById(int id);

}
