package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.utils.DateTimeUtil;
import com.bjpowernode.crm.utils.SqlSessionUtil;
import com.bjpowernode.crm.utils.UUIDUtil;
import com.bjpowernode.crm.workbench.dao.*;
import com.bjpowernode.crm.workbench.domain.*;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Author 北京动力节点
 */
public class ClueServiceImpl implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    public boolean save(Clue c) {
        boolean flag = true;
        int count = clueDao.save(c);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public Clue detail(String id) {
        Clue c = null;
        c = clueDao.detail(id);
        return c;
    }

    public boolean deleteClueAndActivityRelation(String id) {
        boolean flag = true;
        int count = clueActivityRelationDao.deleteClueAndActivityRelation(id);
        if (count!=1){
            flag = false;
        }
        return flag;
    }

    public boolean bund(String clueId, String[] ids) {
        boolean flag = true;
        for (String activityId:ids){
            ClueActivityRelation car = new ClueActivityRelation();
            car.setId(UUIDUtil.getUUID());
            car.setActivityId(activityId);
            car.setClueId(clueId);
            int count = clueActivityRelationDao.bund(car);
            if (count!=1){
                flag = false;
            }
        }
        return flag;
    }

    public boolean convert(String clueId, Tran t, String createBy) {
        boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();
        //(1) 获取到线索id，通过线索id获取线索对象（线索对象当中封装了线索的信息）
        Clue clue = clueDao.getClueById(clueId);
        //(2) 通过线索对象提取客户信息，当该客户不存在的时候，新建客户（根据公司的名称精确匹配，判断该客户是否存在！）
        Customer customer = customerDao.getCustomerByName(clue.getCompany());
        if (customer==null){
            customer = new Customer();
            customer.setAddress(clue.getAddress());
            customer.setContactSummary(clue.getContactSummary());
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setOwner(clue.getOwner());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setName(clue.getCompany());
            customer.setId(UUIDUtil.getUUID());
            customer.setDescription(clue.getDescription());
            customer.setCreateTime(createTime);
            customer.setCreateBy(createBy);
            int count1 = customerDao.addCustomer(customer);
            if (count1!=1){
                flag = false;
            }
        }

        //(3) 通过线索对象提取联系人信息，保存联系人
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setOwner(clue.getOwner());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setFullname(clue.getFullname());
        contacts.setEmail(clue.getEmail());
        contacts.setDescription(clue.getDescription());
        contacts.setCustomerId(customer.getId());
        contacts.setCreateTime(createTime);
        contacts.setCreateBy(createBy);
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setAppellation(clue.getAppellation());
        contacts.setAddress(clue.getAddress());
        int count2 = contactsDao.addContacts(contacts);
        if (count2!=1){
            flag = false;
        }
        //(4) 线索备注转换到客户备注以及联系人备注
        List<ClueRemark> crList = clueRemarkDao.getRemarkByClueId(clueId);
        for (ClueRemark cr:crList){
            CustomerRemark customerRemark = new CustomerRemark();
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setNoteContent(cr.getNoteContent());
            customerRemark.setEditFlag("0");
            customerRemark.setCustomerId(customer.getId());
            customerRemark.setCreateTime(createTime);
            customerRemark.setCreateBy(createBy);
            int count3 = customerRemarkDao.addRemark(customerRemark);
            if (count3!=1){
                flag = false;
            }
            ContactsRemark contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(cr.getNoteContent());
            contactsRemark.setEditFlag("0");
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setContactsId(contacts.getId());
            int count4 = contactsRemarkDao.addRemark(contactsRemark);
            if (count4!=1){
                flag = false;
            }
        }


        //(5) “线索和市场活动”的关系转换到“联系人和市场活动”的关系

        List<ClueActivityRelation> carList = clueActivityRelationDao.getList(clueId);

        for (ClueActivityRelation car:carList){
            ContactsActivityRelation contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(car.getActivityId());
            int count5 = contactsActivityRelationDao.addRelation(contactsActivityRelation);
            if (count5!=1){
                flag = false;
            }
        }

        //(6) 如果有创建交易需求，创建一条交易
        if (t!=null){
            t.setContactsId(contacts.getId());
            t.setContactSummary(contacts.getContactSummary());
            t.setCustomerId(customer.getId());
            t.setDescription(clue.getDescription());
            t.setNextContactTime(clue.getNextContactTime());
            t.setOwner(clue.getOwner());
            t.setSource(clue.getSource());
            int count6 = tranDao.addTran(t);
            if (count6!=1){
                flag = false;
            }

            //(7) 如果创建了交易，则创建一条该交易下的交易历史
            TranHistory tranHistory = new TranHistory();
            tranHistory.setId(UUIDUtil.getUUID());
            tranHistory.setTranId(t.getId());
            tranHistory.setStage(t.getStage());
            tranHistory.setMoney(t.getMoney());
            tranHistory.setExpectedDate(t.getExpectedDate());
            tranHistory.setCreateTime(createTime);
            tranHistory.setCreateBy(createBy);
            int count7 = tranHistoryDao.addHistory(tranHistory);
            if (count7!=1){
                flag = false;
            }
        }

        //(8) 删除线索备注
        int num1 = clueRemarkDao.getNumByClueId(clueId);
        int count8 = clueRemarkDao.deleteByClueId(clueId);
        if (count8!=num1){
            flag = false;
        }

        //(9) 删除线索和市场活动的关系
        int num2 = clueActivityRelationDao.getNum(clueId);
        int count9 = clueActivityRelationDao.deleteByClueId(clueId);
        if (count9!=num2){
            flag = false;
        }

        //(10) 删除线索
        int count10 = clueDao.delete(clueId);
        if (count10!=1){
            flag = false;
        }

        return flag;
    }


}
