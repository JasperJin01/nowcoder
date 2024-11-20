package com.nowcoder.community;

import com.nowcoder.community.controller.AlphaController;
import org.springframework.context.ApplicationContext;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.BeansException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class) // TODO 这个好像是Junit的注释
@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
class NowcoderApplicationTests implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Test
    public void testApplicationContext() {
        System.out.println(applicationContext);

        AlphaController alphaController = applicationContext.getBean(AlphaController.class);
        System.out.println(alphaController.hello());
    }


    @Test
    public void userTest() {

    }


}
