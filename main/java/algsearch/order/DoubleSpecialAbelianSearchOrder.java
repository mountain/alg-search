/*
 * @(#)DoubleSpecialAbelianSearchOrder.java	1.20, 2001/02/26
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */

package algsearch.order;


import math.alg.CayleyTable;

/**
 * 类<code>DoubleSpecialAbelianSearchOrder</code>定义了含两个特殊元素
 * 的交换代数的搜索顺序。该类可用于对交换幺环乘法的搜索。
 *
 * @author 苑明理
 * @version 1.20, 2001/02/26
 * @see algsearch.order.SearchOrder
 */


public class DoubleSpecialAbelianSearchOrder implements SearchOrder {


    protected int x, y;
    protected int order;
    protected CayleyTable table;

    /**
     * 类的构造函数。
     */
    public DoubleSpecialAbelianSearchOrder(CayleyTable t) {
        x = 2;
        y = 2;
        table = t;
        order = table.getOrder();
    }

    /**
     * 获取搜索顺序的最大步长。
     *
     * @return 返回搜索顺序的最大步长
     */
    public int getLength() {
        return (order - 2) * (order - 2);
    }

    /**
     * 判断当前搜索局面是否回到搜索起点。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    public boolean isAtRoot() {
        return (x == 2 && y == 2);
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
            x = (++y);
        }
    }

    /**
     * 在一个局面试探失败后，消除本次试探对凯莱表的修改，从而返回到搜
     * 索树上级结点的局面。
     */
    public void backPos() {
        write(CayleyTable.UNDEFINED);
        if (x > y) {
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
        if (y == 2 && x < order - 1)
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
     * 在凯莱表的当前位置及其转置位置上填入值val。
     */
    public void write(int val) {
        table.set(x, y, val);
        table.set(y, x, val);
    }
}
