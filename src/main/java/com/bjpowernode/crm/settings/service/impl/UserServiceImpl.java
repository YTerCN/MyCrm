package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.excaption.LoginExcaption;
import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author  都钦宗
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    public User login(String loginAct, String loginPwd, String ip) throws LoginExcaption {
        User user = null;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        user = dao.login(map);

        if (user==null){
            throw new LoginExcaption("用户名密码错误");
        }

        if (user.getExpireTime().compareTo(DateTimeUtil.getSysTime())<0){
            throw new LoginExcaption("账号已过期");
        }

        if (user.getLockState().equals("0")){
            throw new LoginExcaption("账号被锁住，请联系管理员");
        }

        /*if (!user.getAllowIps().contains(ip)){
            throw new LoginExcaption("ip地址非法，请联系管理员");
        }*/
        return user;
    }

    public List<User> getUserList() {
        List<User> uList = null;
        uList = dao.getUserList();
        return uList;
    }
}
