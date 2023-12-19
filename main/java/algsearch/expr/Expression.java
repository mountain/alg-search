/*
 * @(#)Expression.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */

package algsearch.expr;

/**
 * 接口<code>Expression</code>定义了表达式的编程接口。
 * <p>
 * 代数系统一般由满足几个特定运算律来定义，因此在搜索代数系统时，我们需
 * 要检测凯莱表是否满足特定运算律。这就需要我们在程序中定义特定的表达式。
 * <p>
 * 在实现中，我们采用了 Chain Of Responsibility 的设计模式来进行表达式
 * 求值、简化、恢复。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.expr.CompondExpression
 * @see algsearch.expr.SimpleExpression
 * @see algsearch.expr.UnitaryExpression
 */


public interface Expression extends Cloneable {

    int UNDEFINED = -1;
    int UNBOND = -2;

    /**
     * 表达式求值。
     *
     * @return 如果表达式尚未同凯莱表结合，返回UNBOND；
     * 如果凯莱表中尚未给出表达式的确切值，返回UNDEFINED；
     * 如果凯莱表中表达式的值可以求出，返回表达式的值
     */
    int getValue();

    /**
     * 表达式简化。根据凯莱表，复杂的表达式可以简化为简单表达式。
     *
     * @return 简化后的表达式
     */
    Expression simplify();

    /**
     * 恢复表达式。在表达式求值、简化过程中，表达式内部可能会产生一些依赖
     * 于给定凯莱表的中间值；但如果凯莱表发生变化，这些中间值可能就不再正
     * 确；为避免副作用，我们通过resume()方法使表达式恢复最初状态。
     */
    void resume();

}
