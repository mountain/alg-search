/*
 * @(#)EquationCondition.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.cond;

import algsearch.expr.Expression;
import math.alg.CayleyTable;

/**
 * 类<code>EquationCondition</code>定义了等式检测条件，实现了接口
 * <code>Condition</code>。
 * <p>
 * 在搜索半群、群、环时，我们需要检测结合律、分配律是否成立，因而我们
 * 需要检测两个表达式是否相等。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.cond.Condition
 */
public class EquationCondition implements Condition {

    protected int lVal, rVal;
    protected Expression lExp, rExp;

    /**
     * 类的构造函数。
     */
    public EquationCondition(Expression e1, Expression e2) {

        lExp = e1;
        rExp = e2;
        lVal = Expression.UNBOND;
        rVal = Expression.UNBOND;

    }

    /**
     * 类的构造函数。
     */
    public EquationCondition(Expression e, int val) {
        lExp = e;
        rExp = null;
        lVal = Expression.UNBOND;
        rVal = val;
    }

    /**
     * 检测两表达式是否相等，从而确定条件是否满足。
     *
     * @return 如果凯莱表尚未确定两表达式是否相等，返回UNCERTAIN；
     * 如果两表达式相等，返回TRUE；
     * 如果两表达式不相等，返回FALSE。
     */
    public int check(CayleyTable t) {

        int rslt = UNCERTAIN;

//    lVal = lExp.getValue();
        if (lExp != null) lVal = lExp.getValue();
        if (rExp != null) rVal = rExp.getValue();

        if (lVal != Expression.UNDEFINED && rVal != Expression.UNDEFINED) {
            if (lVal == rVal)
                rslt = TRUE;
            else
                rslt = FALSE;
        }

        return rslt;

    }

    /**
     * 根据凯莱表简化表达式，从而简化条件。
     *
     * @return 简化后的条件
     */
    public Condition simplify(CayleyTable t) {

        if (lVal == Expression.UNBOND || rVal == Expression.UNBOND) check(t);

        EquationCondition smplCond = null;
        if (lVal == Expression.UNDEFINED) {
            Expression nlExp = lExp.simplify();
            if (rVal == Expression.UNDEFINED) {
                Expression nrExp = rExp.simplify();
                smplCond = new EquationCondition(nlExp, nrExp);
            } else
                smplCond = new EquationCondition(nlExp, rVal);
        } else {
            Expression nrExp = rExp.simplify();
            smplCond = new EquationCondition(nrExp, lVal);
        }

        return smplCond;

    }

    /**
     * 恢复条件初始状态。在条件检测、简化过程中，条件内部可能会产生一些依
     * 赖于给定凯莱表的中间值；但如果凯莱表发生变化，这些中间值可能就不再
     * 正确；为避免副作用，我们通过resume()方法使条件恢复最初状态。
     */
    public void resume() {
        lVal = Expression.UNBOND;
        if (lExp != null) lExp.resume();
        if (rExp != null) {
            rVal = Expression.UNBOND;
            rExp.resume();
        }
    }

    /**
     * 给出条件的字符表达。
     */
    public String toString() {
        if (rExp != null)
            return "if " + lExp + " == " + rExp;
        else
            return "if " + lExp + " == " + rVal;
    }

}
