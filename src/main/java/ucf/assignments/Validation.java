/*
 *  UCF COP3330 Fall 2021 assignment_name Solution
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Validation {

    public static void getChecked(TableView<Task> list, ObservableList<Task> compItems, ObservableList<Task> incItems) {
        for (Task e : list.getItems()) {

            e.getSelected().selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {

                System.out.println(e.getDescription());
                System.out.println("Current state: "+e.isComplete());
                System.out.println("Before check: "+old_val);
                System.out.println("After check: "+new_val);
                System.out.println("\n\n");
                //if the task is marked completed
                if (new_val && !e.isComplete()) {
                    e.setComplete(true);
                    compItems.add(e);
                    incItems.remove(e);
                }

                //task was complete but it was unchecked
                if (!new_val && e.isComplete()) {
                    e.setComplete(false);
                    compItems.remove(e);
                    incItems.add(e);
                }
            });

        }
    }

    //checks if user inputted a valid date in the correct format
    public static boolean isDateValid(String date) {
        //if date is null or does not have valid range of months or days
        if (date == null || !date.matches("\\d{4}-[01]\\d-[0-3]\\d")) {

            return false;
        }

        //initialize simpledateformat for yyyy-mm-dd format
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);

        //if date inputted matches df
        try {
            df.parse(date);
            return true;

            //catch exceptions
        } catch (ParseException ex) {
            return false;
        }

    }

    public static boolean isDescrValid(String df) {
        //returns true if the string is between 1 and 256 characters
        return !df.isEmpty() && !df.isBlank() && df.length() <= 256;

    }

}
