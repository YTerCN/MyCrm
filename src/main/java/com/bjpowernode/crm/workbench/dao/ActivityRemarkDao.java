package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {
    int delete(String[] ids);

    List<ActivityRemark> contentList(String id);

    int deleteRemark(String id);

    int updateRemark(ActivityRemark ar);

    int saveRemark(ActivityRemark ar);
}
