package com.example.controller;

import com.example.pojo.Comment;
import com.example.pojo.CommentItem;
import com.example.pojo.HeadPicture;
import com.example.pojo.User;
import com.example.service.*;
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
import java.util.*;

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

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    @Autowired
    @Qualifier("itemServiceImpl")
    private ItemService itemService;

    @Autowired
    @Qualifier("requireItemServiceImpl")
    private RequireItemService requireItemService;

    @RequestMapping(value = "/user",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> userShow(HttpSession session, Model model){
        String userid = (String) session.getAttribute("admin");
        User user = userService.queryUserByName(userid);

        Map<String,Object> map = new HashMap<>();

        map.put("user",user);
//        model.addAttribute("user",user);

//            model.addAttribute("headpicture",headPictureService.queryHeadPicture(userid).getHeadpicture());
        map.put("headpicture",headPictureService.queryHeadPicture(userid));

        return map;
    }

//    @RequestMapping(value = "/to/update/{userid}",method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> toUpdateUser(@PathVariable("userid") String userid, Model model){
//        User user = userService.queryUserByName(userid);
//
//        Map<String,Object> map = new HashMap<>();
//
//        map.put("user",user);
//
////        model.addAttribute("user",user);
//
////            model.addAttribute("headpicture",headPictureService.queryHeadPicture(userid).getHeadpicture());
//        map.put("headpicture",headPictureService.queryHeadPicture(userid));
//
//        return map;
//    }

//    @RequestMapping("/toUpdatePassword")
//    public String toUpdatePassword(){
//        return "updatePwd";
//    }

    @RequestMapping(value = "/update/pwd",method = RequestMethod.POST)
    @ResponseBody
    public String updatepwd(@RequestParam("oldpwd") String oldpwd,@RequestParam("password") String password,
                            @RequestParam("password2") String password2,HttpSession session,Model model){
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

    @RequestMapping(value = "/update/user",method = RequestMethod.POST)
    @ResponseBody
    public String updateUser(@RequestParam("userid") String userid, @RequestParam(value = "name",required = false) String name,
                             @RequestParam(value = "schoolzone",required = false) String schoolzone,
                             @RequestParam(value = "introduce",required = false) String introduce,
                             @RequestParam(value = "headpicture",required = false) MultipartFile[] headpictures,
                             HttpSession session, Model model) throws IOException {
        String admin = (String) session.getAttribute("admin");
        MultipartFile headpicture = headpictures[0];
        System.out.println(headpicture);
        if (userid.equals(admin)){
            User user = userService.queryUserByName(userid);
            user.setName(name);
            user.setSchoolzone(schoolzone);
            user.setIntroduce(introduce);
            userService.updateUser(user);

            String oldFileName = headpicture.getOriginalFilename();

            String filePath = session.getServletContext().getRealPath("pictures");

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
        }else {
            return "error";
        }

    }

    @RequestMapping(value = "/query/{userid}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> queryOtherUser(@PathVariable("userid") String userid,Model model){
        User user = userService.queryUserByName(userid);

        Map<String,Object> map = new HashMap<>();

        map.put("user",user);
//        model.addAttribute("user",user);

//            model.addAttribute("headpicture",headPictureService.queryHeadPicture(userid).getHeadpicture());
        map.put("headpicture",headPictureService.queryHeadPicture(userid));

        return map;
    }

    @RequestMapping(value = "/item/message",method = RequestMethod.GET)
    @ResponseBody
    public List<CommentItem> queryItemMessage(HttpSession session){
        String admin = (String) session.getAttribute("admin");

        List<Comment> commentList = commentService.queryCommentByItemHost(admin);

        List<Comment> commentList2 = commentService.queryCommentRequireByItemHost(admin);

        List<CommentItem> comments = new ArrayList<>();

        int a = commentList.size();
        int b = commentList2.size();
        int flag = 0;

        for (int i = 0; i < a; i++) {
            Comment comment = commentList.get(i);
            for (int j = flag; j < b; j++){
                Comment comment1 = commentList2.get(j);
                if (comment.getDatetime().getTime()>comment1.getDatetime().getTime()){
                    CommentItem commentItem = new CommentItem();
                    commentItem.setCommentid(comment.getCommentid());
                    commentItem.setComment(comment.getComment());
                    commentItem.setDatetime(comment.getDatetime());
                    commentItem.setItemid(comment.getItemid());
                    commentItem.setUserid(comment.getUserid());
                    commentItem.setItemname(itemService.queryItemById(comment.getItemid()).getItemname());
                    comments.add(commentItem);
                    break;
                }else {
                    CommentItem commentItem = new CommentItem();
                    commentItem.setCommentid(comment1.getCommentid());
                    commentItem.setComment(comment1.getComment());
                    commentItem.setDatetime(comment1.getDatetime());
                    commentItem.setItemid(comment1.getItemid());
                    commentItem.setUserid(comment1.getUserid());
                    commentItem.setItemname(requireItemService.queryRequireItemById(comment1.getItemid()).getRitemname());
                    comments.add(commentItem);
                    flag++;
                }
            }
        }

        return comments;
    }

}
