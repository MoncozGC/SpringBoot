package com.moncozgc.mall.service.impl;

import com.moncozgc.mall.dto.ExportExcelERP;
import com.moncozgc.mall.service.PythonService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by MoncozGC on 2022/12/13
 */
@Service
public class PythonServiceImpl implements PythonService {

    private final String PYTHON_SYS_ENV = "D:\\File\\YKYY\\Work File\\IntelliJ IDEA\\YKYY-PyCharm\\Employ\\venv\\Scripts\\python.exe";
    private final String PYTHON_RUN_PATH = "D:\\File\\YKYY\\Work File\\IntelliJ IDEA\\YKYY-UseProject\\SpringBootEmploy\\mall\\src\\main\\python\\";

    @Override
    public void PythonDebugImpl() {
        String PythonScript = PYTHON_RUN_PATH + "spring_debug.py";
        //前面一半是本地环境下的python的启动文件地址，后面一半是要执行的python脚本地址
        String[] arguments = new String[]{PYTHON_SYS_ENV, PythonScript};
        Process proc;
        try {

            proc = Runtime.getRuntime().exec(arguments);// 执行py文件
            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            new InputStreamReader(proc.getInputStream());
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            //waitFor是用来显示脚本是否运行成功，1表示失败，0表示成功，还有其他的表示其他错误
            int re = proc.waitFor();
            System.out.println(re);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void PythonToExcelImpl(ExportExcelERP exportExcelERP) {
        // 待执行的python脚本
        String PythonScript = PYTHON_RUN_PATH + "to_one_excel.py";
        String[] arg = {PYTHON_SYS_ENV, PythonScript, exportExcelERP.getFile_name(), exportExcelERP.getChoice_colum()};
        Process proc;
        try {
            proc = Runtime.getRuntime().exec(arg);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            int re = proc.waitFor();
            System.out.println(re);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}