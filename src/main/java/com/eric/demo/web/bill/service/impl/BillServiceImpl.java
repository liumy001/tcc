package com.eric.demo.web.bill.service.impl;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.service.BaseServiceImpl;
import com.eric.demo.web.bill.dao.BillDao;
import com.eric.demo.web.bill.domain.Bill;
import com.eric.demo.web.bill.domain.BillCriteria;
import com.eric.demo.web.bill.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class BillServiceImpl extends BaseServiceImpl<Bill, BillCriteria> implements IBillService {

    @Autowired
    private BillDao billDao;

    @Override
    protected BaseDao<Bill, BillCriteria, String> getDao() {
        return this.billDao;
    }

}
