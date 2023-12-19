/*
 * @(#)AbelianRingSearch.java	1.00 2001/02/19
 *
 * 本程序是代数系统搜索框架程序的一部分。代数系统搜索框架程序用于搜索
 * 低阶数的代数系统实例，包括半群、群、环以及一些非交换代数的实例。
 *
 */
package algsearch;

import algsearch.cond.AssociatableConditionGenerator;
import algsearch.cond.ConditionChecker;
import algsearch.cond.DistributableConditionGenerator;
import algsearch.engn.CayleyTableSearchEngine;
import algsearch.event.HomoSearchListener;
import algsearch.order.SearchOrder;
import algsearch.order.SingleSpecialAbelianSearchOrder;
import math.alg.CayleyTable;

import java.util.List;

/*
 *
 * 本程序用于搜索低阶数的交换环。
 *
 * @author  苑明理
 * @version 1.00, 2001/02/19
 *
 */
public class AbelianRingSearch {

/*
  public static final int order = 2;
  public static final int[][] addArray =
    {
       {0,1},
       {1,2}
    };
*/

/*
  public static final int order = 3;
  public static final int[][] addArray =
    {
       {0,1,2},
       {1,2,0},
       {2,0,1},
    };
*/

/*
  public static final int order = 4;
  public static final int[][] addArray =
    {
       {0,1,2,3},
       {1,0,3,2},
       {2,3,0,1},
       {3,2,1,0}
    };
*/

/*
  public static final int order = 4;
  public static final int[][] addArray =
    {
       {0,1,2,3},
       {1,0,3,2},
       {2,3,1,0},
       {3,2,0,1}
    };
*/

/*
  public static final int order = 5;
  public static final int[][] addArray =
    {
       {0,1,2,3,4},
       {1,2,3,4,0},
       {2,3,4,0,1},
       {3,4,0,1,2},
       {4,0,1,2,3}
    };
*/

/*
  public static final int order = 6;
  public static final int[][] addArray =
    {
       {0,1,2,3,4,5},
       {1,0,3,2,5,4},
       {2,3,4,5,0,1},
       {3,2,5,4,1,0},
       {4,5,0,1,2,3},
       {5,4,1,0,3,2}
    };
*/

/*
  public static final int order = 7;
  public static final int[][] addArray =
    {
       {0,1,2,3,4,5,6},
       {1,2,3,4,5,6,0},
       {2,3,4,5,6,0,1},
       {3,4,5,6,0,1,2},
       {4,5,6,0,1,2,3},
       {5,6,0,1,2,3,4},
       {6,0,1,2,3,4,5}
    };
*/

/*
  public static final int order = 8;
  public static final int[][] addArray =
    {
      {0,1,2,3,4,5,6,7},
      {1,0,3,2,5,4,7,6},
      {2,3,0,1,6,7,4,5},
      {3,2,1,0,7,6,5,4},
      {4,5,6,7,0,1,2,3},
      {5,4,7,6,1,0,3,2},
      {6,7,4,5,2,3,0,1},
      {7,6,5,4,3,2,1,0}
    };
*/

/*
  public static final int order = 8;
  public static final int[][] addArray =
    {
      {0,1,2,3,4,5,6,7},
      {1,0,3,2,5,4,7,6},
      {2,3,0,1,6,7,4,5},
      {3,2,1,0,7,6,5,4},
      {4,5,6,7,1,0,3,1},
      {5,4,7,6,0,1,2,3},
      {6,7,4,5,3,2,1,0},
      {7,6,5,4,2,3,0,1}
    };
*/

/*
  public static final int order = 8;
  public static final int[][] addArray =
    {
      {0,1,2,3,4,5,6,7},
      {1,0,3,2,5,4,7,6},
      {2,3,1,0,6,7,5,4},
      {3,2,0,1,7,6,4,5},
      {4,5,6,7,2,3,1,0},
      {5,4,7,6,3,2,0,1},
      {6,7,5,4,1,0,3,2},
      {7,6,4,5,0,1,2,3}
    };
*/


/*
  public static final int order = 9;
  public static final int[][] addArray =
    {
      {0,1,2,3,4,5,6,7,8},
      {1,2,0,4,5,3,7,8,6},
      {2,0,1,5,3,4,8,6,7},
      {3,4,5,6,7,8,0,1,2},
      {4,5,3,7,8,6,1,2,0},
      {5,3,4,8,6,7,2,0,1},
      {6,7,8,0,1,2,3,4,5},
      {7,8,6,1,2,0,4,5,3},
      {8,6,7,2,0,1,5,3,4}
    };
*/

    ///*
    public static final int order = 9;
    public static final int[][] addArray =
        {
            {0, 1, 2, 3, 4, 5, 6, 7, 8},
            {1, 2, 0, 4, 5, 3, 7, 8, 6},
            {2, 0, 1, 5, 3, 4, 8, 6, 7},
            {3, 4, 5, 6, 7, 8, 1, 2, 0},
            {4, 5, 3, 7, 8, 6, 2, 0, 1},
            {5, 3, 4, 8, 6, 7, 0, 1, 2},
            {6, 7, 8, 1, 2, 0, 4, 5, 3},
            {7, 8, 6, 2, 0, 1, 5, 3, 4},
            {8, 6, 7, 0, 1, 2, 3, 4, 5}
        };
//*/

    public static void main(String[] args) {

        CayleyTable add = new CayleyTable(order, "+");
        initAddTable(add, order);

        CayleyTable multi = new CayleyTable(order);
        initMutiplyTable(multi, order);

        SearchOrder sOrder = new SingleSpecialAbelianSearchOrder(multi);

        List condLst = AssociatableConditionGenerator.generate(multi);
        DistributableConditionGenerator.generate(add, multi, condLst);
        ConditionChecker checker = new ConditionChecker(multi, condLst);

        CayleyTableSearchEngine srchEngine = new CayleyTableSearchEngine(multi, sOrder, checker);

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

        srchEngine.search();

    }

    private static void initAddTable(CayleyTable add, int order) {
        for (int i = 0; i < order; i++)
            for (int j = 0; j < order; j++)
                add.set(i, j, addArray[i][j]);
    }

    private static void initMutiplyTable(CayleyTable multi, int order) {
        for (int i = 0; i < order; i++) {
            multi.set(0, i, 0);
            multi.set(i, 0, 0);
        }
    }

}
