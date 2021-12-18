package com.dk.handsons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * This class is used to validate the Email based on the regex pattern
 * </p>
 *
 * @author Contus
 * @version 1.0
 * @since 12/18/2021
 */
public class EmailValidator {

    /**
     * <p>
     * Starts with Alpha Numeric - [a-z0-9] first character
     * Between 3 to 30- (\.?[a-z0-9]){2,27}
     * Ends with  - gmail.com
     * </p>
     *
     * @param args The String array args
     */
    public static void main(String[] args) {
        String email = "dinesh@gmail.com";
        String regex = "^[a-z0-9](\\.?[a-z0-9]){2,27}@gmail\\.com$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        System.out.println(email + " : " + matcher.matches() + "\n");
    }
}
