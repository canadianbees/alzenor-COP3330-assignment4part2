/*
 *  UCF COP3330 Fall 2021 Assignment 4 Task Class file
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class Task {

    private SimpleStringProperty description;
    private SimpleStringProperty dueDate;
    private boolean complete;
    private CheckBox selected;

    public Task(String description, String dueDate, Boolean complete) {
        this.description = new SimpleStringProperty(description);
        this.dueDate = new SimpleStringProperty(dueDate);
        this.selected = new CheckBox();
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public SimpleStringProperty dueDateProperty() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = new SimpleStringProperty(dueDate);
    }

    public String getDueDate() {
        return dueDate.get();
    }

    public CheckBox getSelected() {
        return selected;
    }

    public void setSelected(CheckBox selected) {
        this.selected = selected;
    }

}
