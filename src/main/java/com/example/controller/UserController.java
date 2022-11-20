package com.example.controller;

import com.example.pojo.HeadPicture;
import com.example.pojo.User;
import com.example.service.HeadPictureService;
import com.example.service.ShoppingCartService;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
@Api
public class UserController {
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("headPictureServiceImpl")
    private HeadPictureService headPictureService;

    @Autowired
    @Qualifier("shoppingCartServiceImpl")
    private ShoppingCartService shoppingCartService;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> userShow(HttpSession session, Model model){
        String userid = (String) session.getAttribute("admin");
        User user = userService.queryUserByName(userid);

        Map<String,Object> map = new HashMap<>();

        map.put("user",user);
//        model.addAttribute("user",user);

        if (headPictureService.queryHeadPicture(userid)!=null){
//            model.addAttribute("headpicture",headPictureService.queryHeadPicture(userid).getHeadpicture());
            map.put("headpicture",headPictureService.queryHeadPicture(userid));
        }
        return map;
    }

    @RequestMapping(value = "/to/update/{userid}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> toUpdateUser(@PathVariable("userid") String userid, Model model){
        User user = userService.queryUserByName(userid);

        Map<String,Object> map = new HashMap<>();

        map.put("user",user);

//        model.addAttribute("user",user);

        if (headPictureService.queryHeadPicture(userid)!=null){
//            model.addAttribute("headpicture",headPictureService.queryHeadPicture(userid).getHeadpicture());
            map.put("headpicture",headPictureService.queryHeadPicture(userid));
        }
        return map;
    }

//    @RequestMapping("/toUpdatePassword")
//    public String toUpdatePassword(){
//        return "updatePwd";
//    }

    @RequestMapping(value = "/update/pwd",method = RequestMethod.PUT)
    @ResponseBody
    public String updatepwd(@RequestParam("oldpwd") String oldpwd,@RequestParam("password") String password,@RequestParam("password2") String password2,HttpSession session,Model model){
        String userid = (String) session.getAttribute("admin");
        User user = userService.queryUserByName(userid);
        String oldPassword = user.getPassword();
        if (oldPassword.equals(oldpwd)){
            if (password.equals(password2)){
                user.setPassword(password);
                userService.updateUser(user);
                return "success";
            }else {
//                model.addAttribute("error","密码不一致");
                return "error";
            }
        }else {
//            model.addAttribute("error","密码不正确");
            return "error";
        }
    }

    @RequestMapping(value = "/update/user",method = RequestMethod.PUT)
    @ResponseBody
    public String updateUser(@RequestParam("userid") String userid, @RequestParam("name") String name, @RequestParam("schoolzone") String schoolzone, @RequestParam("introduce") String introduce, @RequestParam("headpicture") MultipartFile headpicture, HttpSession session, Model model) throws IOException {
        User user = userService.queryUserByName(userid);
        user.setName(name);
        user.setSchoolzone(schoolzone);
        user.setIntroduce(introduce);
        userService.updateUser(user);

        String oldFileName = headpicture.getOriginalFilename();

        String filePath = session.getServletContext().getRealPath("upload");

        if (headpicture!=null && oldFileName!=null && oldFileName.length()>0) {
            String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));

            if (headPictureService.queryHeadPicture(userid)!=null){
                String oldHeadPicture = headPictureService.queryHeadPicture(userid).getHeadpicture();

                headPictureService.updateHeadPicture(userid,newFileName);

                String oldHeadPicturePath = filePath + "/" + oldHeadPicture;
                File oldFile = new File(oldHeadPicturePath);
                if (oldFile.exists()){
                    boolean delete = oldFile.delete();
                }
            }else {
                headPictureService.addHeadPicture(userid,newFileName);
            }

            File newFile = new File(filePath + "/" + newFileName);
            headpicture.transferTo(newFile);
        }

        return "success";
    }

    @RequestMapping(value = "/query/{userid}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryOtherUser(@PathVariable("userid") String userid,Model model){
        User user = userService.queryUserByName(userid);

        Map<String,Object> map = new HashMap<>();

        map.put("user",user);
//        model.addAttribute("user",user);

        if (headPictureService.queryHeadPicture(userid)!=null){
//            model.addAttribute("headpicture",headPictureService.queryHeadPicture(userid).getHeadpicture());
            map.put("headpicture",headPictureService.queryHeadPicture(userid));
        }
        return map;
    }

}
