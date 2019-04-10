package me.myschools.demo.utils;

import cn.zucc.netdisc.domain.CurrentAccount;
import io.jsonwebtoken.Claims;

import java.util.LinkedHashMap;
import java.util.Map;

public class AccountUtil {
    public static  CurrentAccount parse(Claims claims){
        Map m=(LinkedHashMap)claims.get("user");
        CurrentAccount user=new CurrentAccount();
        user.setAccountId((int)m.get("accountId"));
        user.setUserId((int)m.get("userId"));
        user.setUserName((String) m.get("userName"));
        return user;
    }
}
