package org.moncozgc.employ.elevator;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 1. 电梯设计
 * - 1-22楼, 总共A\B两部电梯. 以11楼为中间楼
 * - 用户a在一楼乘电梯, A-12, B-7, B下行. 越靠近一楼的先下行
 * - 用户b在十一楼, A-1, B-22. B下行. 下行先行
 *
 * 上/下行, 用户所在楼层 -> 判断A/B那部电梯去接 -> 那么需要先知道哪部电梯离用户较近
 *
 * Created by MoncozGC on 2023/3/6
 */
public class ElevatorThreadA extends Thread {

    // 电梯开关
    private boolean flag = true;
    // 电梯总楼层
    private final int floorMax = 22;
    // 电梯当前楼层 [-2, 22]
    private final int floorMin = -2;

    @Override
    public void run() {
        // 记录当前
        int walking = -3;
        while (flag) {
            synchronized (this) {

                String[] input = inputScanner();
                System.out.println("用户输入的信息: " + Arrays.toString(input));
                if (input.length == 2 && (Integer.parseInt(input[1]) < floorMin || Integer.parseInt(input[1]) > floorMax)) {
                    System.out.println("输入有误, 请重新输入");
                    input = inputScanner();
                }
                // 电梯运行方向 上/下
                String directionOf = input[0];
                // 运行至几层
                int userFloor = Integer.parseInt(input[1]);

                System.out.println("当前电梯所在楼层: " + walking + " 乘客需到达层: " + userFloor + " 最高楼层为: " + floorMax + " 最低楼层为: " + floorMin);
                // 初始值
                walking = walking == -3 ? -2 : walking;
                if ("上行".equals(directionOf)) {
                    // 上行方向, 但是当前楼层 > 乘客需到达层, 那么就得执行向下方向
                    if (walking > userFloor) {
                        System.out.println("执行下行方向");
                        walking = downside(walking, userFloor);
                    }
                    walking = upside(walking, userFloor);
                } else if ("下行".equals(directionOf)) {
                    // 下行方向, 但是当前楼层 < 乘客需到达层, 那么就得执行向上方向
                    if (walking < userFloor) {
                        System.out.println("执行上行方向");
                        walking = upside(walking, userFloor);
                    }
                    walking = downside(walking, userFloor);
                } else {
                    System.out.println("等待中....");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                // 异常情况电梯退出
                if (walking < floorMin || walking > floorMax) {
                    System.out.println("电梯异常, 即将重置");
                    walking = -2;
                    userFloor = -2;
                }
            }
        }
    }

    /**
     * 根据输入获取参数
     *
     * @return [方向, 楼层]
     */
    private String[] inputScanner() {
        // 获取 上行,所在楼层
        Scanner cin = new Scanner(System.in);
        System.out.println("输入上/下行及所在楼层, 使用逗号隔开:");
        return cin.nextLine().split(",");
    }

    /**
     * 上行
     *
     * @param walking   电梯所在楼层
     * @param userFloor 乘客到达楼层
     * @return 电梯所在楼层
     */
    private int upside(int walking, int userFloor) {
        for (; walking >= floorMin && walking <= floorMax && walking <= userFloor; walking++) {
            System.out.println(Thread.currentThread().getName() + "上行: 当前所在楼层: " + walking);

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        walking--;
        // 重置
        if (walking > floorMax) walking = floorMax;
        return walking;
    }

    /**
     * 下行
     *
     * @param walking   电梯所在楼层
     * @param userFloor 乘客到达楼层
     * @return 电梯所在楼层
     */
    private int downside(int walking, int userFloor) {
        for (; walking >= floorMin && walking <= floorMax && walking >= userFloor; walking--) {
            System.out.println(Thread.currentThread().getName() + "下行: 当前所在楼层: " + walking);

            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        walking++;
        if (walking <= floorMin) walking = floorMin;
        return walking;
    }
}
