package com.eric.demo.task;

import com.alibaba.fastjson.JSON;
import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.DateUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.bill.domain.Bill;
import com.eric.demo.web.bill.domain.BillCriteria;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.service.IBillService;
import com.eric.demo.web.category.domain.Category;
import com.eric.demo.web.category.domain.CategoryCriteria;
import com.eric.demo.web.category.service.ICategoryService;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import com.eric.demo.web.users.service.IUserService;
import com.google.common.collect.Lists;
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

    @Autowired
    private ICategoryService categoryService;

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
        billCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode()).andUidEqualTo(billReportTask.getUid()).andConsumTimeGreaterThanOrEqualTo(billReportTask.getStartTime()).andConsumTimeLessThanOrEqualTo(billReportTask.getEndTime());
        List<Bill> billList = billService.search(billCriteria);
        int totalPrice = 0;
        for (Bill bill : billList) {
            totalPrice += bill.getAmount();
        }
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andIdEqualTo(billReportTask.getUid());
        List<User> userList = userService.search(userCriteria);

        String emails = userList.get(0).getExtEmail();
        List<String> emailList = Lists.newArrayList(userList.get(0).getEmail());
        if (!Check.NuNObj(emails)) {
            String[] e = emails.split(",");
            for (String ee : e) {
                emailList.add(ee);
            }
        }
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("liumingyue0203@163.com");
        helper.setTo(emailList.toArray(new String[emailList.size()]));
        helper.setSubject("消费明细");
        //html 加如参数 true
        String text = "日期：" + DateUtil.dateFormat(billReportTask.getStartTime()) + "00:00-" + DateUtil.dateFormat(billReportTask.getEndTime()) + "23:59 累计消费：" + totalPrice / 100.0 + "元 明细如下：\n";
        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            CategoryCriteria categoryCriteria = new CategoryCriteria();
            categoryCriteria.or().andIdEqualTo(bill.getCategoryId());
            List<Category> category = categoryService.search(categoryCriteria);
            text += ""+i + "." + category.get(0).getCategoryName() + "-" + bill.getBillName() + "花费：" + bill.getAmount() / 100.0 + "元\n";
        }
        helper.setText(text);

        mailSender.send(message);
    }

}
