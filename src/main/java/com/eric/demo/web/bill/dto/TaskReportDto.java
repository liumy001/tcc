package com.eric.demo.web.bill.dto;

import java.util.ArrayList;
import java.util.List;

public class TaskReportDto {

    private List<Double> data=new ArrayList<>();

    private int data_max;

    private List<String> x;

    public List<Double> getData() {
        return data;
    }

    public TaskReportDto setData(List<Double> data) {
        this.data = data;
        return this;
    }

    public int getData_max() {
        return data_max;
    }

    public TaskReportDto setData_max(int data_max) {
        this.data_max = data_max;
        return this;
    }

    public List<String> getX() {
        return x;
    }

    public TaskReportDto setX(List<String> x) {
        this.x = x;
        return this;
    }
}
