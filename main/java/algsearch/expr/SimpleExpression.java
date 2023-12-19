/*
 * @(#)SimpleExpression.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */

package algsearch.expr;

import math.alg.CayleyTable;


/**
 * 类<code>SimpleExpression</code>定义了简单表达式。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/19
 */

public class SimpleExpression implements Expression {

    protected CayleyTable op;
    protected int lVal = UNBOND, rVal = UNBOND, val = UNBOND;

    /**
     * 类的构造函数。
     */
    public SimpleExpression(int v1, int v2, CayleyTable t) {
        lVal = v1;
        rVal = v2;
        op = t;
    }

    /**
     * 表达式求值。
     *
     * @return 如果表达式尚未同凯莱表结合，返回UNBOND；
     * 如果凯莱表中尚未给出表达式的确切值，返回UNDEFINED；
     * 如果凯莱表中表达式的值可以求出，返回表达式的值
     */
    public int getValue() {
        if (val == UNBOND) val = op.get(lVal, rVal);
        return val;
    }

    /**
     * 表达式克隆。
     */
    public Object clone() {
        return new SimpleExpression(lVal, rVal, op);
    }

    /**
     * 表达式简化。根据凯莱表，复杂的表达式可以简化为简单表达式。
     *
     * @return 简化后的表达式
     */
    public Expression simplify() {
        //假设了CayleyTable上的不确定值只能为UNDEFINED
        if (val == UNBOND) getValue();
        if (val == UNDEFINED)
            return (Expression) clone();
        else
            return new UnitaryExpression(val);
    }

    /**
     * 恢复表达式。在表达式求值、简化过程中，表达式内部可能会产生一些依赖
     * 于给定凯莱表的中间值；但如果凯莱表发生变化，这些中间值可能就不再正
     * 确；为避免副作用，我们通过resume()方法使表达式恢复最初状态。
     */
    public void resume() {
        val = UNBOND;
    }

    /**
     * 给出表达式的字符表示。
     */
    public String toString() {
        return "(" + lVal + op.toString() + rVal + ")";
    }

}
