package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.vo.PaginationVO;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    Boolean save(Activity activity);

    PaginationVO<Activity> activityList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> getUserListAndActivity(String id);

    boolean update(Activity activity);

    Activity detail(String id);

    List<ActivityRemark> contentList(String id);

    boolean deleteRemark(String id);

    boolean updateRemark(ActivityRemark ar);

    boolean saveRemark(ActivityRemark ar);

    List<Activity> getActivityByClueId(String clueId);

    List<Activity> searchActivityText(Map<String, String> map);

    List<Activity> searchActivityByName(String name);
}
