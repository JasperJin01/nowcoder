package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;


public class AlphaConfig {
    // NOTE 这里在函数上面加上 @Bean 有什么用啊？
    //
    @Bean
    public SimpleDateFormat simpleDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
}
