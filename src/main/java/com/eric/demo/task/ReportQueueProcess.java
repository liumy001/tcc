package com.eric.demo.task;

import com.alibaba.fastjson.JSON;
import com.eric.demo.commons.util.Check;
import com.eric.demo.web.bill.domain.BillCriteria;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.service.IBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReportQueueProcess {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    private final static String key = "redis_bill_report";

    @Autowired
    private IBillService billService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void execute() throws Exception {

        while (true) {
            String value = redisTemplate.opsForList().leftPop(key);
            if (Check.NuNObj(value)) {
                Thread.sleep(2000);
            } else {
                fixedThreadPool.execute(new Runnable() {
                    public void run() {
                        try {
                            BillReportTask billReportTask = JSON.parseObject(value, BillReportTask.class);
                            process(billReportTask);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }
    }

    private void process(BillReportTask billReportTask) {
        BillCriteria billCriteria = new BillCriteria();

    }

}
