/*
 * @(#)AbelianGroupSearch.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch;

import algsearch.cond.AssociatableConditionGenerator;
import algsearch.cond.ConditionChecker;
import algsearch.cond.RegularConditionGenerator;
import algsearch.engn.CayleyTableSearchEngine;
import algsearch.event.HomoSearchListener;
import algsearch.order.SearchOrder;
import algsearch.order.SingleSpecialAbelianSearchOrder;
import math.alg.CayleyTable;

import java.util.List;

/*
 *
 * 本程序用于搜索低阶数的交换群。
 *
 * @author  苑明理
 * @version 1.00, 2001/02/19
 *
 */
public class AbelianGroupSearch {
    public static void main(String[] args) {

        int order = 9;
        CayleyTable cTable = new CayleyTable(order, "+");
        initTable(cTable, order);

        SearchOrder sOrder = new SingleSpecialAbelianSearchOrder(cTable);
        List condLst = RegularConditionGenerator.generate(cTable);
        AssociatableConditionGenerator.generate(cTable, condLst);

        ConditionChecker checker = new ConditionChecker(cTable, condLst);

        CayleyTableSearchEngine srchEngine = new CayleyTableSearchEngine(cTable, sOrder, checker);

/*
    SimpleSearchListener l = new SimpleSearchListener();
    srchEngine.addSearchEventListener(l);
    srchEngine.search();
*/

///*
        HomoSearchListener h = new HomoSearchListener(order, 1);
        srchEngine.addSearchEventListener(h);
        srchEngine.search();
        h.printResult();
//*/

    }

    private static void initTable(CayleyTable table, int order) {
        for (int i = 0; i < order; i++) {
            table.set(0, i, i);
            table.set(i, 0, i);
        }
    }

}
