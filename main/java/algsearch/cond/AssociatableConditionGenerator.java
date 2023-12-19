/*
 * @(#)AssociatableConditionGenerator.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.cond;

import algsearch.expr.CompondExpression;
import algsearch.expr.Expression;
import algsearch.expr.SimpleExpression;
import math.alg.CayleyTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 类<code>AssociatableConditionGenerator</code>是结合律验证条件的生成器，
 * 它生成检验一个给定的凯莱表是否满足结合律的条件集合。
 * <p>
 * 满足结合律是构成半群的重要条件。检验一个给定的凯莱表是否满足结合律，
 * 即验证对所有可能的元素组合(x,y,z)是否有 (x*y)*z = x*(y*z) 恒成立。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.expr.Expression
 * @see algsearch.SemigroupSearch
 * @see algsearch.RingSearch
 */

public class AssociatableConditionGenerator {

    /**
     * 生成检验一个给定的凯莱表是否满足结合律的条件集合。
     *
     * @return 返回检验条件集合
     */
    public static List generate(CayleyTable t) {
        int n = t.getOrder();
        List cList = new ArrayList();
        return generate(t, cList);
    }

    /**
     * 生成检验一个给定的凯莱表是否满足结合律的所有条件，并将它们添加到指
     * 定的条件集合中。
     *
     * @return 返回传入的条件集合，但返回时该集合中已经添加了新的条件。
     */
    public static List generate(CayleyTable t, List cList) {

        int n = t.getOrder();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++) {

                    Expression e1 = new SimpleExpression(i, j, t);
                    Expression e2 = new CompondExpression(e1, k, t);

                    Expression e3 = new SimpleExpression(j, k, t);
                    Expression e4 = new CompondExpression(i, e3, t);

                    Condition cond = new EquationCondition(e2, e4);

                    cList.add(cond);

                }

        return cList;

    }

}
