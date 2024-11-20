package com.nowcoder.community.util;


import com.nowcoder.community.entity.User;
import org.springframework.stereotype.Component;

// 持有用户信息，用于代替session对象？说我们在项目中一般不喜欢使用session对象是什么意思啊？
@Component
public class HostHolder {

    // TODO ThreadLocal 线程隔离? 什么叫线程隔离啊？ 根据线程取数据吗？
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
