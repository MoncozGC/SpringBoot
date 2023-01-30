package com.moncozgc.mall.service;

/**
 * Created by MoncozGC on 2022/12/27
 */
public enum PythonScriptCode {
    PYTHON_TEST("PY_0001", "spring_debug.py", "通道测试"),
    PYTHON_EXCEL("PY_0002", "to_one_excel.py", "根据指定EXCEL导出指定列数据"),
    PYTHON_UNIV_REPTILE("PY_0003", "china_rank_data_capture.py", "中国大学排名数据爬取");
    final private String code;
    final private String scriptName;
    final private String message;

    private PythonScriptCode(String code, String scriptName, String message) {
        this.code = code;
        this.scriptName = scriptName;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getScriptName(String code) {
        return scriptName;
    }

    public String getScriptName() {
        return scriptName;
    }

    public String getMessage() {
        return message;
    }
}
