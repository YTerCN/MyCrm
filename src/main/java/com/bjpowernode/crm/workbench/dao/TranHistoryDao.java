package com.bjpowernode.crm.workbench.dao;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int addHistory(TranHistory tranHistory);

    List<TranHistory> getHistoty(String tranId);
}
