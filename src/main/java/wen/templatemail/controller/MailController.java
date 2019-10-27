package wen.templatemail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import wen.templatemail.domain.MailDo;
import wen.templatemail.service.MailService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    @GetMapping("/templateMail")
    public void mail() {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("username", "test");
            MailDo mailDo = new MailDo("template mail", "", "820352756@qq.com");
            mailDo.setAttachment(map);
            mailService.sendTemplateMail(mailDo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
