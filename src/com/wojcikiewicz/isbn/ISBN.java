package com.wojcikiewicz.isbn;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Class to handle and validate ISBN that Works both to ISBN 10
 * and ISBN 13. Also, it don't requires a pre-formatted input.
 * the code only sees the numeral part (and a x final).
 *
 * @author Hilson Alexandre Wojcikiewicz Junior
 */
public class ISBN implements Serializable {

    /**
     * Private constructor.
     */
    private ISBN (){}

    /**
     * Filter the number part of the input (ignores '.', '/', ' '
     * and any other symbol or letter).
     *
     * Obs: 'x' can be taken as a "number" in the ISBN-10 if it is
     * the final digit. The code also considers it.
     *
     * @param isbn a string ISBN to be filtered.
     * @return a String with just the numbers (and x in some cases).
     */
    public static String toNumeric (String isbn){
        String numeric = "";
        for (char ch : isbn.toCharArray()){
            if (Character.isDigit(ch)){
                numeric = numeric + ch;
            }
        }
        numeric = (Character.toUpperCase(isbn.charAt(isbn.length()-1)) == 'X') ? numeric + "X" : numeric;
        return numeric;
    }

    /**
     * Throw an error if the ISBN is not valid.
     *
     * @param isbn the ISBN to be verified. (don't need to be formatted).
     */
    public static void validate(String isbn, String message){
        if(!isValid(isbn)){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Verifies if an isbn is valid.
     *
     * @param isbn the ISBN to be verified. (don't need to be formatted).
     * @return True if it's a valid ISBN
     */
    public static boolean isValid (String isbn){
        String numeric = toNumeric(isbn);
        if(numeric.length() != 10 && numeric.length() != 13){
            return false;
        }
        return validCheckDigit(isbn);
    }

    /**
     * Verifies the ISBN check digit.
     * @param isbn the ISBN to be verified
     * @return true if the check digit is valid
     */
    private static boolean validCheckDigit (String isbn){
        isbn = toNumeric(isbn);
        int calcDigit = calculateDigit(isbn);
        char lastDigit = isbn.charAt(isbn.length()-1);
        return (calcDigit == 10) ? lastDigit == 'X' : calcDigit == Character.getNumericValue(lastDigit);
    }

    /**
     * calculate the check digit.
     * @param isbn the ISBN to calculate.
     * @return the expected check digit.
     */
    private static int calculateDigit (String isbn){
        int[] numbers = new int[12];
        int calcDigit = 0;
        for (int i = 0; i < isbn.length()-1; i++){
            numbers[i] = Character.getNumericValue(isbn.charAt(i));
        }
        if(isbn.length() == 10){
            for (int i = 1; i < 10; i++){
                calcDigit += numbers[i-1] * i;
            }
            calcDigit = calcDigit % 11;
        } else {
            for (int i = 0; i < 12; i++){
                calcDigit += (i % 2 == 0) ? numbers[i] : numbers[i]*3;
            }
            calcDigit = 10 - (calcDigit % 10);
        }
        return calcDigit;
    }
}
