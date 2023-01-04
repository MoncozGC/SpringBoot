package com.moncozgc.mall.service.dataStructureImpl;

import com.moncozgc.mall.service.DataStructureService;
import org.springframework.stereotype.Service;

/**
 * Created by MoncozGC on 2022/12/22
 */
@Service
public class DataStructureServiceImpl implements DataStructureService {

    /**
     * 五子棋棋盘 <=> 稀疏数组
     */
    public void GobangToSparseArray() {
        // 1. 首先知道五子棋棋盘的位置
        int[][] goBang = new int[11][11];
        // 0: 未动; 1: 黑子; 2: 白子
        goBang[1][2] = 1;
        goBang[2][3] = 2;
        // 记录棋子的有效数量
        int sum = 0;
        System.out.println("棋盘数据: ");
        for (int[] ints : goBang) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
                if (anInt != 0) {
                    sum++;
                }
            }
            System.out.println();
        }
        System.out.println("有效棋子数量: " + sum);

        // 2. 棋盘转换为稀疏数组
        int[][] SparseArray = new int[sum + 1][3];
        SparseArray[0][0] = goBang.length;
        SparseArray[0][1] = goBang.length;
        SparseArray[0][2] = sum;
        System.out.println("稀疏数组: ");
        int count = 1;
        for (int i = 1; i < goBang.length; i++) {
            for (int j = 0; j < goBang.length; j++) {
                if (goBang[i][j] != 0) {
                    SparseArray[count][0] = i;
                    SparseArray[count][1] = j;
                    SparseArray[count][2] = goBang[i][j];
                    count++;
                }
            }
        }
        for (int[] ints : SparseArray) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println();
        }

        // 3. 稀疏数组转换为五子棋棋盘
        int[][] goBangTo = new int[SparseArray[0][0]][SparseArray[0][1]];
        System.out.println("稀疏数组展开为五子棋棋盘: ");
        for (int i = 1; i < SparseArray.length; i++) {
            goBangTo[SparseArray[i][0]][SparseArray[i][1]] = SparseArray[i][2];
        }

        for (int[] ints : goBangTo) {
            for (int anInt : ints) {
                System.out.printf("%d\t", anInt);
            }
            System.out.println();
        }
    }

}
