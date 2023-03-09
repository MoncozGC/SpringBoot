package com.moncozgc.employ.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * Excel数据的导入导出, 根据自定义模板文件
 *
 * Created by MoncozGC on 2022/11/21
 */
public class ExportExcelDTO {
    @Excel(name = "学号", height = 8, width = 13, isImportField = "true")
    private Integer id;
    @Excel(name = "姓名", height = 8, width = 13, isImportField = "true")
    private String userName;
    @Excel(name = "年龄", height = 8, width = 13, isImportField = "true")
    private String userAge;

    @Override
    public String toString() {
        return "ExportExcelDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userAge='" + userAge + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public ExportExcelDTO(Integer id, String userName, String userAge) {
        this.id = id;
        this.userName = userName;
        this.userAge = userAge;
    }
}
