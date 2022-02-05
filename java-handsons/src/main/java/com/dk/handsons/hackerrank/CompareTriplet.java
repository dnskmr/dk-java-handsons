package com.dk.handsons.hackerrank;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;


/**
 * @author Dinesh
 * @since 02/05/2022
 * @version 1.0
 *
 */
public class CompareTriplet {

    static Map<String, Integer> valueMap = new HashMap<>();

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> aliceList = Arrays.asList(1,3,4);
        List<Integer> bobList = Arrays.asList(2,3,5);
        List<Integer> resultList = compareTriplets(aliceList,bobList);
        System.out.println(resultList);
    }

    /*
     * Complete the 'compareTriplets' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER_ARRAY a
     *  2. INTEGER_ARRAY b
     */

    public static List<Integer> compareTriplets(List<Integer> a, List<Integer> b) {
        List<Integer> resultList = new ArrayList<>();
        // Write your code here
        if (a.size() == b.size()) {
            for (int i = 0; i < a.size(); i++) {
                compareValue(a.get(i), b.get(i));
            }
            resultList.add(valueMap.containsKey("alice")?valueMap.get("alice"):0);
            resultList.add(valueMap.containsKey("bob")?valueMap.get("bob"):0);
            return resultList;
        }
        return resultList;
    }

    public static void compareValue(int alice, int bob) {
        if (alice > bob) {
            if (!valueMap.containsKey("alice")) {
                valueMap.put("alice", 1);
            } else {
                valueMap.put("alice", valueMap.get("alice") + 1);
            }
        }
        if (alice < bob) {
            if (!valueMap.containsKey("bob")) {
                valueMap.put("bob", 1);
            } else {
                valueMap.put("bob", valueMap.get("bob") + 1);
            }
        }

    }

}
