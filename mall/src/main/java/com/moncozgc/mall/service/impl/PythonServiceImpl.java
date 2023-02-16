package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.dto.ExportExcelERP;
import com.moncozgc.mall.service.PythonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MoncozGC on 2022/12/13
 */
@Slf4j
@Service
public class PythonServiceImpl implements PythonService {

    @Value("${python.parser.path}")
    private String PYTHON_SYS_ENV;
    private final String PYTHON_RUN_PATH = System.getProperty("user.dir") + "\\python\\";
    // 读取resources路径文件, 但是无法执行
//    private final String PYTHON_RUN_PATH = (ClassLoader.getSystemResource("python").getPath() + "/").replaceAll("%20", " ");

    @Override
    public int PythonDebugImpl() {
        String PythonScript = PYTHON_RUN_PATH + "spring_debug.py";
        //前面一半是本地环境下的python的启动文件地址，后面一半是要执行的python脚本地址
        String[] arguments = new String[]{PYTHON_SYS_ENV, PythonScript};
        Process proc;
        int re = 0;
        try {
            proc = Runtime.getRuntime().exec(arguments);// 执行py文件
            //用输入输出流来截取结果, 中文编码处理: UTF-8/GBK/GB2312
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line: " + line);
            }
            in.close();
            //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
            re = proc.waitFor();
            log.info("执行状态: " + re);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return re;
    }

    @Override
    public int PythonToExcelImpl(ExportExcelERP exportExcelERP) {
        // 待执行的python脚本
        String PythonScript = PYTHON_RUN_PATH + "to_one_excel.py";
        // {python解释器, python执行脚本, 文件的指定路径, 文件的名称, 指定导出的列}
        String[] arg = {PYTHON_SYS_ENV, PythonScript, exportExcelERP.getFile_path(), exportExcelERP.getFile_name(), exportExcelERP.getChoice_colum()};
        Process proc;
        int result = -1;
        try {
            proc = Runtime.getRuntime().exec(arg);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line: " + line);
            }
            in.close();
            result = proc.waitFor();
            log.info("执行状态: " + result);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int PythonToIntegrate(String script) {
        String PythonScript = PYTHON_RUN_PATH + script;
        log.info("PYTHON解释器路径: " + PYTHON_SYS_ENV + ", PYTHON脚本路径: " + PythonScript);
        log.info("PYTHON RUN SCRIPT: " + script);
        //前面一半是本地环境下的python的启动文件地址，后面一半是要执行的python脚本地址
        String[] arguments = new String[]{PYTHON_SYS_ENV, PythonScript};
        Process proc;
        int re = 0;
        try {
            proc = Runtime.getRuntime().exec(arguments);// 执行py文件
            //用输入输出流来截取结果, 中文编码处理: UTF-8/GBK/GB2312
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(), "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                log.info("line: " + line);
            }
            in.close();
            //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
            re = proc.waitFor();
            log.info("执行状态: " + re);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return re;
    }
}
