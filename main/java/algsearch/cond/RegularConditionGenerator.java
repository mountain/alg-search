/*
 * @(#)RegularConditionGenerator.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.cond;

import math.alg.CayleyTable;

import java.util.ArrayList;
import java.util.List;


/**
 * 类<code>RegularConditionGenerator</code>是正则验证条件的生成器，它
 * 生成检验一个给定的凯莱表是否满足正则条件的条件集合。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/19
 * @see algsearch.cond.RegularCondition
 * @see algsearch.GroupSearch
 * @see algsearch.AbelianGroupSearch
 */
public class RegularConditionGenerator {

    /**
     * 生成检验一个给定的凯莱表是否满足正则条件的条件集合。
     *
     * @return 返回检验条件集合
     */
    public static List generate(CayleyTable t) {
        int n = t.getOrder();
        List cList = new ArrayList();
        return generate(t, cList);
    }

    /**
     * 生成检验一个给定的凯莱表是否满足正则条件的所有条件，并将它们添加到
     * 指定的条件集合中。
     *
     * @return 返回传入的条件集合，但返回时该集合中已经添加了新的条件。
     */
    public static List generate(CayleyTable t, List cList) {

        int n = t.getOrder();
        for (int ind = 1; ind < n; ind++)
            for (int val = 0; val < n; val++) {
                RegularCondition rCond = new RegularCondition(true, ind, val);
                RegularCondition cCond = new RegularCondition(false, ind, val);
                cList.add(rCond);
                cList.add(cCond);
            }

        return cList;

    }
}
