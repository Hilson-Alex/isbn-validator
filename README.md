# ISBN

A Java class to handle and validate ISBN that works both to ISBN-10 and ISBN-13. 
Also, it don't requires a pre-formatted input, since the code only sees the numeral part (and a x final in ISBN-13 case).

## Getting Started

As a static class, the only thing you need to run is importing it. ISBN does not use any dependecies you need to install, just plain Java.

### Installing

To use it you can [download the Jar](/out/artifacts/isbn_jar/isbn.jar) And include as an library in your project or [copy the class](/src/com/wojcikiewicz/isbn/ISBN.java).

Note that ISBN class have a private constructor, so you can't instantiate it. All methods are static, so:

> **Use like this:**
> ```
> ISBN.isValid(isbnCode);
> ```
> **Not like this:**
> ```
> new ISBN().isValid(isbnCode);
> ```
> **Nor like this:**
> ```
> ISBN isbnValidator = new ISBN();
> isbnValidator.isValid(isbnCode);
> ```

## Tests

You can see my tests [here](/Test/com/wojcikiewicz/isbn/ISBNTest.java), but to run it you will need [these jars](/lib).
Cloning the project, you can run the tests running the [test file](/Test/com/wojcikiewicz/isbn/ISBNTest.java).

----

The first test:
```
@Test
public void testTheISBNFilter (){
    String[] isbnTest = {"978-85.945 4033a1", "85-220-0523-0", "0-8044-2957-x"};
    Arrays.stream(isbnTest).map(ISBN::toNumeric).collect(Collectors.toList()).toArray(isbnTest);
    String[] expected =  {"9788594540331", "8522005230", "080442957X"};
    Assert.assertArrayEquals(expected, isbnTest);
}
```
Check if the public method **ISBN.*toNumeric*(isbn)** is filtering all invalid characters
> 'X' is not an invalid character if it's in the end of the code. You might try to put it in another place to see if it's filterd.

----

Next, we have: 
```
@Test
public void testIfISBNValidationIsWorking (){
    String[] isbnTest = {"978-85.945 4033a1", "123456789 0123", "85-220-0523-0", "0-8044-2957-x",
                         "1234567890", "123"};
    Boolean[] results = new Boolean[isbnTest.length];
    Arrays.stream(isbnTest).map(ISBN::isValid).collect(Collectors.toList()).toArray(results);
    Boolean[] expected =  {true, false, true, true, false, false};
    Assert.assertArrayEquals(expected, results);
}
```
That checks if the public method **ISBN.*isValid*(isbn)** is returning the right values to an array of ISBNs (some are valid and some not). As an boolean function, it doesn't throw an error, we have another function to do that.

----

The last two tests:
```
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
```
Check the **ISBN.*validate*(isbn)** that throws an error when an invalid ISBN is passed. The first test verifies if the two wrong ISBNs are trowing the desired error, and the second one if the correct ISBN are passing.

## Author
This validator was developed by [@Wojcikiewicz](https://github.com/Wojcikiewicz).
