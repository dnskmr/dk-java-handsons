package com.dk.handsons;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Dinesh
 * @version 1.0
 * @since 02/12/2021
 * <p>
 * This class is used top demonstrate how to find the character occurrence
 */
public class CharacterOccurrences {

    /**
     * @param args
     */
    public static void main(String[] args) {
        String value = "Dineshkumar";
        Map<Character, Integer> charMap = getCharMap(value);
        iterateMap(charMap);
    }

    /**
     * @param charMap
     */
    private static void iterateMap(Map<Character, Integer> charMap) {
        for (Map.Entry<Character, Integer> map : charMap.entrySet()) {
            System.out.println("Character : " + map.getKey() + " Occurrence is :" + map.getValue());
        }
    }

    /**
     * <p>
     * This method is used to provide the character map from the String
     * </p>
     *
     * @param value
     * @return the map object
     */
    private static Map<Character, Integer> getCharMap(String value) {
        Map<Character, Integer> charMap = new HashMap<>();
        char[] chars = value.toCharArray();
        for (Character c : chars) {
            if (charMap.containsKey(c)) {
                charMap.put(c, charMap.get(c) + 1);
            } else {
                charMap.put(c, 1);
            }
        }
        return charMap;
    }
}
