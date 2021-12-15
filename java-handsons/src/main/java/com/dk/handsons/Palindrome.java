package com.dk.handsons;

/**
 * @author Dinesh
 * @version 1.0
 * @since 12/02/2021
 * <p>
 * This class is used to demonstrate how to perform palindrome for a number
 */
public class Palindrome {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int result = getPalindrome(0);
        System.out.println("result = " + result);
    }

    /**
     * @param num
     * @return the result
     */
    private static int getPalindrome(int num) {
        for (int i = num - 1; i > 0; i--) {
            num = num * i;
        }
        return num;
    }
}
