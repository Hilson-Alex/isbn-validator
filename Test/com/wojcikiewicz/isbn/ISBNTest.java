package com.wojcikiewicz.isbn;

import org.junit.Test;
import org.junit.Assert;
import org.junit.function.ThrowingRunnable;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ISBNTest {

    @Test
    public void testTheISBNFilter (){
        String[] isbnTest = {"978-85.945 4033a1", "85-220-0523-0", "0-8044-2957-x"};
        Arrays.stream(isbnTest).map(ISBN::toNumeric).collect(Collectors.toList()).toArray(isbnTest);
        String[] expected =  {"9788594540331", "8522005230", "080442957X"};
        Assert.assertArrayEquals(expected, isbnTest);
    }

    @Test
    public void testIfISBNValidationIsWorking (){
        String[] isbnTest = {"978-85.945 4033a1", "123456789 0123", "85-220-0523-0", "0-8044-2957-x", "1234567890", "123"};
        Boolean[] results = new Boolean[isbnTest.length];
        Arrays.stream(isbnTest).map(ISBN::isValid).collect(Collectors.toList()).toArray(results);
        Boolean[] expected =  {true, false, true, true, false, false};
        Assert.assertArrayEquals(expected, results);
    }

    @Test
    public void testValidateError (){
        ThrowingRunnable[] throwers = {
                () -> ISBN.validate("123456789 0123",   "Invalid ISBN!"),
                () -> ISBN.validate("1234567890",       "Invalid ISBN!"),
        };
        for (int i = 0; i < throwers.length; i++) {
            Assert.assertThrows(IllegalArgumentException.class, throwers[i]);
        }
    }

    @Test
    public void testValidatePasser (){
        String[] isbnTest = {"978-85.945 4033a1", "85-220-0523-0", "0-8044-2957-x"};
        try{
            for (int i = 0; i < isbnTest.length; i++){
                ISBN.validate(isbnTest[i], "Invalid ISBN");
            }
        } catch (IllegalArgumentException e){
            Assert.fail();
        }
    }

}
