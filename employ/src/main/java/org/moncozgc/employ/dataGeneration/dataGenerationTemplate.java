package org.moncozgc.employ.dataGeneration;

import com.forte.util.Mock;
import com.forte.util.mockbean.MockObject;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * 数据生成模板
 * Created by MoncozGC on 2022/11/10
 */
public class dataGenerationTemplate {
    public static void main(String[] args) {
        // 生成假数据
        HashMap<String, Object> template = new HashMap<>(2);
        template.put("time_unit|1-24", 0);
        template.put("total_num|18-80", 0);
        Mock.set(FlowerDto.class, template);

        MockObject<FlowerDto> mock = Mock.get(FlowerDto.class);

        // 拿到20个list
        List<FlowerDto> list = mock.getList(20);
        // 拿到20个的list并转换为string字符串
        Set<String> mockSet = mock.getSet(20, FlowerDto::toString);
        // 获取一个生成对象的无限流
        Stream<FlowerDto> stream = mock.getStream();
        // 通过并行流(多线程)的方式获取20个对象
        List<FlowerDto> listParallel = mock.getListParallel(20);

        System.out.println(list.toString());
    }
}
