package com.lpm.shop.controller;

import com.lpm.shop.entity.User;
import com.lpm.shop.server.imp.UserServiceImpl;
import com.lpm.shop.util.JsonUtils;
import com.lpm.shop.util.MapResultUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UsersController {

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MapResultUtils mapResultUtils;

    @GetMapping("/users")
    @ResponseBody
    public Map<String, Object> getUsers(@RequestParam(value = "query",required = false,defaultValue = "") String query,
                            @RequestParam("pagesize") int pagesize,
                            @RequestParam("pagenum") int pagenum){
        HashMap<String,Object> data = new HashMap<>();
        if (query.equals("") || query.isEmpty()){
            List all = userService.findAll();
            List resultList = new ArrayList();
            if (pagesize >= all.size()){
                for (int i = 0;i < all.size();i++){
                    resultList.add(all.get(i));
                }
            }else if (pagesize < all.size()){
                for (int i = 0;i < pagesize;i++){
                    if (pagesize*(pagenum-1) + i > all.size()-1){
                        break;
                    }
                    resultList.add(all.get(pagesize*(pagenum-1) + i));
                }
            }
            data.put("users",resultList);
            data.put("total",all.size());
            HashMap<String, Object> map = mapResultUtils.resultMap(data, 200, "获取用户列表成功");
            return map;
        }else {
            List list = userService.searchUserByName(query);
            data.put("users",list);
            data.put("total",list.size());
            HashMap<String, Object> map = mapResultUtils.resultMap(data, 200, "查询用户成功");
            return map;
        }
    }

    @PutMapping("/users/{id}/state/{type}")
    @ResponseBody
    public Map<String, Object> updateState(@PathVariable("id") int id, @PathVariable("type") Boolean type){
        if (type == false){
            userService.updateState(0,id);
        }else if (type == true){
            userService.updateState(1,id);
        }
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "更新用户状态成功");
        return map;
    }

    @PostMapping("/users")
    @ResponseBody
    public Map<String, Object> addUser(@RequestBody String data){
        String name = jsonUtils.conversionStr(data,"username");
        String pwd = jsonUtils.conversionStr(data,"password");
        String email = jsonUtils.conversionStr(data,"email");
        String mobile = jsonUtils.conversionStr(data,"mobile");
        String pwdMd5 = DigestUtils.md5Hex(pwd).toLowerCase();
        userService.addUserForMenu(name,pwdMd5,email,mobile);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "添加用户成功");
        return map;
    }

    @GetMapping("/users/{username}")
    @ResponseBody
    public Map<String, Object> getUserById(@PathVariable("username") String username){
        User user = userService.queryUserByName(username);
        HashMap<String,Object> datamap = new HashMap();
        datamap.put("id",user.getId());
        datamap.put("role_name",user.getRoleName());
        datamap.put("username",user.getName());
        datamap.put("create_time",user.getCreateTime());
        datamap.put("email",user.getEmail());
        datamap.put("mobile",user.getMobile());
        if (user.getState() == 1){
            datamap.put("mg_state",true);
        }else if (user.getState() == 0){
            datamap.put("mg_state",false);
        }
        HashMap<String, Object> map = mapResultUtils.resultMap(datamap, 200, "查询成功");
        return map;
    }

    @PutMapping("/users/{id}")
    @ResponseBody
    public Map<String, Object> updateUserInfo(@PathVariable("id") int id,@RequestBody String data){
        String email = jsonUtils.conversionStr(data,"email");
        String mobile = jsonUtils.conversionStr(data,"mobile");
        userService.updateUser(id,email,mobile);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "更新用户信息成功");
        return map;
    }

    @DeleteMapping("/users/{id}")
    @ResponseBody
    public Map<String, Object> deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 204, "删除用户成功");
        return map;
    }

    @PutMapping("/users/{userId}/role")
    @ResponseBody
    public Map<String, Object> updateUserRole(@PathVariable("userId") int userId,@RequestBody String data){
        int roleId = jsonUtils.conversionInt(data, "rid");
        userService.updateUserRole(userId,roleId);
        HashMap<String, Object> map = mapResultUtils.resultMap(null, 201, "更新角色成功");
        return map;
    }
}
