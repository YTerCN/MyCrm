package com.bjpowernode.crm.workbench.dao;


import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationDao {


    int deleteClueAndActivityRelation(String id);

    int bund(ClueActivityRelation car);

    List<ClueActivityRelation> getList(String clueId);

    int getNum(String clueId);

    int deleteByClueId(String clueId);
}
