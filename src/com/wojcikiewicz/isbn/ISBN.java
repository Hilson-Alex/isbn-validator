package br.univali.kob.poo.lib;

import java.io.Serializable;
import java.util.Objects;

public class ISBN implements Serializable {

    private final String value;

    public ISBN (String value){
        validISBN(value);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getNumeric(){
        return numericIsbn(value);
    }

    private String numericIsbn (String isbn){
        String numeric = "";
        for (char ch : isbn.toCharArray()){
            if (Character.isDigit(ch)){
                numeric = numeric + ch;
            }
        }
        numeric = (isbn.charAt(isbn.length()-1) == 'x') ? numeric + "x" : numeric;
        return numeric;
    }

    private void validISBN (String isbn){
        if(!validNumericISBN(isbn)){
            throw new IllegalArgumentException("NOT A VALID isbn");
        }
    }

    private boolean validNumericISBN (String isbn){
        String numeric = numericIsbn(isbn);
        if(numeric.length() != 10 && numeric.length() != 13){
            return false;
        }
        if(!validCheckDigit(isbn)){
            return false;
        }
        return true;
    }

    private boolean validCheckDigit (String isbn){
        int[] numbers = new int[12];
        int calcDigit = 0;
        char lastDigit = isbn.charAt(isbn.length()-1);
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
        return (calcDigit == 10) ? lastDigit == 'x' : calcDigit == Character.getNumericValue(lastDigit);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ISBN isbn = (ISBN) o;
        return Objects.equals(getNumeric(), isbn.getNumeric());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeric());
    }
}
