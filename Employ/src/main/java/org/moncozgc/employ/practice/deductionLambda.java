package org.moncozgc.employ.practice;

import java.util.Objects;

/**
 * Lambda推导过程
 * 前提: 需符合函数式编程即接口下只有一个抽象方法. 如Runnable
 * Created by MoncozGC on 2023/3/8
 */
public class deductionLambda {

    /**
     * 2. 使用静态代码块
     */
    static class addTargetReal2 implements addTargetImpl {
        @Override
        public void addTargetMethod(int num) {
            System.out.println("目标 --> " + num);
        }
    }

    public static void main(String[] args) {
        // 使用多态
        addTargetImpl addTargetReal = new addTargetReal();
        Objects.requireNonNull(addTargetReal).addTargetMethod(1);

        addTargetReal = new addTargetReal2();
        addTargetReal.addTargetMethod(2);

        /*
          3. 使用内部类
         */
        class addTargetReal3 implements addTargetImpl {
            @Override
            public void addTargetMethod(int num) {
                System.out.println("目标 --> " + num);
            }
        }
        addTargetReal = new addTargetReal3();
        addTargetReal.addTargetMethod(3);

        /*
         * 4. 使用匿名内部类
         */
        addTargetReal = new addTargetImpl() {
            @Override
            public void addTargetMethod(int num) {
                System.out.println("目标 --> " + num);
            }
        };
        addTargetReal.addTargetMethod(4);


        /*
        5. 简化使用Lambda方式
         */
        addTargetReal = (num) -> {
            System.out.println("目标 --> " + num);
        };
        addTargetReal.addTargetMethod(5);

    }
}

/**
 * 定义接口
 */
interface addTargetImpl {
    void addTargetMethod(int num);
}

/**
 * 1. 类实现接口
 */
class addTargetReal implements addTargetImpl {
    @Override
    public void addTargetMethod(int num) {
        System.out.println("目标 --> " + num);
    }
}
