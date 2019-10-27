package wen.templatemail.service.impl;

import wen.templatemail.domain.MailDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import wen.templatemail.service.MailService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private final static Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendTemplateMail(MailDo mailDo) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setFrom(from);
            messageHelper.setTo(mailDo.getEmail());
            messageHelper.setSubject(mailDo.getTitle());

            Context context = new Context();
            context.setVariables(mailDo.getAttachment());

            String emailContent = templateEngine.process("/mail/mail", context);

            messageHelper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            log.error("模板邮件发送失败->message:{}", e.getMessage());
        }
    }
}
