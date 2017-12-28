package com.eric.demo.task;

import com.alibaba.fastjson.JSON;
import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.DateUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.bill.domain.Bill;
import com.eric.demo.web.bill.domain.BillCriteria;
import com.eric.demo.web.bill.domain.BillReportDetail;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.service.IBillReportDetailService;
import com.eric.demo.web.bill.service.IBillReportTaskService;
import com.eric.demo.web.bill.service.IBillService;
import com.eric.demo.web.category.domain.Category;
import com.eric.demo.web.category.domain.CategoryCriteria;
import com.eric.demo.web.category.service.ICategoryService;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import com.eric.demo.web.users.service.IUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ReportQueueProcess {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReportQueueProcess.class);

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

    @Autowired
    private IBillReportTaskService billReportTaskService;

    @Autowired
    private IBillReportDetailService billReportDetailService;

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
        if (Check.NuNCollection(billList)) {
            return;
        }
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
        String text = "日期：" + DateUtil.dateFormat(billReportTask.getStartTime()) + " 00:00-" + DateUtil.dateFormat(billReportTask.getEndTime()) + " 23:59 累计消费：" + totalPrice / 100.0 + "元 列表如下：\n";
        text += "--------------------------------------------------账单明细----------------------------------------------------------------------------\n";
        Set<String> setC = Sets.newHashSet();
        for (int i = 0; i < billList.size(); i++) {
            Bill bill = billList.get(i);
            setC.add(bill.getCategoryId());
            CategoryCriteria categoryCriteria = new CategoryCriteria();
            categoryCriteria.or().andIdEqualTo(bill.getCategoryId());
            List<Category> category = categoryService.search(categoryCriteria);
            text += "" + i + " 日期："+DateUtil.dateFormat(bill.getCreatedDate(),"yyyy-MM-dd HH:mm:ss") + " 消费名称："+category.get(0).getCategoryName() + "-" + bill.getBillName() + "花费金额：" + bill.getAmount() / 100.0 + "元\n";
        }
        text += "--------------------------------------------------类别明细----------------------------------------------------------------------------\n";
        int i = 0;
        for (String z : setC) {
            BillReportDetail billReportDetail = new BillReportDetail();
            int price = 0;
            for (Bill bill : billList) {
                if (z.equals(bill.getCategoryId())) {
                    price += bill.getAmount();
                }
            }
            billReportDetail.setCategoryId(z);
            billReportDetail.setReportId(billReportTask.getId());
            billReportDetail.setTotalAmount(price);
            billReportDetailService.create(billReportDetail);
            CategoryCriteria categoryCriteria = new CategoryCriteria();
            categoryCriteria.or().andIdEqualTo(z);
            List<Category> category = categoryService.search(categoryCriteria);
            text += i + " 类别为：" + category.get(0).getCategoryName() + "         累计消费:" + price / 100.00 + "元\n";
            i++;
        }
        helper.setText(text);
        try {
            mailSender.send(message);
            billReportTask.setStatus(2);
            billReportTask.setSendStatus(1);
            billReportTask.setTotalAmount(totalPrice);
            billReportTaskService.update(billReportTask);
        } catch (Exception e) {
            LOGGER.error("邮件发送失败", e);
            billReportTask.setStatus(0);
            billReportTask.setSendStatus(2);
            billReportTaskService.update(billReportTask);
        }


    }

}
