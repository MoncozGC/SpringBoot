package com.moncozgc.employ.controlle;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import com.forte.util.Mock;
import com.forte.util.mockbean.MockObject;
import com.google.common.collect.Maps;
import com.moncozgc.employ.dto.ExportExcelDTO;
import com.moncozgc.employ.dto.ExportFlowerDto;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Excel导出功能
 *
 * Created by MoncozGC on 2022/11/21
 */
@Controller
@RequestMapping(value = "/export")
public class ExportExcelController {

    /**
     * 根据模板文件导出数据
     *
     * @param response 请求
     */
    @GetMapping(value = "/exportAssignTemplateExcel")
    public void ExportTemplateExcel(HttpServletResponse response) {
        List<ExportExcelDTO> exportList = new ArrayList<>();
        ExportExcelDTO mon01 = new ExportExcelDTO(1001, "Mon1", "18");
        ExportExcelDTO mon02 = new ExportExcelDTO(1002, "Mon2", "19");
        ExportExcelDTO mon03 = new ExportExcelDTO(1003, "Mon3", "20");

        exportList.add(mon01);
        exportList.add(mon02);
        exportList.add(mon03);

        // exportData作为key, exportList作为value. 用于给ExcelExportUtil识别变量.
        // 注意: map的key需要和Excel中的for循环的参数名保持一致, 否则找不到数据. 只会将模板给导出.
        HashMap<String, Object> exportMap = Maps.newHashMap();
        exportMap.put("exportData", exportList);

        // 获取导出模板地址
        ClassPathResource classPathResource = new ClassPathResource("static/export/template/ExportExcelDTO.xlsx");
        String path = classPathResource.getPath();
        TemplateExportParams templateExportParams = new TemplateExportParams(path);
        Workbook wb = ExcelExportUtil.exportExcel(templateExportParams, exportMap);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"));
        String fileName = "模板文件" + time + ".xlsx";

        try {
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据生成的假数据, 指定sheet页导出数据
     *
     * @param response 请求
     */
    @GetMapping(value = "/export/exportFlower")
    public void ExportFlower(HttpServletResponse response) {
        // 生成假数据
        HashMap<String, Object> template = new HashMap<>(2);
        template.put("time_unit|1-24", 0);
        template.put("total_num|18-80", 0);
        Mock.set(ExportFlowerDto.class, template);

        MockObject<ExportFlowerDto> mock = Mock.get(ExportFlowerDto.class);

        // 拿到20个list
        List<ExportFlowerDto> dataList = mock.getList(20);
        System.out.println("随机数据: " + dataList.toString());

        HashMap<String, Object> exportMap = Maps.newHashMap();
        exportMap.put("exportData", dataList);

        // 获取导出模板地址
        ClassPathResource classPathResource = new ClassPathResource("static/export/template/ExportExcelDTO.xlsx");
        String path = classPathResource.getPath();
        TemplateExportParams templateExportParams = new TemplateExportParams(path, 1);
        Workbook wb = ExcelExportUtil.exportExcel(templateExportParams, exportMap);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = "模板文件" + time + ".xlsx";

        try {
            response.setContentType("application/octet-stream;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
