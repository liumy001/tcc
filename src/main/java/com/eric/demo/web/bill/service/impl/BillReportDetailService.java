package com.eric.demo.web.bill.service.impl;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.commons.service.BaseServiceImpl;
import com.eric.demo.web.bill.dao.BillReportDetailDao;
import com.eric.demo.web.bill.domain.BillReportDetail;
import com.eric.demo.web.bill.domain.BillReportDetailCriteria;
import com.eric.demo.web.bill.service.IBillReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BillReportDetailService extends BaseServiceImpl<BillReportDetail, BillReportDetailCriteria> implements IBillReportDetailService {

    @Autowired
    private BillReportDetailDao billReportDetailDao;

    @Override
    protected BaseDao<BillReportDetail, BillReportDetailCriteria, String> getDao() {
        return billReportDetailDao;
    }
}
