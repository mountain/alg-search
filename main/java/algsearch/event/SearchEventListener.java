/*
 * @(#)SearchEventListener.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.event;

import math.alg.CayleyTable;


/**
 * 接口<code>SearchEventListener</code>定义了凯莱表搜索事件监听
 * 器的编程接口。
 * <p>
 * 接口<code>SearchEventListener</code>的设计是Observer设计模式
 * 的一个典型的应用，即<code>SearchEventListener</code>的实现类
 * 监听<code>CayleyTableSearchEngine</code>的搜索结果。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.event.SimpleSearchListener
 * @see algsearch.event.HomoSearchListener
 * @see algsearch.engn.CayleyTableSearchEngine
 */


public interface SearchEventListener {

    /**
     * 当<code>CayleyTableSearchEngine</code>搜索到结果时，
     * 由它负责，通过调用本方法，将搜索结果通知给各个注册的
     * 事件监听程序。
     */
    void onSearched(CayleyTable t);

}
