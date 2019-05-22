package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.workbench.dao.TranDao;
import com.bjpowernode.crm.workbench.dao.TranHistoryDao;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.service.TransactionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author  都钦宗
 */
public class TransactionServiceImpl implements TransactionService {
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    public Tran detail(String id) {
        Tran t = tranDao.detail(id);
        return t;
    }

    public List<TranHistory> getHistoty(String tranId) {
        List<TranHistory> tList = tranHistoryDao.getHistoty(tranId);

        return tList;
    }

    public Map<String, Object> getCharts() {
        Map<String,Object> map = new HashMap<String, Object>();
        int total = tranDao.getTotal();
        List<Map<String,Object>> list = tranDao.getAll();
        map.put("total", total);
        map.put("list", list);
        return map;
    }
}
