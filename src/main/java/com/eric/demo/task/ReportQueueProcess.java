package com.eric.demo.task;

import com.alibaba.fastjson.JSON;
import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.DateUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.bill.domain.Bill;
import com.eric.demo.web.bill.domain.BillCriteria;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.service.IBillService;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import com.eric.demo.web.users.service.IUserService;
import com.eric.demo.web.users.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ReportQueueProcess {

    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

    private final static String key = "redis_bill_report";

    @Autowired
    private IBillService billService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private IUserService userService;

    @Scheduled(cron = "*/10 * * * * *")
    public void execute() throws Exception {
        String value = redisTemplate.opsForList().leftPop(key);
        if (Check.NuNObj(value)) {
            return;
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

    private void process(BillReportTask billReportTask) throws Exception {
        BillCriteria billCriteria = new BillCriteria();
        billCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode()).andUidEqualTo(billReportTask.getUid()).andConsumTimeGreaterThanOrEqualTo(billReportTask.getEndTime()).andConsumTimeLessThanOrEqualTo(billReportTask.getStartTime());
        List<Bill> billList = billService.search(billCriteria);
        int totalPrice = 0;
        for (Bill bill : billList) {
            totalPrice += bill.getAmount();
        }

        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andIdEqualTo(billReportTask.getUid());
        List<User> userList = userService.search(userCriteria);


        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("liumingyue0203@163.com");
        helper.setTo(userList.get(0).getEmail());
        helper.setSubject("消费明细");
        //html 加如参数 true
        helper.setText("日期：" + DateUtil.dateFormat(billReportTask.getStartTime()) + "-" + DateUtil.dateFormat(billReportTask.getEndTime()) + "累计消费：" + totalPrice / 100.0 + "元");

        mailSender.send(message);
    }

}
