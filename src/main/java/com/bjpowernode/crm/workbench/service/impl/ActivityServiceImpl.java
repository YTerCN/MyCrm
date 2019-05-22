package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.settings.dao.UserDao;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.dao.ActivityDao;
import com.bjpowernode.crm.workbench.dao.ActivityRemarkDao;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;
import com.bjpowernode.crm.workbench.service.ActivityService;
import org.apache.ibatis.jdbc.SQL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author  都钦宗
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityDao dao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao remarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    public Boolean save(Activity a) {
        boolean flag = true;
        int count = dao.save(a);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public PaginationVO<Activity> activityList(Map<String, Object> map) {
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        //查询总条数
       int total = dao.total(map);

        //查询每页的数据
        List<Activity> dataList = dao.activityList(map);
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    public boolean delete(String[] ids) {
        boolean flag = true;
        int count1 = dao.delete(ids);
        int count2 = remarkDao.delete(ids);
        if (count1==0){
            flag = false;
        }
        return flag;
    }

    public Map<String, Object> getUserListAndActivity(String id) {
        Map<String,Object> map = new HashMap<String, Object>();
        //查询用户列表
        List<User> uList = userDao.getUserList();
        //查询activity对象
        Activity a = dao.getById(id);
        //添加到map中
        map.put("uList", uList);
        map.put("a", a);
        return map;
    }

    public boolean update(Activity activity) {
        boolean flag = true;
        int count = dao.update(activity);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public Activity detail(String id) {
        Activity a = null;
        a = dao.detail(id);
        return a;
    }

    public List<ActivityRemark> contentList(String id) {
        List<ActivityRemark> arList = null;
        arList = remarkDao.contentList(id);
        return arList;
    }

    public boolean deleteRemark(String id) {
        boolean flag = true;
        int count = remarkDao.deleteRemark(id);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public boolean updateRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = remarkDao.updateRemark(ar);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public boolean saveRemark(ActivityRemark ar) {
        boolean flag = true;
        int count = remarkDao.saveRemark(ar);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public List<Activity> getActivityByClueId(String clueId) {
        List<Activity> aList = dao.getActivityByClueId(clueId);
        return aList;
    }

    public List<Activity> searchActivityText(Map<String, String> map) {
        List<Activity> aList = dao.searchActivityText(map);
        return aList;
    }

    public List<Activity> searchActivityByName(String name) {
        List<Activity> aList = dao.searchActivityByName(name);
        return aList;
    }
}
