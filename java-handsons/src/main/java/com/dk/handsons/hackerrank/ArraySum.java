package com.dk.handsons.hackerrank;

import java.util.Arrays;
import java.util.List;

/**
 * @author Dinesh
 * @version 1.0
 * @since 02/05/2022
 */
public class ArraySum {

    /**
     * @param inputList
     * @return
     */
    public static int sumArray(List<Integer> inputList) {
        int result = 0;
        for (Integer input : inputList) {
            result = result + input;
        }
        return result;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<Integer> inputList = Arrays.asList(2, 4, 5, 6, 7, 8, 90);
        int result = sumArray(inputList);
        System.out.println("result = " + result);
    }

}
