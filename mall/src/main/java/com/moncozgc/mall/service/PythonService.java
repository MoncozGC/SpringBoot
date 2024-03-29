package com.moncozgc.mall.service;

import com.moncozgc.mall.dto.ExportExcelERP;

/**
 * Created by MoncozGC on 2022/12/13
 */
public interface PythonService {
    /**
     * 测试python程序测试
     */
    int PythonDebugImpl();

    /**
     * 根据指定文件筛选指定列到新文件
     * waitFor()返回值含义: https://blog.csdn.net/qq_36838191/article/details/90438876
     *
     * @param exportExcelERP 导出实体类
     */
    int PythonToExcelImpl(ExportExcelERP exportExcelERP);

    /**
     * Python集成脚本
     *
     * @param type         执行环境, local-本地, server-远程服务器(默认)
     * @param pythonScript 脚本名称
     */
    int PythonToIntegrate(String type, String pythonScript);
}
