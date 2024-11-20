package com.nowcoder.community;


import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = NowcoderApplication.class)
public class DiscussPostMapperTest {


    @Autowired
    DiscussPostMapper discussPostMapper;

    @Test
    public void insertTest() {
        DiscussPost post = new DiscussPost();

        post.setTitle("测试标题");
        post.setContent("测试内容：   post.setContent(\"测试内容\"这个错误提示 InvalidTestClassError: Invalid test class 表明 JUnit 没有找到可运行的测试方法。具体原因是因为 DiscussPostMapperTest 类中没有符合 JUnit 要求的测试方法);\n");
        post.setUserId(151);
        post.setCreateTime(new Date());

        discussPostMapper.insertDiscussPost(post);
    }
}
