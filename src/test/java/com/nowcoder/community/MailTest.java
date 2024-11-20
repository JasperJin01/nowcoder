package com.nowcoder.community;


import com.nowcoder.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@RunWith(SpringRunner.class) // TODO 这个注释不加确实报错
@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
public class MailTest {

    @Autowired
    private MailClient mailClient;

    @Autowired // NOTE Thymeleaf模板引擎 手动引用
    private TemplateEngine templateEngine;

    @Test
    public void testTextMail() {
        mailClient.sendMail("timaxthu@gmail.com", "TEST", "hello world mailsrv");
    }


    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "Jasper");

        // TODO 这里的路径是从templates开始算的
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("timaxthu@gmail.com", "TEST html abc1", content);

    }

}
