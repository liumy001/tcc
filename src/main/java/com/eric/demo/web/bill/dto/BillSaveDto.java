package com.eric.demo.web.bill.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public class BillSaveDto {

    //二级类目
    @NotBlank(message = "二级类目不可为空")
    private String categoryId;
    @NotBlank(message = "账单名称不能为空")
    private String billName;
    @NotBlank(message = "金额不可为空")
    @Pattern(regexp = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$", message = "金额为整数或者保留两位小数")
    @Min(value = 0, message = "金额必须大于等于0元")
    private String amount;
    private String remark;
    @NotNull(message = "消费时间不可为空")
    private Date consumTime;

    public String getCategoryId() {
        return categoryId;
    }

    public BillSaveDto setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getBillName() {
        return billName;
    }

    public BillSaveDto setBillName(String billName) {
        this.billName = billName;
        return this;
    }

    public String getAmount() {
        return amount;
    }

    public BillSaveDto setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public BillSaveDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public Date getConsumTime() {
        return consumTime;
    }

    public BillSaveDto setConsumTime(Date consumTime) {
        this.consumTime = consumTime;
        return this;
    }
}
