package com.nowcoder.community.controller;

import com.nowcoder.community.annotation.LoginRequired;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userSrv;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    private HostHolder hostHolder;

    @LoginRequired // 302状态码表示重定向
    @GetMapping("/setting")
    public String getSettingPage() {
        System.out.println("controller get setting ");
        return "/site/setting";
    }

    // TODO MultipartFile 是什么格式？
    @LoginRequired
    @PostMapping("/upload")
    public String uploadHeader(MultipartFile headerImage, ModelMap modelMap) {
        System.out.println("post upload header");

        if (headerImage==null) {
            modelMap.put("error", "您没有选择图片");
            return "/site/setting";
        }

        String fileName = headerImage.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        if (StringUtils.isBlank(suffix)) {
            modelMap.put("error", "文件格式不正确！");
            return "/site/setting";
        }

        // 生成随机文件名
        fileName = CommunityUtil.generateUUID() + suffix;
        // 确定文件存放的路径
        File dest = new File(uploadPath + "/" + fileName);
        try {
            // 存储文件
            headerImage.transferTo(dest);
        } catch (IOException e) {
            logger.error("上传文件失败: " + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常：", e);
        }

        // 更新当前用户的头像的路径（web访问路径）
        User user = hostHolder.getUser();
        String headerUrl = domain + contextPath + "/header/" + fileName;
        userSrv.updateHeader(user.getId(), headerUrl);
        return "redirect:/index";
    }

    @GetMapping("/header/{fileName}")
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 数据库中的 headerUrl http://localhost:8080/community/header/ab4cc75611ed4d3db9eaa5bb411e2a57.JPG
        System.out.println("filename: " + fileName);
        // 服务器存放路径
        fileName = uploadPath + "/" + fileName;
        System.out.println("filename: " + fileName);
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println("suffix:" + suffix);
        // 响应图片
        response.setContentType("image/" + suffix);
        try (
                FileInputStream fis = new FileInputStream(fileName);
                OutputStream os = response.getOutputStream();
        ) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
        }
    }





}
