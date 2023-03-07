package com.moncozgc.elevator;

import com.moncozgc.elevator.real.ElevatorThreadA;

/**
 * Created by MoncozGC on 2023/3/6
 */
public class ElevatorMain {
    public static void main(String[] args) {
        ElevatorThreadA elevatorThreadA = new ElevatorThreadA();
        new Thread(elevatorThreadA, "电梯A").start();
        new Thread(elevatorThreadA, "电梯B").start();
    }
}
