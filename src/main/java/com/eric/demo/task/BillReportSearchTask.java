package com.eric.demo.task;

import com.alibaba.fastjson.JSONObject;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.domain.BillReportTaskCriteria;
import com.eric.demo.web.bill.service.IBillReportTaskService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BillReportSearchTask {

    private final static String key = "redis_bill_report";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private IBillReportTaskService billReportTaskService;

    @Scheduled(cron = "0 */1 * * * *")
    public void findTask() {
        BillReportTaskCriteria billReportTaskCriteria = new BillReportTaskCriteria();
        List<Integer> statusList = Lists.newArrayList(0, 3, 4);
        billReportTaskCriteria.or().andStatusIn(statusList).andIsDelEqualTo(BaseConst.isDel.no_Del.getCode());
        List<BillReportTask> billReportTaskList = billReportTaskService.search(billReportTaskCriteria);
        for (BillReportTask billReportTask : billReportTaskList) {
            redisTemplate.opsForList().leftPush(key, JSONObject.toJSONString(billReportTask));
        }
        return;
    }

}
