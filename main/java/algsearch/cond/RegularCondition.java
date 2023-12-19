/*
 * @(#)RegularCondition.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.cond;

import math.alg.CayleyTable;

/**
 * 类<code>RegularCondition</code>定义了正则检测条件，实现了接口
 * <code>Condition</code>。
 * <p>
 * 在搜索群时，我们需要检测群中的元素是否都是正则的，因此我们需要
 * 在程序中给出正则条件的定义。对有限群来说，正则条件表现为任意一
 * 行、任意一列中任意一个元素出现并只出现一次。
 * <p>
 * 类<code>RegularCondition</code>就是用来检测某一行或列中，某一元
 * 素是否出现并只出现一次。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/19
 * @see algsearch.cond.Condition
 */
public class RegularCondition implements Condition {

    protected boolean isOnRow;
    protected int chkVal;
    protected int index;
    protected int startPos, endPos;
    protected int lastHitTime, curHitTime;

    /**
     * 类的公开的构造函数。
     */
    public RegularCondition(boolean isChkOnRow, int ind, int val) {

        isOnRow = isChkOnRow;
        index = ind;
        chkVal = val;

        startPos = 0;
        endPos = -1;

        lastHitTime = 0;
        curHitTime = 0;

    }

    /**
     * 类的私有的构造函数，主要用于生成简化后的条件。
     */
    private RegularCondition(boolean isChkOnRow, int ind, int val, int lastHitNum, int lastEndPos) {

        isOnRow = isChkOnRow;
        index = ind;
        chkVal = val;

        startPos = lastEndPos;
        endPos = -1;

        lastHitTime = lastHitNum;
        curHitTime = 0;

    }

    /**
     * 检测检测某一行或列中，某一元素是否出现并只出现一次。
     *
     * @return 如果凯莱表尚未确定条件是否满足，返回UNCERTAIN；
     * 如果条件满足，返回TRUE；
     * 如果条件不满足，返回FALSE。
     */
    public int check(CayleyTable t) {
        int order = t.getOrder();

        int pos = startPos;
        int hitTime = 0;
        if (isOnRow) {
            int cmpVal = t.get(pos, index);
            while (cmpVal != CayleyTable.UNDEFINED && pos != order) {
                if (cmpVal == chkVal) hitTime++;
                pos++;
                if (pos != order) cmpVal = t.get(pos, index);
            }
        } else {
            int cmpVal = t.get(index, pos);
            while (cmpVal != CayleyTable.UNDEFINED && pos != order) {
                if (cmpVal == chkVal) hitTime++;
                pos++;
                if (pos != order) cmpVal = t.get(index, pos);
            }
        }

        endPos = pos;
        curHitTime = lastHitTime + hitTime;

        if (curHitTime > 1 || (curHitTime == 0 && pos == order))
            return FALSE;
        else if (curHitTime == 1 && pos == order)
            return TRUE;
        else
            return UNCERTAIN;

    }

    /**
     * 简化条件。根据凯莱表，复杂的条件可以简化为简单条件。
     *
     * @return 简化后的条件
     */
    public Condition simplify(CayleyTable t) {
        return new RegularCondition(isOnRow, index, chkVal, curHitTime, endPos);
    }

    /**
     * 恢复条件初始状态。在条件检测、简化过程中，条件内部可能会产生一些依
     * 赖于给定凯莱表的中间值；但如果凯莱表发生变化，这些中间值可能就不再
     * 正确；为避免副作用，我们通过resume()方法使条件恢复最初状态。
     */
    public void resume() {
    }

    /**
     * 给出条件的字符表达。
     */
    public String toString() {
        String str = null;
        if (isOnRow) {
            str = "if there has " + chkVal + " in row " + index + " in one time";
        } else {
            str = "if there has " + chkVal + " in coloum " + index + " in one time";
        }
        return str;
    }

}
