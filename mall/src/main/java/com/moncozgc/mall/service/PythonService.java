package com.moncozgc.mall.service;

import com.moncozgc.mall.dto.ExportExcelERP;

/**
 * Created by MoncozGC on 2022/12/13
 */
public interface PythonService {
    /**
     * 调用python程序测试
     */
    void PythonDebugImpl();

    /**
     * 根据文件导出指定列
     *
     * @param FileStr     文件名称 xx.xlsx
     * @param ChoiceColum 指定列 eg: A,B,C,D
     */
    void PythonToExcelImpl(ExportExcelERP exportExcelERP);
}
