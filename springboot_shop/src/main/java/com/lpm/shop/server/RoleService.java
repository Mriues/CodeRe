package com.lpm.shop.server;

import com.lpm.shop.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {

    List getRoleAndRightList();

    void deleteRightFromRoleById(int id,int rightId);

    Map getRoleById(int id);

    void addRightById(int roleId,int rightId);

    void deleteAllRightByRoleId(int roleId);

    void addRole(String name,String desc);

    Map getRoleInfoById(int id);

    void updateRoleById(int id,String name,String desc);

    void deleteRoleById(int id);

}
