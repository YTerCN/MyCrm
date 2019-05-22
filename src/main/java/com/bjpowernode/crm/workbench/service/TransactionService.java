package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    Tran detail(String id);

    List<TranHistory> getHistoty(String tranId);

    Map<String, Object> getCharts();

}
