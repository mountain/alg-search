/*
 * @(#)CayleyTableSearchEngine.java	1.20 2001/02/25
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.engn;

import algsearch.cond.Condition;
import algsearch.cond.ConditionChecker;
import algsearch.event.SearchEventListener;
import algsearch.order.SearchOrder;
import math.alg.CayleyTable;

import java.util.ArrayList;
import java.util.List;

/**
 * 类<code>CayleyTableSearchEngine</code>是代数系统搜索框架程序的主干程序。
 * <p>
 * 在搜索代数系统时，我们将按照一定的顺序在凯莱表的不同位置上逐一试探不同
 * 的值，如果在凯莱表的当前位置curPos上取值val同给定条件集合不矛盾，那么我
 * 们就再去试探下一位置，这样便形成了一个按深度优先顺序遍历树的过程。如果
 * 在遍历过程中我们到达树的叶节点，那么这个叶节点便对应一个搜索结果。
 * <p>
 * 在上述过程中类<code>CayleyTableSearchEngine</code>负责整个树遍历过程的
 * 控制；接口<code>SearchOrder</code>负责确定在凯莱表上的搜索顺序以及修改
 * 凯莱表的工作；类<code>ConditionChecker</code>负责搜索条件的检测和管理。
 * <p>
 * 为了增加灵活性，当搜索到一个结果时处理工作并不由本类负责，相反本类只是
 * 负责将搜索结果通知给各个注册的搜索事件监听类，再由搜索事件监听类处理搜
 * 索结果。
 * <p>
 * 类<code>CayleyTableSearchEngine</code>、接口<code>SearchOrder</code>与
 * 类<code>ConditionChecker</code>三者相互分工协作，共同完成对代数系统的
 * 搜索，我们可以视之为Strategy设计模式的应用。
 * <p>
 * 类<code>CayleyTableSearchEngine</code>与接口<code>SearchEventListener</code>
 * 的协作是Observer设计模式的典型应用，即接口<code>SearchEventListener</code>
 * 的实现类监听<code>CayleyTableSearchEngine</code>的搜索结果。
 *
 * @author 苑明理
 * @version 1.20, 2001/02/26
 * @see algsearch.order.SearchOrder
 * @see algsearch.cond.ConditionChecker
 * @see algsearch.event.SearchEventListener
 */
public class CayleyTableSearchEngine {

    protected CayleyTable table;
    protected SearchOrder srchOrder;
    protected ConditionChecker checker;
    protected List lstns;

    /**
     * 类的构造函数。
     */
    public CayleyTableSearchEngine(CayleyTable t, SearchOrder o, ConditionChecker c) {
        table = t;
        srchOrder = o;
        checker = c;
        lstns = new ArrayList();
    }


    /**
     * 测试搜索树的下一个节点。
     */
    protected void nextPosition() {
        srchOrder.nextPos();
        srchOrder.write(srchOrder.nextVal());
        checker.next();
/*
    int n = table.getOrder();
    printSeprateLine(n);
    System.out.println("Check next position.");
    printSeprateLine(n);
    printCayleyTable(table,n);
    printSeprateLine(n);
*/
    }

    /**
     * 测试搜索树的当前节点的下一个值。
     */
    protected void nextValue() {
        checker.resumeConditions();
        srchOrder.write(srchOrder.nextVal());
/*
    int n = table.getOrder();
    printSeprateLine(n);
    System.out.println("Check next value.");
    printSeprateLine(n);
    printCayleyTable(table,n);
    printSeprateLine(n);
*/
    }

    /**
     * 返回搜索树的当前节点的上级节点，并测试上级节点的下一个值。
     */
    protected void back() {
        srchOrder.backPos();
        srchOrder.write(srchOrder.nextVal());
        checker.back();
/*
    int n = table.getOrder();
    printSeprateLine(n);
    System.out.println("Check position back.");
    printSeprateLine(n);
    printCayleyTable(table,n);
    printSeprateLine(n);
*/
    }

    /**
     * 注册搜索事件监听器。
     */
    public void addSearchEventListener(SearchEventListener l) {
        lstns.add(l);
    }

    /**
     * 触发搜索事件
     */
    protected void fireEvent() {
        int size = lstns.size();
        for (int i = 0; i < size; i++) {
            SearchEventListener l = (SearchEventListener) (lstns.get(i));
            l.onSearched(table);
        }
    }

    /**
     * 搜索过程。
     */
    public void search() {

        nextValue();
        while (srchOrder.hasNextVal() || !srchOrder.isAtRoot()) {

            if (!srchOrder.hasNextVal()) {

                back();

            } else {

                int isPassed = checker.check();
                if (isPassed == Condition.FALSE) {
                    nextValue();
                } else {
                    if (srchOrder.hasNextPos()) {
                        nextPosition();
                    } else {
                        fireEvent();
                        nextValue();
                    }
                }

            }

        }

    }

    /**
     * 打印凯莱表。
     */
    protected void printCayleyTable(CayleyTable t, int order) {
        for (int i = 0; i < order; i++) {
            for (int j = 0; j < order; j++) {
                int val = t.get(i, j);
                System.out.print("   ");
                System.out.print(val);
            }
            System.out.println();
        }
    }

    /**
     * 打印分隔线。
     */
    protected void printSeprateLine(int n) {
        System.out.print("   ");
        for (int i = 0; i < n; i++)
            System.out.print("----");
        System.out.println();
    }

}
