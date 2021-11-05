/*
 *  UCF COP3330 Fall 2021 Assignment 4 Lists Class file
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class taskInfo {

    private final String Description;
    private final String dueDate;

    public String getDescription() {
        return Description;
    }

    public String getDueDate() {
        return dueDate;
    }

    public taskInfo(String description, String dueDate) {
        Description = description;
        this.dueDate = dueDate;
    }
}
