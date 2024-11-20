package com.nowcoder.community;

import com.nowcoder.community.dao.LoginTicketMapper;
import com.nowcoder.community.entity.LoginTicket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class) // TODO 这个注释不加确实报错
@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
public class LoginTicketMapperTest {

    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Test
    public void insertLoginTicketTest() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1243);
        loginTicket.setTicket("abcfed");
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*600));
        loginTicket.setStatus(0);
        System.out.println("insert login ticket...");
        loginTicketMapper.insertLoginTicket(loginTicket);
        System.out.println("insert login ticket ok...");

    }

}
