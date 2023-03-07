package com.moncozgc.elevator.real;

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
    private int floorMax = 22;
    // 电梯当前楼层 [-2, 22]
    private int floorMin = -2;

    @Override
    public void run() {
        // 记录当前
        int walking = -3;
        while (flag) {
            synchronized (this) {

                // 获取 上行,所在楼层
                Scanner cin = new Scanner(System.in);
                System.out.println("输入上/下行及所在楼层:");
                String input = cin.nextLine();
                System.out.println(input);

                System.out.println("当前电梯所在楼层: " + walking + " 最高楼层为: " + floorMax + " 最低楼层为: " + floorMin);
                walking = walking == -3 ? -2 : walking;
                if (input.equals("上行")) {
                    for (; walking >= floorMin && walking <= floorMax; walking++) {
                        System.out.println(Thread.currentThread().getName() + "上行: 当前所在楼层: " + walking);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 重置
                    if (walking > floorMax) walking = floorMax;
                } else if (input.equals("下行")) {
                    for (; walking >= floorMin && walking <= floorMax; walking--) {
                        System.out.println(Thread.currentThread().getName() + "下行: 当前所在楼层: " + walking);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (walking <= floorMin) walking = floorMin;
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
                    flag = false;
                }
            }
        }
    }
}
