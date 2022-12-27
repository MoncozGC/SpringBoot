package org.moncozgc.employ.goBang;

import java.awt.*;

public class GoBang {
    private int x;//棋子的x坐标索引
    private int y;//棋子的y坐标索引
    private Color color;//棋子颜色
    public static int DIAMETER = 30;//直径

    public GoBang(int x, int y, Color color) {//棋子构造函数
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

}