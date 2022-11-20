package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/login")
@Api
public class LoginController{

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

//    @RequestMapping("/toLogin")
//    public String toLogin(){
//        return "userLogin";
//    }

    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public String Login(@RequestParam("phone") String phone,@RequestParam("password") String password, Model model, HttpSession session){
        User user = userService.queryUserByPhone(phone);
        if (user!=null){
            if (user.getPassword().equals(password)){
                session.setAttribute("admin",user.getUserid());
                return "success";
            }else {
                session.setAttribute("msg","密码错误");
                return "error";
            }
        }else {
            session.setAttribute("msg","该用户不存在");
            return "error";
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    @ResponseBody
    public String logout(HttpSession session){
        session.invalidate();
        return "logout";
    }

//    @RequestMapping("/toRegister")
//    public String toRegister(){
//        return "register";
//    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam("userid") String userid,@RequestParam("phone") String phone,@RequestParam("password") String password,@RequestParam("password2") String password2,Model model){
        if (userService.queryUserByName(userid)==null){
            if (userService.queryUserByPhone(phone)==null){
                if (password.equals(password2)){
                    User user = new User(userid,password,phone);
                    userService.addUser(user);
                    return "success";
                }else {
//                    model.addAttribute("error","密码不一致");
                    return "error";
                }
            }else {
//                model.addAttribute("error","该手机号已注册");
                return "error";
            }

        }else {
//            model.addAttribute("error","该用户已存在");
            return "error";
        }

    }

}
