/*
 * @(#)CompondExpression.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */

package algsearch.expr;

import math.alg.CayleyTable;


/**
 * 类<code>CompondExpression</code>定义了复合表达式。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/19
 */

public class CompondExpression implements Expression {

    protected CayleyTable op;
    protected Expression lExp;
    protected Expression rExp;
    protected int lVal = UNBOND, rVal = UNBOND, val = UNBOND;

    /**
     * 类的构造函数。
     */
    public CompondExpression(Expression e1, Expression e2, CayleyTable t) {

        op = t;
        lExp = e1;
        rExp = e2;

    }

    /**
     * 类的构造函数。
     */
    public CompondExpression(Expression e, int v, CayleyTable t) {

        op = t;
        lExp = e;
        rExp = null;
        rVal = v;

    }

    /**
     * 类的构造函数。
     */
    public CompondExpression(int v, Expression e, CayleyTable t) {

        op = t;
        lExp = null;
        rExp = e;
        lVal = v;

    }

    /**
     * 表达式求值。
     *
     * @return 如果表达式尚未同凯莱表结合，返回UNBOND；
     * 如果凯莱表中尚未给出表达式的确切值，返回UNDEFINED；
     * 如果凯莱表中表达式的值可以求出，返回表达式的值
     */
    public int getValue() {

        if (lVal == UNBOND && lExp != null)
            lVal = lExp.getValue();
        if (rVal == UNBOND && rExp != null)
            rVal = rExp.getValue();

        if (lVal > UNDEFINED && rVal > UNDEFINED)
            val = op.get(lVal, rVal);
        else
            val = UNDEFINED;

        return val;

    }

    /**
     * 表达式克隆。
     */
    public Object clone() {

        Expression exp = null;
        if (lExp != null && rExp != null) {
            exp = new CompondExpression(lExp, rExp, op);
        } else if (lExp == null && rExp != null) {
            exp = new CompondExpression(lVal, rExp, op);
        } else if (lExp != null && rExp == null) {
            exp = new CompondExpression(lExp, rVal, op);
        }
        return exp;

    }

    /**
     * 表达式简化。根据凯莱表，复杂的表达式可以简化为简单表达式。
     *
     * @return 简化后的表达式
     */
    public Expression simplify() {

        if (val == UNBOND) getValue();

        Expression sExp = null;

        if (val > UNDEFINED) {
            sExp = new UnitaryExpression(val);
        } else {
            Expression nlExp;
            Expression nrExp;
            if (lVal == UNDEFINED && rVal == UNDEFINED) {
                nlExp = lExp.simplify();
                nrExp = rExp.simplify();
                sExp = new CompondExpression(nlExp, nrExp, op);
            } else if (lVal == UNDEFINED && rVal > UNDEFINED) {
                nlExp = lExp.simplify();
                sExp = new CompondExpression(nlExp, rVal, op);
            } else if (lVal > UNDEFINED && rVal == UNDEFINED) {
                nrExp = rExp.simplify();
                sExp = new CompondExpression(lVal, nrExp, op);
            } else if (lVal > UNDEFINED && rVal > UNDEFINED) {
                sExp = new SimpleExpression(lVal, rVal, op);
            }
        }

        return sExp;
    }

    /**
     * 恢复表达式。在表达式求值、简化过程中，表达式内部可能会产生一些依赖
     * 于给定凯莱表的中间值；但如果凯莱表发生变化，这些中间值可能就不再正
     * 确；为避免副作用，我们通过resume()方法使表达式恢复最初状态。
     */
    public void resume() {
        val = UNBOND;
        if (lExp != null && rExp != null) {
            lVal = UNBOND;
            lExp.resume();
            rVal = UNBOND;
            rExp.resume();
        } else if (lExp == null && rExp != null) {
            rVal = UNBOND;
            rExp.resume();
        } else if (lExp != null && rExp == null) {
            lVal = UNBOND;
            lExp.resume();
        }
    }

    /**
     * 给出表达式的字符表示。
     */
    public String toString() {
        if (lExp != null && rExp != null) {
            return "(" + lExp + op.toString() + rExp.toString() + ")";
        } else if (lExp == null && rExp != null) {
            return "(" + lVal + op.toString() + rExp.toString() + ")";
        } else if (lExp != null && rExp == null) {
            return "(" + lExp + op.toString() + rVal + ")";
        }
        return "";
    }

}
