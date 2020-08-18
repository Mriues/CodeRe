package com.lpm.shop.controller;

import com.lpm.shop.server.imp.RightsServiceImpl;
import com.lpm.shop.server.imp.RoleServiceImpl;
import com.lpm.shop.util.JsonUtils;
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

    @GetMapping("/roles")
    @ResponseBody
    public HashMap<String,Object> getRolesList(){
        List roleList = roleService.getRoleAndRightList();
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> metamap = new HashMap<>();
        metamap.put("status",200);
        metamap.put("msg","获取角色列表成功！");
        map.put("data",roleList);
        map.put("meta",metamap);
        return map;
    }

    @DeleteMapping("/roles/{id}/rights/{rightId}")
    @ResponseBody
    public Map deleteRightFromRole(@PathVariable("id") int id,@PathVariable("rightId") int rightId){
        roleService.deleteRightFromRoleById(id,rightId);
        Map<String, Object> roleById = roleService.getRoleById(id);
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",204);
        metaMap.put("msg","删除该权限成功！");
        map.put("meta",metaMap);
        map.put("data",roleById.get("data"));
        return map;
    }


    @GetMapping("/rights/tree")
    @ResponseBody
    public Map getRightsTree(){
        List rightsTree = rightsService.getRightsTree();
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",200);
        metaMap.put("msg","获取权限数据成功！");
        map.put("data",rightsTree);
        map.put("meta",metaMap);
        return map;
    }


    @PostMapping("/roles/{roleId}/rights")
    @ResponseBody
    public Map addRoleRight(@PathVariable("roleId") int roleId,@RequestBody String data){
        String rids = jsonUtils.conversionStr(data, "rids");
        String[] split = rids.split(",");
        roleService.deleteAllRightByRoleId(roleId);
        for (String s : split) {
            int rightId = Integer.parseInt(s);
            roleService.addRightById(roleId,rightId);
        }
        HashMap<String,Object> metaMap = new HashMap<>();
        HashMap<String,Object> map = new HashMap<>();
        metaMap.put("status",201);
        metaMap.put("msg","分配权限成功！");
        map.put("meta",metaMap);
        return map;
    }

    @PostMapping("/roles")
    @ResponseBody
    public Map addRole(@RequestBody String data){
        String rolename = jsonUtils.conversionStr(data, "rolename");
        String roledesc = jsonUtils.conversionStr(data, "roledesc");
        roleService.addRole(rolename,roledesc);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",201);
        metaMap.put("msg","添加角色成功");
        map.put("meta",metaMap);
        return map;
    }

    @GetMapping("/roles/{id}")
    @ResponseBody
    public Map getRoleInfoById(@PathVariable("id") int id){
        Map info = roleService.getRoleInfoById(id);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",200);
        metaMap.put("msg","获取角色信息成功");
        map.put("meta",metaMap);
        map.put("data",info);
        return map;
    }

    @PutMapping("/roles/{roleid}")
    @ResponseBody
    public Map updateRoleById(@PathVariable("roleid") int roleId,@RequestBody String data){
        String roleName = jsonUtils.conversionStr(data, "rolename");
        String roleDesc = jsonUtils.conversionStr(data, "roledesc");
        roleService.updateRoleById(roleId,roleName,roleDesc);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",201);
        metaMap.put("msg{","更新角色信息成功");
        map.put("meta",metaMap);
        return map;
    }

    @DeleteMapping("/roles/{id}")
    @ResponseBody
    public Map deleteRoleById(@PathVariable("id") int id){
        roleService.deleteRoleById(id);
        HashMap<String,Object> map = new HashMap<>();
        HashMap<String,Object> metaMap = new HashMap<>();
        metaMap.put("status",204);
        metaMap.put("msg","删除角色成功");
        map.put("meta",metaMap);
        return map;
    }
}
