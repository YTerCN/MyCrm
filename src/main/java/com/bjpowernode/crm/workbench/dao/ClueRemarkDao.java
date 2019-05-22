package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    List<ClueRemark> getRemarkByClueId(String clueId);

    int getNumByClueId(String clueId);

    int deleteByClueId(String clueId);
}
