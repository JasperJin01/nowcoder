package com.nowcoder.community.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    public String hello() {
        return "hello world";
    }

    // NOTE HttpServletRequest 和 HttpServletResponse
    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取（client端的）请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            System.out.println(key + ": " + value);
        }

        // 返回相应数据
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write("<h1>HTTP alpha 论坛</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/student/{id}")
    public void student(@PathVariable("id")int id, // NOTE 这个PathVariable是必须的，没有会报错
                        HttpServletResponse response) {
        // NOTE Controller函数一定要进行响应
        //  在参数中添加了 HttpServletResponse 后，返回void函数，跳过MVC视图机制，这时 HttpServletResponse 的内容代表返回的内容
        //  如果不添加这个参数，利用MVC视图机制，String类型然后调用魔板引擎；或者添加@ResponseBody返回文本
        System.out.println(id);
    }

    // NOTE @RequestParam可以省略
    //  @RequestParam 用于从请求参数中获取值。
    //  可以把多个 RequestParam 统一到一个类中，然后在类前添加 @ModelAttribute 注释，要注意类的属性和 Param 要对应
    //  ① public String add(@ModelAttribute Course course, ModelMap map) { ... }
    //  ② public String add(@RequestParam("courseId") Integer courseId,
    //					  @RequestParam("title") String title,
    //					  @RequestParam("deptName") String deptName,
    //					  @RequestParam("credits") Integer credits,
    //					  ModelMap map) { ... }
    //  ③ 	public String add(Integer courseId,
    //					  String title,
    //					  String deptName,
    //					  Integer credits,
    //					  ModelMap map) { ... }


    // FIXME 还是不太理解 ServletRequest 请求的数据结构到底是啥样的？RequestParam参数放在哪里了？
    //  HTTP报文-> ServletRequest还是SpringMVC？ 这两个顺序是啥样的？-> Controller 方法 -> thymeleaf 魔板引擎

    // POST /course/add HTTP/1.1
    // Host: localhost:8080
    // Connection: keep-alive
    // Content-Length: 51
    // Cache-Control: max-age=0
    // sec-ch-ua: "Google Chrome";v="129", "Not=A?Brand";v="8", "Chromium";v="129"
    // sec-ch-ua-mobile: ?0
    // sec-ch-ua-platform: "macOS"
    // Content-Type: application/x-www-form-urlencoded
    // Upgrade-Insecure-Requests: 1
    // User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36
    // Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8
    // Sec-Fetch-Site: same-origin
    // Sec-Fetch-Mode: navigate
    // Sec-Fetch-User: ?1
    // Sec-Fetch-Dest: document
    // Referer: http://localhost:8080/course/add
    // Accept-Encoding: gzip, deflate, br
    // Accept-Language: zh-CN,zh;q=0.9
    //
    // courseId=123&title=Java+Programming&deptName=Computer+Science&credits=4
    // NOTE HTTP报文格式
    //  上面是一个完整的请求报文示例： 请求行、请求头、请求体
    //  第一行：请求行  POST /course/add HTTP/1.1 分别表示HTTP请求方法，请求的URI路径，协议版本
    //  从 Host 到 Accept-Language 的部分：请求头
    //  请求体（和请求头之间隔一行），包含了提交到服务器的实际数据
    //  GET 请求的数据附加在 URL 中（查询字符串部分），而 POST 请求的数据存放在请求体中

    // FIXME URL和URI的区别 （好像差不多？URL是URI的子集，URI是标识资源的标识符号，URL定义的更准确一些）




}
