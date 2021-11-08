/*
 *  UCF COP3330 Fall 2021 Assignment 4 FXMLController Class  file
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class FXMLController implements Initializable {


    //list related
    public MenuItem saveList;
    public MenuItem loadList;


    //task related
    public Button newTask;
    public TextField descriptionField;
    public TextField dueDateField;
    public MenuItem remTask;
    public RadioButton displayAll;
    public RadioButton displayInc;
    public RadioButton displayComp;


    // table related
    public ObservableList<Task> toDos = FXCollections.observableArrayList();
    public TableView<Task> tableView;
    public TableColumn<Task, String> descriptCol;
    public TableColumn<Task, String> dueDateCol;
    public TableColumn<Task, String> CheckCol;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //groups the radio buttons together so only one can be selected at a time
        final ToggleGroup group = new ToggleGroup();
        displayAll.setToggleGroup(group);
        displayInc.setToggleGroup(group);
        displayComp.setToggleGroup(group);

        //selects display all by default
        displayAll.setSelected(true);

        descriptCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        CheckCol.setCellValueFactory(new PropertyValueFactory<>("completed"));

        tableView.setEditable(true);
        descriptCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDateCol.setCellFactory(TextFieldTableCell.forTableColumn());

    }

    //this method will allow a user to display only completed tasks
    public int displayComp() {
       /*
       initialize a fitleredList and display all data
       set the filter predicate when the filter changes
       if no boxes are checked, display none
       compare the row's completion status with a checked check-box
       if it is a match return true to the predicate
       if it doesn't match return false to the predicate
       put the filtered list in a sorted list
       user the comparator to put the sorted list in the tableview
       add the sorted list to the tableview
       */

        return 1;

    }

    //this method will allow a user to display only incomplete tasks
    public int displayIncomp() {
        /*
       initialize a fitleredList and display all data
       set the filter predicate when the filter changes
       if all boxes are checked, display none
       compare the row's completion status with an unchecked check-box
       if it is a match return true to the predicate
       if it doesn't match return false to the predicate
       put the filtered list in a sorted list
       user the comparator to put the sorted list in the tableview
       add the sorted list to the tableview

        return 1 to signify success

       */

        return 1;
    }


    //this method will allow the user to change the description by double-clicking on the cell
    public int changeDescript(TableColumn.CellEditEvent change) {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        selected.setDescription(change.getNewValue().toString());

        return 1;

    }

    //this method will allow the user to change the due date by double-clicking on the cell
    public int changeDate(TableColumn.CellEditEvent change) {
        Task selected = tableView.getSelectionModel().getSelectedItem();

        //if user is changing date to a valid date
        if (isValid(change.getNewValue().toString())) {
            tableView.setEditable(true);

            selected.setDueDate(change.getNewValue().toString());
            return 1;
        }
        else {

            return 0;
        }


    }

    //adds a task to a list
    public int addTaskButton() {
        //user clicks add task button
        newTask.setOnAction(event -> {

            //if user inputs a valid date and the description field is not empty or blank
            if (isValid(dueDateField.getText()) && !descriptionField.getText().isEmpty()
                    || !descriptionField.getText().isBlank() && isValid(dueDateField.getText())) {

                //add description field and due date field to observable list
                toDos.add(new Task(descriptionField.getText(), dueDateField.getText()));
                //refresh the table
                tableView.setItems(toDos);
            }
            //change the prompt text to let user know that they inputted an invalid format
            else {
                dueDateField.setPromptText("Not a valid format!");
            }
        });

        //return 1 to signify success
        return 1;

    }

    //removes a task from a list
    public int removeTaskButton() {

        remTask.setOnAction(event-> {

            // get the task that user selected
            Task selected = tableView.getSelectionModel().getSelectedItem();

            //remove it from the tableview
            tableView.getItems().remove(selected);
        });

        //return 1 to signify success
        return 1;

    }

    //save a single list
    public int saveListButton() {
        //user right clicks on listView and selects save list
        //saveList is triggered
        //open save dialog from desktop
        //save selected cell
        //return 1 to signify success
        return 1;


    }

    //load a single list
    public int loadListButton() {
        //user right clicks on listView and selects save list
        //loadList is triggered
        //open open-dialog from desktop
        //open selected file
        //return 1 to signify success
        return 1;

    }

    //save all the lists in the list manager
    public int saveAllButton() {
        //user clicks on menubar and selects file
        //user selects save all
        //saveALl is triggered
        //select all cells in listView
        //open save-dialog from desktop
        //save all cells
        //return 1 to signify success
        return 1;

    }

    //load mulitple lists from file explorer
    public int loadMultiButton() {
        //user clicks on menubar and selects file
        //user selects load multiple lists
        //loadALl is triggered
        //open open-dialog from desktop
        //allow user to select multiple lists from file explorer
        //open all lists
        //return 1 to signify success
        return 1;

    }

    //checks if user inputted a valid date in the correct format
    public static boolean isValid(String date) {
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

}
