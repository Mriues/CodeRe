package com.lpm.shop.controller;

import com.lpm.shop.entity.User;
import com.lpm.shop.server.imp.MenuServiceImpl;
import com.lpm.shop.server.imp.UserServiceImpl;
import com.lpm.shop.util.JsonUtils;
import com.lpm.shop.util.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Master
 */
@Controller
public class LoginAndRegister {

    @Autowired
    JsonUtils jsonUtils;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    MenuServiceImpl menuService;

    @PostMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody String data,HttpSession session){
        HashMap<String,Object> map = new HashMap();
        String name = jsonUtils.conversionStr(data,"username");
        String pwd = jsonUtils.conversionStr(data,"password");
        User user = userService.queryUserByName(name);
        //int available = userService.isAvailable(name);
        if (user == null){
            map.put("success",false);
            map.put("msg","登录失败,用户不存在");
            return map;
        }
        if (user != null) {
            int available = userService.isAvailable(name);
            if (available == 0) {
                map.put("success", false);
                map.put("msg", "该用户不可用");
                return map;
            }else if (user.getPassword().equals(DigestUtils.md5Hex(pwd).toLowerCase())){
                map.put("success",true);
                map.put("msg","登录成功！");
                session.setAttribute("roleId",user.getRoleId());
                map.put("token",jwtUtils.createToken(name));
            }else {
                map.put("msg","密码错误请重新输入");
                map.put("success",false);
                return map;
            }
        }
        return map;
    }

    @RequestMapping("/register")
    @ResponseBody
    public Map<String,Object> register(@RequestBody String data){
        HashMap<String,Object> map = new HashMap<>();
        String name = jsonUtils.conversionStr(data,"name");
        String pwd = jsonUtils.conversionStr(data,"pwd");
        User user = userService.queryUserByName(name);
        if (user == null){
            Date time = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(time);
            String passwordMd5 = DigestUtils.md5Hex(pwd).toLowerCase();
            userService.addUser(name,passwordMd5,date);
            map.put("msg","注册成功，即将返回登录页面");
            map.put("success",true);
        }else {
            map.put("msg","用户名已存在，请重新注册");
            map.put("success",false);
        }
        return map;
    }


    /*@RequestMapping("/test")
    @ResponseBody
    public void test(@RequestBody HashMap hashMap){
        System.out.println(hashMap.get("name") + "---" + hashMap.get("pwd"));
    }*/
}
