package com.eric.demo.web.bill.service.impl;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.service.BaseServiceImpl;
import com.eric.demo.web.bill.dao.BillReportTaskDao;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.domain.BillReportTaskCriteria;
import com.eric.demo.web.bill.service.IBillReportTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillReportTaskService extends BaseServiceImpl<BillReportTask, BillReportTaskCriteria> implements IBillReportTaskService {

    @Autowired
    private BillReportTaskDao billReportTaskDao;

    @Override
    protected BaseDao<BillReportTask, BillReportTaskCriteria, String> getDao() {
        return billReportTaskDao;
    }
}
