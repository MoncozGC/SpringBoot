package org.moncozgc.employ.dataGeneration;

import com.forte.util.Mock;
import com.forte.util.mockbean.MockObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 利用假数据模拟流量分布功能
 * 产品文档: https://u4nkr1.axshare.com/#id=e3b2xq&p=%E6%B5%81%E9%87%8F%E5%88%86%E5%B8%83&c=1
 * Created by MoncozGC on 2022/11/10
 */
public class MetFlowDistributeServiceImpl {
    public static void main(String[] args) {
        // 生成假数据
        HashMap<String, Object> template = new HashMap<>(2);
        template.put("time_unit|1-24", 0);
        template.put("total_num|18-80", 0);
        Mock.set(FlowerDto.class, template);

        MockObject<FlowerDto> mock = Mock.get(FlowerDto.class);

        // 拿到20个list
        List<FlowerDto> list = mock.getList(20);
        System.out.println("随机数据: " + list.toString());

        MetFlowDistributeServiceImpl dataGeneration = new MetFlowDistributeServiceImpl();
        List<FlowerDto> flowerDtos = dataGeneration.processHourData(list, "2022-11-01", "2022-11-10");
        System.out.println("\n\n时段分布数据: " + flowerDtos);

        // List转换为JSON字符串
        Gson gson = new Gson();
        String flowerJSON = gson.toJson(flowerDtos);
        System.out.println("\n\nList转换为JSON: " + flowerJSON);

        // JSON转换为List. 提供两个参数, 分别是json字符串以及需要转换对象的类型
        List<FlowerDto> translationFlowerDto = gson.fromJson(flowerJSON, new TypeToken<List<FlowerDto>>() {
        }.getType());
        System.out.println("\n\nJSON转换为List: " + translationFlowerDto);

        // 转换为列表形式
//        List<FlowerDto> ps = gson.fromJson(flowerJSON, new TypeToken<List<FlowerDto>>(){}.getType());
//        for(int i = 0; i < ps.size() ; i++)
//        {
//            FlowerDto p = ps.get(i);
//            System.out.println(p.toString());
//        }
    }

    /**
     * 处理时段分布数据
     * 时段分布
     *
     * 1、数据：设置的统计分析范围内，指标在各个【时段】（1时~24时）的均值；
     * 1） 计算＝Σ观测时段内某个时段的数值÷观测时间包含该时段的天数；
     * 2）例如
     * ① 9月12日中午12时，统计9月1日~9月11日，14时（13:00~14:00）的均值，为各天14时（13:00~14:00）指标值汇总，除以天数11；
     * ② 9月11日中午12时，统计9月1日~9月11日，14时（13:00~14:00）的均值，为各天14时（13:00~14:00）指标值汇总，除以天数10；
     * 2、图表
     * 1）使用柱状图
     * 2）横坐标轴横坐标轴
     * ① 1时~24时；
     * ② 1时，0点（含）~1点（不含），以此类推；
     *
     * @param flowerDtoList FlowerDto{time_unit='21', total_num=34.0}, 每小时及统计数
     * @param start_dt      开始时间
     * @param end_dt        结束时间
     * @return 计算出每个时段均值 FlowerDto{time_unit='5', total_num=7.8}
     */
    public List<FlowerDto> processHourData(List<FlowerDto> flowerDtoList, String start_dt, String end_dt) {
        List<FlowerDto> resultList = new ArrayList<>();
        List<FlowerDto> flowerDto = Optional.ofNullable(flowerDtoList).orElse(new ArrayList<>());
        Map<String, FlowerDto> dtoMap = flowerDto.stream().collect(Collectors.toMap(FlowerDto::getTime_unit, Function.identity(), (key1, key2) -> key2));
        // 定义24小时的时间
        int[] hours = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24};
        // 获取当前时间
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now_day = dateTime.format(fmt);
        // 解析本地日期
        LocalDate startDate = LocalDate.parse(start_dt, fmt);
        LocalDate endDate = LocalDate.parse(end_dt, fmt);
        // 获取两个时间差
        long dateDiff = startDate.until(endDate, ChronoUnit.DAYS);
        System.out.println("时间差: " + dateDiff);
        for (int i = 0; i < hours.length; i++) {
            String hour = String.valueOf(hours[i]);
            FlowerDto flowerDtoCompare;
            // 根据hours数组中的时间数据, 循环判断
            if (dtoMap.containsKey(hour)) {
                // 赋值初始化
                flowerDtoCompare = dtoMap.get(hour);
                // 如果结束时间=当前时间, 并且当前时间的小时 < 所选时间, 否则除以相隔天数 + 1
                if (now_day.equals(end_dt) && dateTime.getHour() < hours[i]) {
//                    System.out.println("getTotal_num: " + flowerDtoCompare.getTotal_num());
                    flowerDtoCompare.setTotal_num(format(flowerDtoCompare.getTotal_num() / dateDiff));
//                    System.out.println("+0: " + flowerDtoCompare.getTime_unit() + " ---- " + " ---- " + dateDiff + " --- " + flowerDtoCompare.getTotal_num());
                } else {
//                    System.out.println("getTotal_num: " + flowerDtoCompare.getTotal_num());
                    flowerDtoCompare.setTotal_num(format(flowerDtoCompare.getTotal_num()) / (dateDiff + 1));
//                    System.out.println("+1: " + flowerDtoCompare.getTime_unit() + " ---- " + flowerDtoCompare.getTotal_num() + " ---- " + dateDiff + " ---- " + 1 + " --- " + flowerDtoCompare.getTotal_num());
                }
                resultList.add(flowerDtoCompare);
            } else {
                // Map中不存在的时间数据. 赋值初始数据
                flowerDtoCompare = new FlowerDto();
                flowerDtoCompare.setTime_unit(hour);
                flowerDtoCompare.setTotal_num(0D);
            }
            resultList.add(flowerDtoCompare);
        }
        return resultList;
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
