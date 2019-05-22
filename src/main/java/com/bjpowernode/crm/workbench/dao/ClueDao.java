package com.bjpowernode.crm.workbench.dao;


import com.bjpowernode.crm.workbench.domain.Clue;

public interface ClueDao {


    int save(Clue c);

    Clue detail(String id);

    Clue getClueById(String clueId);

    int delete(String clueId);
}
