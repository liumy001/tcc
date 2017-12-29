package com.eric.demo.web.bill.controller;

import com.eric.demo.commons.util.DateUtil;
import com.eric.demo.commons.util.ResponseVo;
import com.eric.demo.commons.validator.BaseConst;
import com.eric.demo.web.bill.domain.BillReportTask;
import com.eric.demo.web.bill.domain.BillReportTaskCriteria;
import com.eric.demo.web.bill.dto.TaskReportDto;
import com.eric.demo.web.bill.service.IBillReportTaskService;
import com.eric.demo.web.users.domain.User;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("billReport")
public class BillReportController {

    @Autowired
    private IBillReportTaskService billReportTaskService;

    @RequestMapping(value = "getDayData")
    @ResponseBody
    public ResponseVo getDayData(HttpSession session) throws Exception {
        User user = (User) session.getAttribute(BaseConst.USER_SESSION_KEY);
        //x坐标
        List<String> xList = Lists.newArrayList();
        //获取开始日期和结束日期
        xList.add(DateUtil.getDayBeforeCurrentDate());
        String time = DateUtil.getDayBeforeCurrentDate();
        for (int i = 0; i < 6; i++) {
            time = DateUtil.checkOption("pre", time);
            xList.add(time);
        }
        Collections.reverse(xList);
        String endTime = DateUtil.checkOption("next", xList.get(xList.size() - 1)) + " 23:59:59";
        String startTime = DateUtil.checkOption("next", xList.get(0)) + " 00:00:00";
        //
        BillReportTaskCriteria billReportTaskCriteria = new BillReportTaskCriteria();
        billReportTaskCriteria.or().andIsDelEqualTo(BaseConst.isDel.no_Del.getCode()).andUidEqualTo(user.getId()).
                andCreatedDateGreaterThanOrEqualTo(DateUtil.parseDate(startTime, "yyyy-MM-dd HH:mm:ss")).andCreatedDateLessThanOrEqualTo(DateUtil.parseDate(endTime, "yyyy-MM-dd HH:mm:ss")).andTypeEqualTo(BaseConst.taskType.day.getCode());
        billReportTaskCriteria.setOrderByClause("created_date");

        List<BillReportTask> billReportTaskList = billReportTaskService.search(billReportTaskCriteria);
        TaskReportDto taskReportDto = new TaskReportDto();
        taskReportDto.setX(xList);
        double max = 0.0;
        for (String day : xList) {
            String d = DateUtil.checkOption("next", day);
            double price = 0.0;
            for (int i = 0; i < billReportTaskList.size(); i++) {
                if (DateUtil.dateFormat(billReportTaskList.get(i).getCreatedDate(), "yyyy-MM-dd").equals(d)) {
                    price = billReportTaskList.get(i).getTotalAmount() / 100.0;
                    break;
                }
            }
            taskReportDto.getData().add(price);
            if (price > max) {
                max = price;
            }
        }
        taskReportDto.setData_max((int) (((int) max)*1.5));

        return ResponseVo.responseOk(taskReportDto);
    }

    @RequestMapping(value = "toDay")
    public String toDay() {
        return "bill/dayReport";
    }
}
