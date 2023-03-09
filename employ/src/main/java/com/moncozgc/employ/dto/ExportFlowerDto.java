package com.moncozgc.employ.dto;

/**
 * Created by MoncozGC on 2022/11/10
 */
public class ExportFlowerDto {
    // 时间单位
    private String time_unit;
    // 统计数
    private double total_num;

    public String getTime_unit() {
        return time_unit;
    }

    public void setTime_unit(String time_unit) {
        this.time_unit = time_unit;
    }

    public double getTotal_num() {
        return total_num;
    }

    public void setTotal_num(double total_num) {
        this.total_num = total_num;
    }

    @Override
    public String toString() {
        return "FlowerDto{" +
                "time_unit='" + time_unit + '\'' +
                ", total_num=" + total_num +
                '}';
    }
}
