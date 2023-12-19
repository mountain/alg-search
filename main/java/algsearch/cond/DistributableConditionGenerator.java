/*
 * @(#)DistributableConditionGenerator.java	1.00 2001/02/18
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
 * 类<code>DistributableConditionGenerator</code>是分配律验证条件的生成器，
 * 它生成检验一个给定的凯莱表是否满足分配律的条件集合。
 * <p>
 * 满足分配律是构成环的重要条件。检验一个给定的凯莱表是否满足分配律，
 * 即验证对所有可能的元素组合(x,y,z)是否有 (x+y)*z = x*z+y*z 和
 * x*(y+z) = x*y+x*z 恒成立。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.expr.Expression
 * @see algsearch.RingSearch
 */

public class DistributableConditionGenerator {

    /**
     * 生成检验一个给定的凯莱表是否满足分配律的条件集合。
     *
     * @return 返回检验条件集合
     */
    public static List generate(CayleyTable add, CayleyTable mult) {
        int n = add.getOrder();
        List cList = new ArrayList();
        return generate(add, mult, cList);
    }

    /**
     * 生成检验一个给定的凯莱表是否满足分配律的所有条件，并将它们添加到指
     * 定的条件集合中。
     *
     * @return 返回传入的条件集合，但返回时该集合中已经添加了新的条件。
     */
    public static List generate(CayleyTable add, CayleyTable mult, List cList) {

        int n = add.getOrder();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++) {

                    //条件(i+j)*k==i*k+j*k
                    Expression e1 = new SimpleExpression(i, j, add);
                    Expression e2 = new CompondExpression(e1, k, mult);
                    Expression e3 = new SimpleExpression(i, k, mult);
                    Expression e4 = new SimpleExpression(j, k, mult);
                    Expression e5 = new CompondExpression(e3, e4, add);
                    Condition cond1 = new EquationCondition(e2, e5);
                    cList.add(cond1);

                    //条件i*(j+k)==i*j+i*k
                    Expression e6 = new SimpleExpression(j, k, add);
                    Expression e7 = new CompondExpression(i, e6, mult);
                    Expression e8 = new SimpleExpression(i, j, mult);
                    Expression e9 = new SimpleExpression(i, k, mult);
                    Expression e10 = new CompondExpression(e8, e9, add);
                    Condition cond2 = new EquationCondition(e7, e10);
                    cList.add(cond2);

                }

        return cList;

    }

}
