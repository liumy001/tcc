package com.eric.demo.task;

import com.eric.demo.commons.util.Check;
import com.eric.demo.commons.util.DateUtil;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.bill.controller.BillController;
import com.eric.demo.web.bill.domain.Bill;
import com.eric.demo.web.bill.domain.BillCriteria;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.service.IBillReportTaskService;
import com.eric.demo.web.bill.service.IBillService;
import com.eric.demo.web.users.domain.User;
import com.eric.demo.web.users.domain.UserCriteria;
import com.eric.demo.web.users.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class BillReportCreateTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(BillReportCreateTask.class);

    @Autowired
    private IBillReportTaskService billReportTaskService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IBillService billService;

    @Scheduled(cron = "0 10 0 * * *")
    public void createDayTask() throws Exception {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode());
        List<User> userList = userService.search(userCriteria);
        if (Check.NuNCollection(userList)) {
            LOGGER.error("未扫描到有效用户");
            return;
        }
        String date = DateUtil.getDayBeforeCurrentDate();
        Date startTime = DateUtil.parseDate(date + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date endTime = DateUtil.parseDate(date + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        process(userList, startTime, endTime, BaseConst.taskType.day.getCode());


    }

    @Scheduled(cron = "0 15 1 ? * MON")
    public void createWeekDayTask() throws Exception {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode());
        List<User> userList = userService.search(userCriteria);
        if (Check.NuNCollection(userList)) {
            LOGGER.error("未扫描到有效用户");
            return;
        }
        String date = DateUtil.getDayBeforeCurrentDate();
        Date startDate = DateUtil.parseDate(DateUtil.getFirstDayOfWeek(DateUtil.parseDate(date, "yyyy-MM-dd")) + " 00:00:00", "yyyy-MM-dd HH:ss:mm");
        Date endDate = DateUtil.parseDate(DateUtil.getLastDayOfWeek(DateUtil.parseDate(date, "yyyy-MM-dd")) + " 23:59:59", "yyyy-MM-dd HH:ss:mm");
        process(userList, startDate, endDate, BaseConst.taskType.week.getCode());
    }

    @Scheduled(cron = " 0 15 2 1 * ?")
    public void createMounthTask() throws Exception {
        UserCriteria userCriteria = new UserCriteria();
        userCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode());
        List<User> userList = userService.search(userCriteria);
        if (Check.NuNCollection(userList)) {
            LOGGER.error("未扫描到有效用户");
            return;
        }
        String date = DateUtil.getDayBeforeCurrentDate();
        Date startDate = DateUtil.parseDate(DateUtil.getFirstDayOfMonth(DateUtil.parseDate(date, "yyyy-MM-dd")) + " 00:00:00", "yyyy-MM-dd HH:ss:mm");
        Date endDate = DateUtil.parseDate(DateUtil.getLastDayOfMonth(DateUtil.parseDate(date, "yyyy-MM-dd")) + " 23:59:59", "yyyy-MM-dd HH:ss:mm");
        process(userList, startDate, endDate, BaseConst.taskType.mounth.getCode());
    }

    private void process(List<User> userList, Date startTime, Date endTime, int type) {
        //根据指定时间查询
        for (User user : userList) {
            BillCriteria billCriteria = new BillCriteria();
            billCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode()).andUidEqualTo(user.getId()).andConsumTimeLessThanOrEqualTo(endTime).andConsumTimeGreaterThanOrEqualTo(startTime);
            List<Bill> billList = billService.search(billCriteria);
            if (!Check.NuNCollection(billList)) {
                BillReportTask billReportTask = new BillReportTask();
                billReportTask.setEndTime(endTime);
                billReportTask.setStartTime(startTime);
                billReportTask.setStatus(0);
                billReportTask.setSendStatus(0);
                billReportTask.setTotalAmount(0);
                billReportTask.setUid(user.getId());
                billReportTask.setType(type);
                billReportTaskService.create(billReportTask);
            }
        }
    }

}
