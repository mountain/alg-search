/*
 * @(#)HomoSearchListener.java	1.00 2001/02/18
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch.event;

import math.alg.CayleyTable;
import math.alg.Permutation;
import math.alg.SymmetricGroup;
import math.set.EquavilenceClass;
import math.set.EquavilenceRelationship;

import java.util.Iterator;

public class HomoSearchListener implements SearchEventListener {

    private final EquavilenceRelationship eqRltshp = new EquavilenceRelationship(null);
    private final SymmetricGroup sym;
    private int count = 0;
    private int order = -1;
    private int specialElementCount = 0;

    /**
     * 类的构造函数。
     */
    public HomoSearchListener(int o) {
        order = o;
        sym = new SymmetricGroup(order);
    }

    /**
     * 类的构造函数。
     */
    public HomoSearchListener(int o, int sc) {
        order = o;
        specialElementCount = sc;
        sym = new SymmetricGroup(order);
    }

    /**
     * 当搜索到的凯莱表通知给该事件监听类时，它将其记录下来并完
     * 成同构的消除工作。
     */
    public void onSearched(CayleyTable t) {

        count++;
        System.out.println("Find No." + count);
/*
printSeprateLine(order);
printCayleyTable(t);
printSeprateLine(order);
*/
        Iterator iter = sym.iterator();
        Permutation perm = null;
        boolean isNotSlopOver = true;
        while (isNotSlopOver && iter.hasNext()) {
            perm = (Permutation) iter.next();
//System.out.println(perm);
            CayleyTable clone1 = t.clone();
            CayleyTable clone2 = t.clone();
            clone2.exert(perm);
            if (clone2.compareTo(clone1) >= 0) {
                eqRltshp.setEquavilent(clone1, clone2);
/*
          System.out.println("Find Equavilent Of No." + count);
          printSeprateLine(order);
          printCayleyTable(clone2);
          printSeprateLine(order);
*/
            }
            isNotSlopOver = checkPermutation(perm);
        }
    }

    /**
     * 输出记录下来的消除同构之后的搜索结果。
     */
    public void printResult() {
        count = 0;
        Iterator iter = eqRltshp.getEquavilenceClassIterator();
        while (iter.hasNext()) {
            count++;
            System.out.println("Independent No." + count);
            EquavilenceClass equvClass = (EquavilenceClass) iter.next();
            CayleyTable table = (CayleyTable) equvClass.getRepresentation();
            printSeprateLine(order);
            printCayleyTable(table);
            printSeprateLine(order);
        }
    }

    /**
     * 检查置换是否越界。
     */
    protected boolean checkPermutation(Permutation perm) {
        int i = 0;
        boolean isNotSlopOver = true;
        while (isNotSlopOver && i < specialElementCount) {
            isNotSlopOver = (perm.get(i) == i);
            i++;
        }
        return isNotSlopOver;
    }


    /**
     * 打印凯莱表。
     */
    protected void printCayleyTable(CayleyTable t) {
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
