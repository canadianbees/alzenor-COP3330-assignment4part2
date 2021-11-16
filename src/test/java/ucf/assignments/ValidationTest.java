/*
 *  UCF COP3330 Fall 2021 assignment_name Solution
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ValidationTest {

    @Test
    void validateDateTrue()
    {
        assertTrue(Validation.isDateValid("2020-05-07"),"May 7th, 2020");
        assertTrue(Validation.isDateValid("2021-11-15"),"November 15th, 2021");
        assertTrue(Validation.isDateValid("1996-08-27"),"August 27th, 2020");
        assertTrue(Validation.isDateValid("1549-12-19"),"December 19th, 1549");
        assertTrue(Validation.isDateValid("3031-01-06"),"January 6th, 3031");

    }

    @Test
    void validateDateFalse()
    {
        assertFalse(Validation.isDateValid("2020-11-31"),"November doesn't have 31 days");
        assertFalse(Validation.isDateValid("asdfsasdfafssd"),"all letters");
        assertFalse(Validation.isDateValid("19XCxzc96-0zxc8-2998887"),"mix of letters in numbers with hypens");
        assertFalse(Validation.isDateValid("90-1565-19"),"date is only right");
        assertFalse(Validation.isDateValid("01-01-06"),"year is invalid");

    }






}
