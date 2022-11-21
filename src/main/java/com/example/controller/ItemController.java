package com.example.controller;

import com.example.pojo.*;
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
@RequestMapping("/items")
@Api
public class ItemController {

    @Autowired
    @Qualifier("itemServiceImpl")
    private ItemService itemService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("itemPictureServiceImpl")
    private ItemPictureService itemPictureService;

    @Autowired
    @Qualifier("commentServiceImpl")
    private CommentService commentService;

    @Autowired
    @Qualifier("shoppingCartServiceImpl")
    private ShoppingCartService shoppingCartService;

//    @RequestMapping("/toAddItem")
//    public String toAddItem() {
//        return "addItem";
//    }

    @RequestMapping(value = "/add/item",method = RequestMethod.POST)
    @ResponseBody
    public String addItem(@RequestParam("itemname") String itemname, @RequestParam(value = "schoolzone",required = false) String schoolzone,
                          @RequestParam(value = "type",required = false) String type, @RequestParam(value = "detail",required = false) String detail,
                          @RequestParam(value = "price",required = false) String price,
                          @RequestParam(value = "picture1",required = false) MultipartFile picture1,
                          @RequestParam(value = "picture2",required = false) MultipartFile picture2,
                          @RequestParam(value = "picture3",required = false) MultipartFile picture3,
                          @RequestParam(value = "picture4",required = false) MultipartFile picture4,
                          @RequestParam(value = "picture5",required = false) MultipartFile picture5,
                          HttpSession session) throws IOException {
        Item item = new Item();
        item.setItemname(itemname);
        item.setSchoolzone(schoolzone);
        item.setType(type);
        item.setDetail(detail);
        item.setPrice(price);
        String host = (String) session.getAttribute("admin");
        item.setHost(host);
        System.out.println(host);
        itemService.addItem(item);

        ItemPicture itemPicture = new ItemPicture();
        itemPicture.setItemid(item.getItemid());
        int count = 0;

        for (MultipartFile picture : Arrays.asList(picture1,picture2,picture3,picture4,picture5)) {
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

    @RequestMapping(value = "/items",method = RequestMethod.GET)
    @ResponseBody
    public List<Item> AllItems(Model model) {
        List<Item> itemList = itemService.queryAllItem();

        for (Item item : itemList) {
            item.setItempicture(itemPictureService.queryItemPicture(item.getItemid()));
        }

//        model.addAttribute("itemList", itemList);

        return itemList;
    }

    @RequestMapping(value = "/item/{itemid}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> OneItem(@PathVariable("itemid") int itemid,Model model){
        Item item = itemService.queryItemById(itemid);

        ItemPicture itemPicture = itemPictureService.queryItemPicture(itemid);
        item.setItempicture(itemPicture);

        Map<String,Object> map =  new HashMap<>();

        map.put("item",item);
//        model.addAttribute("item",item);

        User user = userService.queryUserByName(item.getHost());
        map.put("user",user);

        List<Comment> comments = commentService.queryCommentByItemid(itemid);
//        model.addAttribute("comments",comments);
        map.put("comments",comments);

        return map;
    }

    @RequestMapping(value = "/to/update/{itemid}",method = RequestMethod.GET)
    @ResponseBody
    public Item toUpdateItem(@PathVariable("itemid") int itemid, Model model) {
        Item item = itemService.queryItemById(itemid);

        ItemPicture itemPicture = itemPictureService.queryItemPicture(itemid);

        item.setItempicture(itemPicture);

//        model.addAttribute("item", item);

        return item;
    }

    @RequestMapping(value = "/update/item",method = RequestMethod.POST)
    @ResponseBody
    public String updateItem(@RequestParam("itemid") int itemid, @RequestParam("itemname") String itemname,
                             @RequestParam(value = "schoolzone",required = false) String schoolZone,
                             @RequestParam(value = "type",required = false) String type,
                             @RequestParam(value = "detail",required = false) String detail,
                             @RequestParam(value = "price",required = false) String price,
                             @RequestParam(value = "picture1",required = false) MultipartFile picture1,
                             @RequestParam(value = "picture2",required = false) MultipartFile picture2,
                             @RequestParam(value = "picture3",required = false) MultipartFile picture3,
                             @RequestParam(value = "picture4",required = false) MultipartFile picture4,
                             @RequestParam(value = "picture5",required = false) MultipartFile picture5,
                             HttpSession session) throws IOException {
        Item item = itemService.queryItemById(itemid);
        item.setItemname(itemname);
        item.setSchoolzone(schoolZone);
        item.setType(type);
        item.setDetail(detail);
        item.setPrice(price);
        itemService.updateItem(item);

//        List<MultipartFile> pictures = Stream.of(itempicture,itempicture2,itempicture3,itempicture4,itempicture5).collect(Collectors.toList());
//        ArrayList<MultipartFile> pictures = new ArrayList<>(Arrays.asList(itempicture, itempicture2, itempicture3, itempicture4, itempicture5));

        int count = 0;
        ItemPicture itemPicture = itemPictureService.queryItemPicture(itemid);
        if (itemPicture!=null){
            for (MultipartFile picture : Arrays.asList(picture1,picture2,picture3,picture4,picture5)) {
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
            itemPicture.setItemid(itemid);
            for (MultipartFile picture : Arrays.asList(picture1,picture2,picture3,picture4,picture5)) {
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

    @RequestMapping(value = "/delete/{itemid}",method = RequestMethod.POST)
    @ResponseBody
    public String deleteItem(@PathVariable("itemid") int itemid,HttpSession session){
        itemService.deleteItem(itemid);
        ItemPicture itemPicture = itemPictureService.queryItemPicture(itemid);

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

        itemPictureService.deleteItemPicture(itemid);

        return "success";
    }

    @RequestMapping(value = "/query/zone",method = RequestMethod.GET)
    @ResponseBody
    public List<Item> queryItemsByType(@RequestParam("schoolZone") String schoolZone, Model model) {
        List<Item> itemList = itemService.queryItemByType(schoolZone);

        for (Item item : itemList) {
            item.setItempicture(itemPictureService.queryItemPicture(item.getItemid()));
        }

//        model.addAttribute("itemList", itemList);

        return itemList;
    }

    @RequestMapping(value = "/query/name",method = RequestMethod.GET)
    @ResponseBody
    public List<Item> queryItemsByName(@RequestParam("query") String query, Model model) {
        List<Item> items1 = itemService.queryItemByAllName(query);
        HashMap<Integer, Item> itemMap = new HashMap<>();
        for (Item item : items1) {
            itemMap.put(item.getItemid(), item);
        }
        List<Item> items2 = itemService.queryItemByName(query);
        for (Item item : items2) {
            itemMap.putIfAbsent(item.getItemid(), item);
        }
        List<Item> itemList = new ArrayList<>(itemMap.values());

        for (Item item : itemList) {
            item.setItempicture(itemPictureService.queryItemPicture(item.getItemid()));
        }

//        model.addAttribute("itemList", itemsList);
        return itemList;
    }

    @RequestMapping(value = "/mine",method = RequestMethod.GET)
    @ResponseBody
    public List<Item> queryMyItem(HttpSession session, Model model) {
        String host = (String) session.getAttribute("admin");

        List<Item> itemList = itemService.queryItemByHost(host);

        for (Item item : itemList) {
            item.setItempicture(itemPictureService.queryItemPicture(item.getItemid()));
        }

//        model.addAttribute("itemList", itemList);

        return itemList;
    }

    @RequestMapping(value = "/otherItem/{userid}",method = RequestMethod.GET)
    @ResponseBody
    public List<Item> queryOtherItem(@PathVariable("userid") String userid, HttpSession session, Model model) {

        List<Item> itemList = itemService.queryItemByHost(userid);

        for (Item item : itemList) {
            item.setItempicture(itemPictureService.queryItemPicture(item.getItemid()));
        }

//        model.addAttribute("itemList", itemList);

        return itemList;
    }

    @RequestMapping(value = "/add/comment",method = RequestMethod.POST)
    @ResponseBody
    public String addComment(@RequestParam("itemid") int itemid, @RequestParam("comment") String comment, Model model, HttpSession session){
        String admin = (String) session.getAttribute("admin");

        Comment comment1 = new Comment();
        comment1.setItemid(itemid);
        comment1.setComment(comment);
        comment1.setUserid(admin);
        comment1.setDatetime(new Date());

        commentService.addComment(comment1);

        return "success";
    }

    @ResponseBody
    @RequestMapping(value = "/add/shoppingCart/{itemid}",method = RequestMethod.POST)
    public String addShoppingCart(@PathVariable("itemid") int itemid, HttpSession session, Model model){
        String admin = (String) session.getAttribute("admin");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setItemid(itemid);
        shoppingCart.setUserid(admin);
        shoppingCart.setDate(new Date());

        shoppingCartService.addShoppingCart(shoppingCart);

        return "success";
    }

    @RequestMapping(value = "/myShoppingCart",method = RequestMethod.GET)
    @ResponseBody
    public List<Item> myShoppingCart(HttpSession session,Model model){
        String admin = (String) session.getAttribute("admin");

        List<ShoppingCart> shoppingCarts = shoppingCartService.queryShoppingCartByUserId(admin);

        ArrayList<Item> itemList = new ArrayList<>();

        for (ShoppingCart shoppingCart : shoppingCarts) {
            Item item = itemService.queryItemById(shoppingCart.getItemid());
            item.setItempicture(itemPictureService.queryItemPicture(item.getItemid()));
            itemList.add(item);
        }

//        model.addAttribute("itemList",itemList);

        return itemList;
    }
}
