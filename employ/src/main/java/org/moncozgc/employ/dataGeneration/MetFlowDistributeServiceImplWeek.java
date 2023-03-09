package org.moncozgc.employ.dataGeneration;

import com.forte.util.Mock;
import com.forte.util.mockbean.MockObject;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 利用假数据模拟流量分布功能
 * 产品文档: https://u4nkr1.axshare.com/#id=e3b2xq&p=%E6%B5%81%E9%87%8F%E5%88%86%E5%B8%83&c=1
 * Created by MoncozGC on 2022/11/10
 */
public class MetFlowDistributeServiceImplWeek {
    public static void main(String[] args) {
        // 生成假数据
        HashMap<String, Object> template = new HashMap<>(2);
        template.put("time_unit|1-12", 0);
        template.put("total_num|18-80", 0);
        Mock.set(FlowerDto.class, template);

        MockObject<FlowerDto> mock = Mock.get(FlowerDto.class);

        // 拿到20个list
        List<FlowerDto> list = mock.getList(12);
        System.out.println(list.toString());

        MetFlowDistributeServiceImplWeek dataGeneration = new MetFlowDistributeServiceImplWeek();
        List<FlowerDto> flowerDtos_week = dataGeneration.processWeekData(list);
        System.out.println("\n\n周段分布数据: ");
        System.out.println(flowerDtos_week);

    }

    /**
     * 处理周分布数据
     * 观测时间天数＜14天，不显示本模块；
     * 1、数据：设置的数据范围内，指标在各个【周时段】（周一~周日）的均值；
     * 1）计算＝Σ观测时段内某个周时段的数值÷观测时间内包含的该周时段天数；
     * 2）如，某个观测时段有3个周四，该时段周四的均值等于这3天数值的均值；
     * 2、图形
     * 1）使用柱状图
     * 2）横坐标轴横坐标轴：周一~周日；
     *
     * @param flowerDtoList FlowerDto{time_unit='21', total_num=34.0}, 每小时及统计数
     * @return 计算出每个周段均值 FlowerDto{time_unit='5', total_num=7.8}
     */
    public List<FlowerDto> processWeekData(List<FlowerDto> flowerDtoList) {
        List<FlowerDto> resultList = new ArrayList<>();
        // 判断list是否为空
        List<FlowerDto> flowerDtos = Optional.ofNullable(flowerDtoList).orElse(new ArrayList<>());
        Map<String, FlowerDto> dtoMap = flowerDtos.stream().collect(Collectors.toMap(FlowerDto::getTime_unit, Function.identity(), (key1, key2) -> key2));
        int[] weeks = {1, 2, 3, 4, 5, 6, 7};
        for (int week : weeks) {
            String week_spec = String.valueOf(week);
            if (dtoMap.containsKey(week_spec)) {
                resultList.add(dtoMap.get(week_spec));
            } else {
                FlowerDto flowerDto = new FlowerDto();
                flowerDto.setTime_unit(week_spec);
                flowerDto.setTotal_num(0D);
                resultList.add(flowerDto);
            }
        }
        return resultList;
    }

    /**
     * 转化为Double类型
     *
     * @param value
     * @return
     */
    private double format(double value) {
        String format = String.format("%.2f", value);
        return Double.parseDouble(format);
    }
}
