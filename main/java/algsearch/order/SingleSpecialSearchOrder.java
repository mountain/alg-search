/*
 * @(#)SingleSpecialSearchOrder.java	1.20, 2001/02/26
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */

package algsearch.order;


import math.alg.CayleyTable;

/**
 * 类<code>SingleSpecialSearchOrder</code>定义了含一个特殊元素的代数
 * 的搜索顺序。该类可用于对群、环乘法等的搜索。
 *
 * @author 苑明理
 * @version 1.20, 2001/02/26
 * @see algsearch.order.SearchOrder
 */


public class SingleSpecialSearchOrder implements SearchOrder {

    protected int x, y;
    protected int order;
    protected CayleyTable table;

    /**
     * 类的构造函数。
     */
    public SingleSpecialSearchOrder(CayleyTable t) {
        x = 1;
        y = 1;
        table = t;
        order = table.getOrder();
    }

    /**
     * 获取搜索顺序的最大步长。
     *
     * @return 返回搜索顺序的最大步长
     */
    public int getLength() {
        return (order - 1) * (order - 1);
    }

    /**
     * 判断当前搜索局面是否回到搜索起点。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    public boolean isAtRoot() {
        return (x == 1 && y == 1);
    }

    /**
     * 判断当前搜索局面是否到达搜索终点。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    public boolean hasNextPos() {
        return (x != (order - 1)) || (y != (order - 1));
    }

    /**
     * 从当前局面出发得到下一个局面，等待框架程序试探不同的可能情况。
     */
    public void nextPos() {
        if (x < order - 1) {
            x++;
        } else {
            x = 1;
            y++;
        }
    }

    /**
     * 在一个局面试探失败后，消除本次试探对凯莱表的修改，从而返回到搜
     * 索树上级结点的局面。
     */
    public void backPos() {
        write(CayleyTable.UNDEFINED);
        if (x > 1) {
            x--;
        } else {
            x = order - 1;
            y--;
        }
    }

    /**
     * 判断当前搜索位置是否还可以试探下一个值。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    public boolean hasNextVal() {
        if (y == 1 && x < order - 1)
            return (table.get(x, y) < x + 2);
        else
            return (table.get(x, y) < order);
    }

    /**
     * 当前搜索位置可以试探的下一个值。
     */
    public int nextVal() {
        int nextVal = table.get(x, y) + 1;
        return nextVal;
    }

    /**
     * 在凯莱表的当前位置上填入值val。
     */
    public void write(int val) {
        table.set(x, y, val);
    }

}
