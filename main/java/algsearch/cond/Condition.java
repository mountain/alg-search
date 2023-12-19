/*
 * @(#)Condition.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.cond;


import math.alg.CayleyTable;

/**
 * 接口<code>Condition</code>定义了搜索条件编程接口。
 * <p>
 * 特定代数系统一般要满足特定的条件，因此在搜索代数系统时，我们需要检测
 * 凯莱表是否满足特定的搜索条件。这就需要我们在程序中定义搜索条件。
 * <p>
 * 在实现中，我们采用了 Chain Of Responsibility 的设计模式来进行条件的
 * 检测、简化、恢复。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/18
 * @see algsearch.cond.EquationCondition
 * @see algsearch.cond.RegularCondition
 * @see algsearch.cond.ConditionChecker
 * @see algsearch.engn.CayleyTableSearchEngine
 */

public interface Condition {

    int FALSE = -1;
    int UNCERTAIN = 0;
    int TRUE = 1;

    /**
     * 检测条件。
     *
     * @return 如果凯莱表尚未确定条件是否满足，返回UNCERTAIN；
     * 如果条件满足，返回TRUE；
     * 如果条件不满足，返回FALSE。
     */
    int check(CayleyTable t);

    /**
     * 简化条件。根据凯莱表，复杂的条件可以简化为简单条件。
     *
     * @return 简化后的条件
     */
    Condition simplify(CayleyTable t);

    /**
     * 恢复条件初始状态。在条件检测、简化过程中，条件内部可能会产生一些依
     * 赖于给定凯莱表的中间值；但如果凯莱表发生变化，这些中间值可能就不再
     * 正确；为避免副作用，我们通过resume()方法使条件恢复最初状态。
     */
    void resume();

}
