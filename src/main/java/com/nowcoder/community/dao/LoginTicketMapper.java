package com.nowcoder.community.dao;

import com.nowcoder.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;



@Mapper
public interface LoginTicketMapper {
    // FIXME 这个@Options(useGeneratedKeys = true, keyProperty = "id")是干啥的呢？说是因为id是自动生成的要加，
    //  但是之前不是在properties里面写了一个true了吗？(mybatis.configuration.useGeneratedKeys=true)
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1==1 ",
            "</if>",
            "</script>"
    })
    int updateStatus(String ticket, int status);
}
