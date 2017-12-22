package com.eric.demo.web.bill.dao;

import com.eric.demo.commons.dao.BaseDao;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.domain.BillReportTaskCriteria;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

/**
 * @数表名称 t_bill_report_task
 * @开发日期 2017-12-22 15:52:47
 * @开发作者 by:liumy 
 */
public interface BillReportTaskDao extends BaseDao<BillReportTask, BillReportTaskCriteria, String> {
}