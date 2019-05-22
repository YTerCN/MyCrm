package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

/**
 * Author 北京动力节点
 */
public interface ClueService {
    boolean save(Clue c);

    Clue detail(String id);


    boolean deleteClueAndActivityRelation(String id);

    boolean bund(String clueId, String[] activityId);

    boolean convert(String clueId, Tran t, String createBy);
}
