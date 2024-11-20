package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    @Autowired
    private DiscussPostService discussPostSrv;

    @Autowired
    private UserService userSrv;

    @GetMapping("/index")
    public String getIndexPage(ModelMap modelMap, Page page) {
        // NOTE
        //  方法调用前,SpringMVC 会自动实例化 Model和Page,并将Page注入Model.
        //  所以,在thymeleaf中可以直接访问Page对象中的数据.
        // FIXME 如何在登录的时候获取用户的id？（我觉得这个问题的扩展就是说，前端页面点击例如我的主页等需要状态的请求时，是如何记住状态的？）
        page.setRows(discussPostSrv.findDiscussPostRows(0));
        page.setPath("/index");

        // 获取贴子（对应页的数量）
        List<DiscussPost> list = discussPostSrv.findDiscussPosts(0, page.getOffset(), page.getLimit());
        System.out.println("list.size:" + list.size());
        if (list == null) {
            System.out.println("list is null");
        }

        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost post: list) {
                Map<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userSrv.findUserById(post.getUserId()); // 这里又根据帖子中的usrid找了一下用户的信息
                map.put("user",user);
                discussPosts.add(map);
            }
        }

        modelMap.put("discussPosts", discussPosts);
        return "index";
    }
}
