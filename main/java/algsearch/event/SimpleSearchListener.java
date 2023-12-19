/*
 * @(#)SimpleSearchListener.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.event;


import math.alg.CayleyTable;

/**
 * 类<code>SimpleSearchListener</code>给出了凯莱表搜索事件监听
 * 器<code>SearchEventListener</code>的一个实现。
 * <p>
 * 类<code>SimpleSearchListener</code>在处理搜索到的凯莱表时，
 * 只是简单的将其打印出来。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.event.SearchEventListener
 */


public class SimpleSearchListener implements SearchEventListener {

    protected int count = 0;

    /**
     * 当搜索到的凯莱表通知给该事件监听类时，它只是简单的将其打印出来。
     */
    public void onSearched(CayleyTable t) {
        count++;
        System.out.println("No." + count);
        int n = t.getOrder();
        printSeprateLine(n);
        printCayleyTable(t, n);
        printSeprateLine(n);

    }

    /**
     * 打印凯莱表。
     */
    protected void printCayleyTable(CayleyTable t, int order) {
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                int val = t.get(i, j);
                System.out.print("   ");
                System.out.print(val);
            }
            System.out.println();
        }
    }

    /**
     * 打印分隔线。
     */
    protected void printSeprateLine(int n) {
        System.out.print("   ");
        for (int i = 0; i < n; i++)
            System.out.print("----");
        System.out.println();
    }
}
