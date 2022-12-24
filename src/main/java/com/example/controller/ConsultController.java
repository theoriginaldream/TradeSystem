package com.example.controller;

import com.example.pojo.Consult;
import com.example.pojo.Reply;
import com.example.pojo.User;
import com.example.service.ConsultService;
import com.example.service.ReplyService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/consult")
public class ConsultController {

    @Autowired
    @Qualifier("consultServiceImpl")
    private ConsultService consultService;

    @Autowired
    @Qualifier("replyServiceImpl")
    private ReplyService replyService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping("/add")
    @ResponseBody
    public String addConsult(@RequestParam("consult") String consult, HttpSession session){
        String admin = (String) session.getAttribute("admin");
        Consult consult1 = new Consult();
        consult1.setConsult(consult);
        consult1.setUserid(admin);
        consult1.setDatetime(new Date());
        consultService.addConsult(consult1);
        return "success";
    }

    @RequestMapping("/show")
    @ResponseBody
    public List<Consult> showAllConsult(HttpSession session){
        String admin = (String) session.getAttribute("admin");
        if (admin.equals("00001")){
            List<Consult> consults = consultService.queryAllConsult();
            return consults;
        }else {
            return null;
        }

    }

    @RequestMapping(value = "/my",method = RequestMethod.GET)
    @ResponseBody
    public List<Consult> showMyConsult(HttpSession session){
        String admin = (String) session.getAttribute("admin");
        List<Consult> consults = consultService.queryConsultByUserId(admin);
        return consults;
    }

    @RequestMapping(value = "/delete/{consultid}",method = RequestMethod.POST)
    @ResponseBody
    public String deleteConsult(@PathVariable("consultid") String consultid, HttpSession session){
        consultService.deleteConsult(Integer.parseInt(consultid));
        return "success";
    }

    @RequestMapping(value = "/add/reply",method = RequestMethod.POST)
    @ResponseBody
    public String CustomerReply(@RequestParam("reply") String reply, @RequestParam("consultid") String consultid,HttpSession session){
        String admin = (String) session.getAttribute("admin");
        if (admin.equals("00001")){
            if (!consultid.equals("00001")){
                Reply reply1 = new Reply();
                reply1.setReply(reply);
                reply1.setConsultid(Integer.parseInt(consultid));
                reply1.setUserid(consultService.queryConsultById(Integer.parseInt(consultid)).getUserid());
                reply1.setDatetime(new Date());
                replyService.addReply(reply1);

                return "success";
            }else {
                for (User user : userService.queryAllUser()) {
                    Reply reply1 = new Reply();
                    reply1.setReply(reply);
                    reply1.setConsultid(0);
                    reply1.setUserid(user.getUserid());
                    reply1.setDatetime(new Date());
                    replyService.addReply(reply1);
                }

                return "success";
            }

        }else {
            return "error";
        }

    }

    @RequestMapping(value = "/mine",method = RequestMethod.GET)
    @ResponseBody
    public List<Reply> queryReplyToMe(HttpSession session){
        String admin = (String) session.getAttribute("admin");
        List<Reply> replies = replyService.queryReplyByUserId(admin);

        return replies;
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public List<Reply> queryAllReply(HttpSession session){
        String admin = (String) session.getAttribute("admin");

        if (admin.equals("00001")){
            List<Reply> replies = replyService.queryAllReply();
            return replies;
        }else {
            return null;
        }
    }

}
