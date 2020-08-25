package com.lpm.shop.controller;

import com.lpm.shop.server.imp.RightsServiceImpl;
import com.lpm.shop.server.imp.RoleServiceImpl;
import com.lpm.shop.util.JsonUtils;
import com.lpm.shop.util.MapResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RolesController {

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    RightsServiceImpl rightsService;

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    MapResultUtils mapResultUtils;

    @GetMapping("/roles")
    @ResponseBody
    public Map<String,Object> getRolesList(){
        List roleList = roleService.getRoleAndRightList();
        HashMap<String, Object> map = mapResultUtils.resultMap(roleList, 200, "获取角色列表成功");
        return map;
    }

    @DeleteMapping("/roles/{id}/rights/{rightId}")
    @ResponseBody
    public Map<String,Object> deleteRightFromRole(@PathVariable("id") int id,@PathVariable("rightId") int rightId){
        roleService.deleteRightFromRoleById(id,rightId);
        Map<String, Object> roleById = roleService.getRoleById(id);
        HashMap<String, Object> map = mapResultUtils.resultMap(roleById.get("data"), 204, "删除该权限成功");
        return map;
    }


    @GetMapping("/rights/tree")
    @ResponseBody
    public Map<String,Object> getRightsTree(){
        List rightsTree = rightsService.getRightsTree();
        HashMap<String, Object> map = mapResultUtils.resultMap(rightsTree, 200, "获取权限数据成功");
        return map;
    }


    @PostMapping("/roles/{roleId}/rights")
    @ResponseBody
    public Map<String,Object> addRoleRight(@PathVariable("roleId") int roleId,@RequestBody String data){
        String rids = jsonUtils.conversionStr(data, "rids");
        String[] split = rids.split(",");
        roleService.deleteAllRightByRoleId(roleId);
        for (String s : split) {
            int rightId = Integer.parseInt(s);
            roleService.addRightById(roleId,rightId);
        }
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "分配权限成功");
        return map;
    }

    @PostMapping("/roles")
    @ResponseBody
    public Map<String,Object> addRole(@RequestBody String data){
        String rolename = jsonUtils.conversionStr(data, "rolename");
        String roledesc = jsonUtils.conversionStr(data, "roledesc");
        roleService.addRole(rolename,roledesc);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "添加角色成功");
        return map;
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public Map<String,Object> getRoleInfoById(@PathVariable("id") int id){
        Map info = roleService.getRoleInfoById(id);
        HashMap<String, Object> map = mapResultUtils.resultMap(info, 200, "获取角色信息成功");
        return map;
    }

    @PutMapping("/roles/{roleid}")
    @ResponseBody
    public Map<String,Object> updateRoleById(@PathVariable("roleid") int roleId,@RequestBody String data){
        String roleName = jsonUtils.conversionStr(data, "rolename");
        String roleDesc = jsonUtils.conversionStr(data, "roledesc");
        roleService.updateRoleById(roleId,roleName,roleDesc);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "更新角色信息成功");
        return map;
    }

    @DeleteMapping("/roles/{id}")
    @ResponseBody
    public Map<String,Object> deleteRoleById(@PathVariable("id") int id){
        roleService.deleteRoleById(id);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 204, "删除角色成功");
        return map;
    }
}
