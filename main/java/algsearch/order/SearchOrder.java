/*
 * @(#)SearchOrder.java	1.20, 2001/02/26
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */

package algsearch.order;

/**
 * 接口<code>SearchOrder</code>定义了凯莱表搜索顺序的编程接口。
 * <p>
 * 在搜索代数系统时，我们需要按照一定的顺序在凯莱表的不同位置上
 * 进行试探，接口<code>SearchOrder</code> 就是对各种搜索顺序的共
 * 同行为的抽象。
 * <p>
 * 在搜索过程中，我们不断从当前凯莱表上的局面出发，分别在凯莱表
 * 的当前位置上（以及相关位置）试探所有可能的情况，从而形成一棵
 * 搜索树。代数系统的搜索就是遍历这棵搜索树的过程。而从一个局面
 * 出发，选定一种可能的情况后得到另一个局面的过程；一个试探局面
 * 失败后返回上次局面的过程；以及判断一条搜索局面是否到达搜索终
 * 点的过程；所有这些处理过程都是由<code>SearchOrder</code>的实
 * 现类负责的。
 * <p>
 * 对不同的代数系统，它们的搜索顺序可能是不同的；对相同的代数系
 * 统，由于采取的搜索优化措施的不同，相应的搜索程序也有可能采用
 * 不同的搜索顺序。
 * <p>
 * 接口<code>SearchOrder</code>的设计可以理解为Strategy设计模式
 * 的一个应用，即<code>SearchOrder</code>的实现类给出了一个搜索
 * 顺序的策略。
 *
 * @author 苑明理
 * @version 1.20, 2001/02/26
 * @see algsearch.order.UniversalSearchOrder
 * @see algsearch.order.SingleSpecialSearchOrder
 * @see algsearch.order.DoubleSpecialSearchOrder
 * @see algsearch.order.UniversalAbelianSearchOrder
 * @see algsearch.order.SingleSpecialAbelianSearchOrder
 * @see algsearch.order.DoubleSpecialAbelianSearchOrder
 * @see algsearch.engn.CayleyTableSearchEngine
 */

public interface SearchOrder {

    /**
     * <p>
     * 获取搜索顺序的最大步长。
     * <p>
     * 由于实际搜索过程比较复杂，有时很难确切求出搜索步长，或者搜索
     * 步长不定，所以实现该接口的类返回的步长不必等于实际搜索的步长，
     * 但必须大于或等于实际搜索的步长。
     *
     * @return 返回搜索顺序的最大步长
     * @see algsearch.engn.CayleyTableSearchEngine
     */
    int getLength();

    /**
     * 判断当前搜索局面是否回到搜索起点。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    boolean isAtRoot();

    /**
     * 判断当前搜索局面是否到达搜索终点。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    boolean hasNextPos();

    /**
     * 从当前局面出发得到下一个局面，等待框架程序试探不同的可能情况。
     */
    void nextPos();

    /**
     * 在一个局面试探失败后，消除本次试探对凯莱表的修改，从而返回到搜
     * 索树上级结点的局面。
     */
    void backPos();

    /**
     * 判断当前搜索位置是否还可以试探下一个值。
     *
     * @return 当搜索局面尚未到达搜索终点时，返回<code>true</code>;
     * 反之返回<code>false</code>。
     */
    boolean hasNextVal();

    /**
     * 当前搜索位置可以试探的下一个值。
     */
    int nextVal();

    /**
     * 在凯莱表的当前位置上填入值val。实现类也可以根据具体情况在相关的
     * 其他位置上填入适当的值。
     */
    void write(int val);

}
