package com.lpm.shop.server.imp;

import com.lpm.shop.entity.Role;
import com.lpm.shop.entity.RoleUnionRight;
import com.lpm.shop.mapper.RightsMapper;
import com.lpm.shop.mapper.RoleUnionRightMapper;
import com.lpm.shop.mapper.RolesMapper;
import com.lpm.shop.server.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Master
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RolesMapper rolesMapper;

    @Autowired
    RightsMapper rightsMapper;

    @Autowired
    RoleUnionRightMapper unionRightMapper;

    @Override
    public List getRoleAndRightList() {

        List<RoleUnionRight> unionList = unionRightMapper.getRolesUnionRightsList();
        List<Role> roleList = rolesMapper.getRoleList();
        List list = new ArrayList();
        for (Role role : roleList) {
            HashMap<String, Object> roleMap = new HashMap<>();
            roleMap.put("id", role.getId());
            roleMap.put("roleName", role.getRoleName());
            roleMap.put("roleDesc", role.getRoleDesc());
            roleMap.put("children", getZeroOfRight(unionList, role.getId()));
            list.add(roleMap);
        }
        return list;
    }

    @Override
    public void deleteRightFromRoleById(int id,int rightId) {
        unionRightMapper.deleteRightFromRoleById(id,rightId);
    }

    @Override
    public void addRole(String name, String desc) {
        rolesMapper.addRole(name,desc);
    }

    @Override
    public Map getRoleInfoById(int id) {
        Role roleInfo = rolesMapper.getRoleInfoById(id);
        HashMap<String,Object> map = new HashMap<>();
        map.put("roleId",roleInfo.getId());
        map.put("roleName",roleInfo.getRoleName());
        map.put("roleDesc",roleInfo.getRoleDesc());
        return map;
    }

    @Override
    public void updateRoleById(int id,String name,String desc) {
        rolesMapper.updateRoleById(id,name,desc);
    }

    @Override
    public void deleteRoleById(int id) {
        rolesMapper.deleteRoleById(id);
        unionRightMapper.deleteAllRightByRoleId(id);
    }

    @Override
    public Map<String, Object> getRoleById(int id) {
        List<RoleUnionRight> unionList = unionRightMapper.getRolesUnionRightsList();
        HashMap<String,Object> map = new HashMap<>();
        List allRights = getZeroOfRight(unionList, id);
        map.put("data",allRights);
        return map;
    }

    @Override
    public void addRightById(int roleId,int rightId) {
        unionRightMapper.addRightById(roleId,rightId);
    }

    @Override
    public void deleteAllRightByRoleId(int roleId) {
        unionRightMapper.deleteAllRightByRoleId(roleId);
    }


    /**
     * 获取第一级权限列表
     * @param unionList
     * @param roleId
     * @return
     */
    private List getZeroOfRight(List<RoleUnionRight> unionList, int roleId) {
        List all = new ArrayList();
        for (RoleUnionRight roleUnionRight : unionList) {
            if (roleUnionRight.getRoleId() != roleId){
                continue;
            }else if (roleUnionRight.getLevel().equals("0")){
                HashMap<String,Object> map = new HashMap<>();
                map.put("id",roleUnionRight.getRightId());
                map.put("authName",roleUnionRight.getAuthName());
                map.put("path",roleUnionRight.getPath());
                map.put("children",getOneOfRight(unionList, roleId, roleUnionRight.getRightId()));
                all.add(map);
            }
        }
        return all;
    }

    /**
     * 获取第二级权限列表
     * @param unionList
     * @param roleId
     * @param rightId
     * @return
     */
    private List getOneOfRight(List<RoleUnionRight> unionList, int roleId, int rightId) {
        List all = new ArrayList();

        for (RoleUnionRight roleUnionRight : unionList) {
            if (roleUnionRight.getRoleId() != roleId){
                continue;
            }else if (roleUnionRight.getLevel().equals("1") && roleUnionRight.getPid() == rightId){
                HashMap<String,Object> map = new HashMap<>();
                map.put("id",roleUnionRight.getRightId());
                map.put("authName",roleUnionRight.getAuthName());
                map.put("path",roleUnionRight.getPath());
                map.put("children",getTwoOfRight(unionList, roleId, roleUnionRight.getRightId()));
                all.add(map);
            }
        }
        return all;
    }

    /**
     * 获取第三级权限列表
     * @param unionList
     * @param roleId
     * @param rightId
     * @return
     */
    private List getTwoOfRight(List<RoleUnionRight> unionList, int roleId, int rightId) {
        List all = new ArrayList();
        for (RoleUnionRight roleUnionRight : unionList) {
            if (roleUnionRight.getRoleId() != roleId){
                continue;
            }else if (roleUnionRight.getLevel().equals("2") && roleUnionRight.getPid() == rightId){
                HashMap<String,Object> map = new HashMap<>();
                map.put("id",roleUnionRight.getRightId());
                map.put("authName",roleUnionRight.getAuthName());
                map.put("path",roleUnionRight.getPath());
                all.add(map);
            }
        }
        return all;
    }

}
