package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {
    int save(Activity activity);

    List<Activity> activityList(Map<String, Object> map);

    int total(Map<String, Object> map);

    int delete(String[] ids);

    Activity getById(String id);

    int update(Activity activity);

    Activity detail(String id);

    List<Activity> getActivityByClueId(String clueId);

    List<Activity> searchActivityText(Map<String, String> map);

    List<Activity> searchActivityByName(String name);
}
