package com.lpm.shop.mapper;

import com.lpm.shop.entity.RoleUnionRight;

import java.util.List;

public interface RoleUnionRightMapper {

    List<RoleUnionRight> getRolesUnionRightsList();

    void deleteRightFromRoleById(int id,int rightId);

    void deleteAllRightByRoleId(int roleId);

    void addRightById(int roleId,int rightId);

    //List<RoleUnionRight> getRightByRoleId(int roleId);

}
