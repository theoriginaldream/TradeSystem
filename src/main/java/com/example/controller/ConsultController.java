package com.example.controller;

import com.example.pojo.Consult;
import com.example.pojo.Reply;
import com.example.service.ConsultService;
import com.example.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        if (admin.equals("001")){
            List<Consult> consults = consultService.queryAllConsult();
            return consults;
        }else {
            return null;
        }

    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteConsult(@RequestParam("consultid") int consultid, HttpSession session){
        consultService.deleteConsult(consultid);
        return "success";
    }

    @RequestMapping(value = "/add/reply",method = RequestMethod.POST)
    @ResponseBody
    public String CustomerReply(@RequestParam("reply") String reply,
                                @RequestParam("consultid") int consultid,HttpSession session){
        String admin = (String) session.getAttribute("admin");
        if (admin.equals("001")){
            Reply reply1 = new Reply();
            reply1.setReply(reply);
            reply1.setConsultid(consultid);
            reply1.setUserid(consultService.queryConsultById(consultid).getUserid());
            reply1.setDatetime(new Date());
            replyService.addReply(reply1);

            return "success";
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

        if (admin.equals("001")){
            List<Reply> replies = replyService.queryAllReply();
            return replies;
        }else {
            return null;
        }
    }

}
