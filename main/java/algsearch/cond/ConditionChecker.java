/*
 * @(#)ConditionChecker.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.cond;

import math.alg.CayleyTable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


/**
 * 类<code>ConditionChecker</code>是搜索条件检测器，是代数系统搜索框架
 * 程序的重要组成部分。
 * <p>
 * 特定代数系统一般要满足特定的条件集合，在代数系统的搜索过程中，随着搜
 * 索树深度的增加，填入凯莱表中元素数目的不断增加，各项条件便可以不断简
 * 化，条件集合也会不断缩小。为了提高搜索的效率，搜索框架程序应当支持对
 * 条件集合的简化。但由于代数系统搜索过程是一棵树的遍历过程，搜索并不会
 * 一直勇往直前，而是会不断回转，于是条件集合的简化也带来一个问题，即当
 * 搜索需要回转时，框架程序也应该能复原已经简化了的条件。这个问题可以通
 * 过将条件集合保存到堆栈中来解决。类<code>ConditionChecker</code>主要
 * 就是负责上述对搜索条件的检测、简化、维护等各项工作的。
 *
 * @author 苑明理
 * @version 1.00, 2001/02/19
 * @see algsearch.cond.EquationCondition
 * @see algsearch.cond.RegularCondition
 * @see algsearch.cond.ConditionChecker
 * @see algsearch.engn.CayleyTableSearchEngine
 */

public class ConditionChecker {

    protected List curCondList;
    protected Stack historyStk;

    protected CayleyTable table;
    protected int[] checkResult;

    /**
     * 类的构造函数。
     */
    public ConditionChecker(CayleyTable t, List cl) {
        table = t;
        curCondList = cl;
        checkResult = null;
        historyStk = new Stack();
    }

    /**
     * 检测当前条件集合是否得到满足。
     *
     * @return 如果凯莱表尚未确定条件集合是否得到满足，返回TRUE；
     * 如果条件集合得到满足，返回TRUE；
     * 如果条件集合没有得到满足，返回FALSE。
     */
    public int check() {
        int n = curCondList.size();
        checkResult = new int[n];
        int i = 0;
        int isPassed = Condition.TRUE;
        while (isPassed == Condition.TRUE && i < n) {
            Condition checkCond = (Condition) curCondList.get(i);
            checkResult[i] = checkCond.check(table);
            if (checkResult[i] == Condition.FALSE) isPassed = Condition.FALSE;
/*
      if(checkResult[i]==Condition.TRUE)
        System.out.println("found " + checkCond + " is true.");
      else if(checkResult[i]==Condition.FALSE)
        System.out.println("found " + checkCond + " is false.");
      else if(checkResult[i]==Condition.UNCERTAIN)
        System.out.println("found " + checkCond + " is uncertain.");
*/
            i++;
        }
        return isPassed;
    }

    /**
     * 准备搜索树下一级节点的搜索。
     * <p>
     * 将当前条件集合压入堆栈，再生成简化条件集合，并将简化条件集合设为
     * 当前条件集合。
     */
    public void next() {
        historyStk.push(curCondList);
        int n = curCondList.size();
        ArrayList temp = new ArrayList(n);
        for (int i = 0; i < n; i++) {
            if (checkResult[i] == Condition.UNCERTAIN) {
                Condition cnd = (Condition) curCondList.get(i);
                Condition sCond = cnd.simplify(table);
//System.out.println("simplify " + cnd +" to " + sCond);
                temp.add(sCond);
            }
        }
        temp.trimToSize();
        curCondList = temp;
        checkResult = null;
    }

    /**
     * 回转搜索过程到搜索树的上级节点。
     * <p>
     * 搜索回转时，原来的条件集合从堆栈中弹出成为当前条件集合，同时将该条
     * 件集合中的条件恢复到初始状态。
     */
    public void back() {
        curCondList = (List) historyStk.pop();
        resumeConditions();
    }

    /**
     * 将当前条件集合中的条件恢复到初始状态。
     * <p>
     * 当前条件集合中的条件有可能包含错误的中间值，在重新使用条件集合前，
     * 我们需要将这些条件恢复到初始状态。
     */
    public void resumeConditions() {
        Iterator iter = curCondList.iterator();
        while (iter.hasNext()) {
            Condition cond = (Condition) iter.next();
            cond.resume();
        }
    }

}
