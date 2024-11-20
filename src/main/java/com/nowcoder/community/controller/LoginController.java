package com.nowcoder.community.controller;

import com.google.code.kaptcha.Producer;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@Controller
public class LoginController implements CommunityConstant {

    // TODO 这里的 private static final 为啥要是final？
    //  关于log的一些问题。比如引用来自哪个包？记录的log信息存在了哪里？等等
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userSrv;

    //  生成登录验证码
    @Autowired
    private Producer kaptchaProducer;


    // NOTE @Value("${server.servlet.context-path}") value关键字，获取application.properties配置文件中的参数
    @Value("${server.servlet.context-path}")
    private String contextPath;


    @GetMapping("/register")
    public String getRegisterPage() {
        return "/site/register";
    }

    @PostMapping("/register")
    public String register(ModelMap model, User user) {
        System.out.println("controller user: " + user);
        Map<String, Object> map = userSrv.register(user);
        if (map == null || map.isEmpty()) { // 表示注册成功
            model.addAttribute("msg", "注册成功,我们已经向您的邮箱发送了一封激活邮件,请尽快激活!");
            model.addAttribute("target", "/index");
            return "/site/operate-result";
        } else {
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            model.addAttribute("emailMsg", map.get("emailMsg"));
            return "/site/register";
        }
    }



    @GetMapping("/login")
    public String getLoginPage() {
        System.out.println("func get login");
        return "/site/login";
    }

    // NOTE 前端的 Thymeleaf 如何调用参数这些值？
    //  ① 如果参数是一个对象，MVC会自动将对象注入到 Model 中，前端通过 <对象名>.<属性名> 调用
    //  ② 如果是值参数，不会注入Model；值参数都是存在于 request 对象中的，前端可以通过 request 取值。调用方法为 `param.username`
    // FIXME 这个 值参数都是存在于 request 对象中的 实际上是怎么存在的呢？有没有更细节具体的一个例子？
    @PostMapping("/login")
    public String login(String username, String password, String code, boolean rememberme,
                        ModelMap model, HttpSession session, HttpServletResponse response) {
        System.out.println("func post login\n username: " + username + "\t\t password: " + password);
        // NOTE response 用来保存cookie: response.addCookie(cookie)
        // 检查验证码是否正确
        String kaptcha = (String) session.getAttribute("kaptcha");
        if (StringUtils.isBlank(kaptcha) || StringUtils.isBlank(code) || !kaptcha.equalsIgnoreCase(code)) {
            model.addAttribute("codeMsg", "验证码不正确!");
            return "/site/login";
        }

        // 检查账号,密码是否正确
        int expiredSeconds = rememberme ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = userSrv.login(username, password, expiredSeconds);
        if (map.containsKey("ticket")) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath(contextPath);
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return "redirect:/index";
            // NOTE 【重要】重定向 和 直接返回视图 /index 的区别？
            //  直接返回视图是通过魔板引擎加载视图了，client请求->server加载视图并返回 （一步过程）
            //  重定向: client请求->server的controller相应为重定向，则会向client发送一个HTTP状态码为302的响应，同时携带一个URL
            //         client请求URL，同时浏览器的地址栏路径也更新成URL了->server再按照这个URL进行相应返回页面（两步过程）
            // FIXME 这里不太理解：
            //  重定向避免重复提交：如果用户在表单提交后刷新页面，使用重定向可以防止表单数据被重复提交。
            //  传递数据: 不能直接在重定向中传递数据，除非通过查询参数或是再次利用会话（session）或其他方式。
            // TODO 一定要好好研究一下，在浏览器的地址栏中，点击 回退<- 或者 -> 和 刷新 的区别
        } else {
            System.out.println("usernameMag: " + map.get("usernameMsg"));
            model.addAttribute("usernameMsg", map.get("usernameMsg"));
            model.addAttribute("passwordMsg", map.get("passwordMsg"));
            return "/site/login";
        }

    }

    @GetMapping("/activation/{userId}/{code}")
    public String activation(ModelMap model, @PathVariable("userId") int userId, @PathVariable("code") String code) {
        int result = userSrv.activation(userId, code);
        if (result == ACTIVATION_SUCCESS) {
            model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
            model.addAttribute("target", "/login");
        } else if (result == ACTIVATION_REPEAT) {
            model.addAttribute("msg", "无效操作,该账号已经激活过了!");
            model.addAttribute("target", "/index");
        } else {
            model.addAttribute("msg", "激活失败,您提供的激活链接不正确!");
            model.addAttribute("target", "/index");
        }
        return "/site/operate-result";
    }

    @GetMapping("/redirecttest01")
    public String redirectTest01() {
        return "site/login";
    }
    @GetMapping("/redirecttest02")
    public String redirectTest02() {
        return "redirect:login";
    }


    // NOTE 为啥是Response和session传参？
    //  将 kaptcha 生成的验证码照片通过流方式写入了response中（很神奇，原来照片可以这么传输）
    //  同时验证码 text 放到了session中（cookie和session都可以用来保存会话的信息，但是隐私信息不能放在客户端。
    //  cookie是保存在客户端的，session是保存在服务器的
    @GetMapping("/kaptcha")
    public void getKaptcha(HttpServletResponse response, HttpSession session) {
        System.out.println("func get kaptcha");
        // 生成验证码
        String text = kaptchaProducer.createText();
        System.out.println("kaptcha text: " + text);
        BufferedImage image = kaptchaProducer.createImage(text);

        // 将验证码存入session
        session.setAttribute("kaptcha", text);

        // 将图片输出给浏览器
        response.setContentType("image/png");
        try {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            System.out.println("响应验证码失败");
            logger.error("响应验证码失败:" + e.getMessage());
        }
    }





}
