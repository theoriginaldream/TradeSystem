package com.example.controller;

import com.example.pojo.Consult;
import com.example.service.ConsultService;
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
        List<Consult> consults = consultService.queryAllConsult();

        return consults;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public String deleteConsult(@RequestParam("consultid") int consultid, HttpSession session){
        consultService.deleteConsult(consultid);
        return "success";
    }
}
