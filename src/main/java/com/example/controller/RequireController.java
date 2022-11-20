package com.example.controller;

import com.example.pojo.Comment;
import com.example.pojo.ItemPicture;
import com.example.pojo.RequireItem;
import com.example.service.CommentService;
import com.example.service.ItemPictureService;
import com.example.service.RequireItemService;
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
@RequestMapping("/requires")
@Api
public class RequireController {

    @Autowired
    @Qualifier("requireItemServiceImpl")
    private RequireItemService requireItemService;

    @Autowired
    @Qualifier("itemPictureServiceImpl")
    private ItemPictureService itemPictureService;

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

//    @RequestMapping("/to/addRequire")
//    public String toAddRequireItem(){
//        return "addRequire";
//    }

    @RequestMapping(value = "/add/require",method = RequestMethod.POST)
    @ResponseBody
    public String addRequire(String ritemname, String detail, String price, MultipartFile[] pictures, HttpSession session) throws IOException {
        String host = (String) session.getAttribute("admin");
        RequireItem requireItem = new RequireItem();
        requireItem.setRitemname(ritemname);
        requireItem.setDetail(detail);
        requireItem.setPrice(price);
        requireItem.setHost(host);

        Date date = new Date();
        requireItem.setDate(date);

        requireItemService.addRequireItem(requireItem);

        ItemPicture itemPicture = new ItemPicture();
        itemPicture.setItemid(requireItem.getRitemid());
        int count = 0;

        for (MultipartFile picture : pictures) {
            count++;
            String oldFileName = picture.getOriginalFilename();

            String filePath = session.getServletContext().getRealPath("pictures");

            if (picture != null && oldFileName != null && oldFileName.length() > 0) {
                String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));

                File newFile = new File(filePath + "/" + newFileName);
                picture.transferTo(newFile);

                if (count==1){
                    itemPicture.setItempicture(newFileName);
                } else if (count==2) {
                    itemPicture.setItempicture2(newFileName);
                } else if (count==3) {
                    itemPicture.setItempicture3(newFileName);
                } else if (count==4) {
                    itemPicture.setItempicture4(newFileName);
                } else if (count==5) {
                    itemPicture.setItempicture5(newFileName);
                }
            }
        }

        itemPictureService.addItemPicture(itemPicture);

        return "success";
    }

    @RequestMapping(value = "/requires",method = RequestMethod.GET)
    @ResponseBody
    public List<RequireItem> AllRequires(Model model){
        List<RequireItem> requireItemList = requireItemService.queryAllRequireItem();

        for (RequireItem requireItem : requireItemList) {
            if (itemPictureService.queryItemPicture(requireItem.getRitemid())!=null){
                requireItem.setItemPicture(itemPictureService.queryItemPicture(requireItem.getRitemid()));
            }
        }

//        model.addAttribute("requireItemList",requireItemList);

        return requireItemList;
    }

    @RequestMapping(value = "/require/{ritemid}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> OneRequire(@PathVariable("ritemid") int ritemid, Model model){
        RequireItem requireItem = requireItemService.queryRequireItemById(ritemid);
        if (itemPictureService.queryItemPicture(ritemid) != null) {
            ItemPicture itemPicture = itemPictureService.queryItemPicture(ritemid);
            requireItem.setItemPicture(itemPicture);
        }

        Map<String,Object> map = new HashMap<>();

        map.put("requireItem",requireItem);

        List<Comment> comments = commentService.queryCommentByItemid(ritemid);

        map.put("comments",comments);
//        model.addAttribute("requireItem",requireItem);
//        model.addAttribute("comments",comments);

        return map;
    }

    @RequestMapping(value = "/to/update/{ritemid}",method = RequestMethod.GET)
    @ResponseBody
    public RequireItem toUpdateRequire(@PathVariable("ritemid") int ritemid,Model model){
        RequireItem requireItem = requireItemService.queryRequireItemById(ritemid);

        ItemPicture itemPicture = itemPictureService.queryItemPicture(requireItem.getRitemid());

        requireItem.setItemPicture(itemPicture);

//        model.addAttribute("requireItem",requireItem);

        return requireItem;
    }

    @RequestMapping(value = "/update/require",method = RequestMethod.POST)
    @ResponseBody
    public String updateRequire(@RequestParam("ritemid") int ritemid,@RequestParam("ritemname") String ritemname,@RequestParam("detail") String detail,@RequestParam("price") String price,@RequestParam("pictures") MultipartFile[] pictures,HttpSession session,Model model) throws IOException {
        RequireItem requireItem = requireItemService.queryRequireItemById(ritemid);
        requireItem.setRitemname(ritemname);
        requireItem.setDetail(detail);
        requireItem.setPrice(price);
        Date date = new Date();
        requireItem.setDate(date);
        requireItemService.updateRequireItem(requireItem);

        int count = 0;
        ItemPicture itemPicture = itemPictureService.queryItemPicture(ritemid);
        if (itemPicture!=null){
            for (MultipartFile picture : pictures) {
                count ++;
                String oldFileName = picture.getOriginalFilename();

                String filePath = session.getServletContext().getRealPath("pictures");

                if (picture != null && oldFileName != null && oldFileName.length() > 0) {
                    String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));

                    File newFile = new File(filePath + "/" + newFileName);
                    picture.transferTo(newFile);

                    if (count==1){
                        if (itemPicture.getItempicture()!=null){
                            String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture();
                            File oldfile = new File(oldItemPicturePath);
                            if (oldfile.exists()) {
                                oldfile.delete();
                            }
                        }
                        itemPicture.setItempicture(newFileName);
                    } else if (count==2) {
                        if (itemPicture.getItempicture2()!=null){
                            String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture2();
                            File oldfile = new File(oldItemPicturePath);
                            if (oldfile.exists()) {
                                oldfile.delete();
                            }
                        }
                        itemPicture.setItempicture2(newFileName);
                    } else if (count==3) {
                        if (itemPicture.getItempicture3()!=null){
                            String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture3();
                            File oldfile = new File(oldItemPicturePath);
                            if (oldfile.exists()) {
                                oldfile.delete();
                            }
                        }
                        itemPicture.setItempicture3(newFileName);
                    } else if (count==4) {
                        if (itemPicture.getItempicture4()!=null){
                            String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture4();
                            File oldfile = new File(oldItemPicturePath);
                            if (oldfile.exists()) {
                                oldfile.delete();
                            }
                        }
                        itemPicture.setItempicture4(newFileName);
                    } else if (count==5) {
                        if (itemPicture.getItempicture5()!=null){
                            String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture5();
                            File oldfile = new File(oldItemPicturePath);
                            if (oldfile.exists()) {
                                oldfile.delete();
                            }
                        }
                        itemPicture.setItempicture5(newFileName);
                    }
                }
            }
            itemPictureService.updateItemPicture(itemPicture);
        }else {
            itemPicture = new ItemPicture();
            for (MultipartFile picture : pictures) {
                count ++;
                String oldFileName = picture.getOriginalFilename();

                String filePath = session.getServletContext().getRealPath("pictures");

                if (picture != null && oldFileName != null && oldFileName.length() > 0) {
                    String newFileName = UUID.randomUUID() + oldFileName.substring(oldFileName.lastIndexOf("."));

                    File newFile = new File(filePath + "/" + newFileName);
                    picture.transferTo(newFile);
                    if (count==1){
                        itemPicture.setItempicture(newFileName);
                    } else if (count==2) {
                        itemPicture.setItempicture2(newFileName);
                    } else if (count==3) {
                        itemPicture.setItempicture3(newFileName);
                    } else if (count==4) {
                        itemPicture.setItempicture4(newFileName);
                    } else if (count==5) {
                        itemPicture.setItempicture5(newFileName);
                    }
                }
            }
            itemPictureService.addItemPicture(itemPicture);
        }

        return "success";
    }

    @RequestMapping(value = "/delete/{ritemid}",method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRequireItem(@PathVariable("ritemid") int ritemid,HttpSession session){
        requireItemService.deleteRequireItem(ritemid);
        ItemPicture itemPicture = itemPictureService.queryItemPicture(ritemid);

        String filePath = session.getServletContext().getRealPath("pictures");

        int count = 1;

        while (itemPicture!=null && count<=5){
            if (count==1){
                if (itemPicture.getItempicture()!=null){
                    String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture();
                    File oldfile = new File(oldItemPicturePath);
                    if (oldfile.exists()) {
                        oldfile.delete();
                    }
                }
            } else if (count==2) {
                if (itemPicture.getItempicture2()!=null){
                    String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture2();
                    File oldfile = new File(oldItemPicturePath);
                    if (oldfile.exists()) {
                        oldfile.delete();
                    }
                }
            } else if (count==3) {
                if (itemPicture.getItempicture3()!=null){
                    String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture3();
                    File oldfile = new File(oldItemPicturePath);
                    if (oldfile.exists()) {
                        oldfile.delete();
                    }
                }
            } else if (count==4) {
                if (itemPicture.getItempicture4()!=null){
                    String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture4();
                    File oldfile = new File(oldItemPicturePath);
                    if (oldfile.exists()) {
                        oldfile.delete();
                    }
                }
            } else if (count==5) {
                if (itemPicture.getItempicture5()!=null){
                    String oldItemPicturePath = filePath + "/" + itemPicture.getItempicture5();
                    File oldfile = new File(oldItemPicturePath);
                    if (oldfile.exists()) {
                        oldfile.delete();
                    }
                }
            }
            count ++;
        }

        itemPictureService.deleteItemPicture(ritemid);

        return "success";
    }

    @RequestMapping(value = "/mine",method = RequestMethod.GET)
    @ResponseBody
    public List<RequireItem> queryMyRequire(HttpSession session, Model model) {
        String host = (String) session.getAttribute("admin");

        List<RequireItem> requireItemList = requireItemService.queryRequireItemByHost(host);

        for (RequireItem requireItem : requireItemList) {
            if (itemPictureService.queryItemPicture(requireItem.getRitemid()) != null) {
                requireItem.setItemPicture(itemPictureService.queryItemPicture(requireItem.getRitemid()));
            }
        }

//        model.addAttribute("requireItemList", requireItemList);

        return requireItemList;
    }

    @RequestMapping(value = "/otherRequire/{userid}",method = RequestMethod.GET)
    @ResponseBody
    public List<RequireItem> queryOtherRequire(@PathVariable("userid") String userid,HttpSession session, Model model) {

        List<RequireItem> requireItemList = requireItemService.queryRequireItemByHost(userid);

        for (RequireItem requireItem : requireItemList) {
            if (itemPictureService.queryItemPicture(requireItem.getRitemid()) != null) {
                requireItem.setItemPicture(itemPictureService.queryItemPicture(requireItem.getRitemid()));
            }
        }

//        model.addAttribute("requireItemList", requireItemList);

        return requireItemList;
    }

    @RequestMapping(value = "/add/comment",method = RequestMethod.POST)
    @ResponseBody
    public String addComment(@RequestParam("itemid") int itemid,@RequestParam("comment") String comment, Model model, HttpSession session){
        String admin = (String) session.getAttribute("admin");

        Comment comment1 = new Comment();
        comment1.setItemid(itemid);
        comment1.setComment(comment);
        comment1.setUserid(admin);
        comment1.setDatetime(new Date());

        commentService.addComment(comment1);

        return "success";
    }
}
