/*
 *  UCF COP3330 Fall 2021 Assignment 4 FXMLController Class  file
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {

    //list related
    public MenuItem saveList;
    public MenuItem loadList;

    //task related
    public Button newTask;
    public TextField descriptionField;
    public TextField dueDateField;
    public MenuItem remTask;
    public RadioButton allButton;
    public RadioButton incButton;
    public RadioButton compButton;

    // table related
    public ObservableList<Task> toDos = FXCollections.observableArrayList();
    public ObservableList<Task> compItems = FXCollections.observableArrayList();
    public ObservableList<Task> incItems = FXCollections.observableArrayList();

    public TableView<Task> tableView;
    public TableColumn<Task, String> descriptCol;
    public TableColumn<Task, String> dueDateCol;
    public TableColumn<Task, String> CheckCol;

    @Override
    //initializes the application
    public void initialize(URL url, ResourceBundle rb) {

        //groups the radio buttons together so only one can be selected at a time
        final ToggleGroup group = new ToggleGroup();
        allButton.setToggleGroup(group);
        incButton.setToggleGroup(group);
        compButton.setToggleGroup(group);

        //selects display all by default
        allButton.setSelected(true);

        //create the columns
        descriptCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        CheckCol.setCellValueFactory(new PropertyValueFactory<>("selected"));

        //set the table to be editable and allow the description column and dueDate column
        tableView.setEditable(true);
        descriptCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        //CheckCol.setCellFactory(col -> Actions.isChecked(tableView, compItems));
        tableView.setPlaceholder(new Label("No tasks to display.\nTry adding one below."));

    }

    public void displayAll() {

        allButton.setOnAction(e -> {
            System.out.println("displaying all");
            tableView.setItems(toDos);
        });
    }

    //this method will allow a user to display only completed tasks
    public void displayComp() {

        compButton.setOnAction(e -> {
            System.out.println("displaying comp");
            Validation.getChecked(tableView, compItems, incItems);
            tableView.setItems(compItems);
        });
    }

    //this method will allow a user to display only incomplete tasks
    public void displayIncomp() {

        incButton.setOnAction(e -> {
            System.out.println("displaying inc");
            Validation.getChecked(tableView, compItems, incItems);
            tableView.setItems(incItems);
        });
    }

    //this method will allow the user to change the description by double-clicking on the cell
    public void changeDescript(TableColumn.CellEditEvent change) {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        selected.setDescription(change.getNewValue().toString());
    }

    //this method will allow the user to change the due date by double-clicking on the cell
    public int changeDate(TableColumn.CellEditEvent change) {
        Task selected = tableView.getSelectionModel().getSelectedItem();

        String oldDate = selected.getDescription();
        String newDate = change.getNewValue().toString();

        //if user is changing date to a valid date
        if (Validation.isDateValid(newDate)) {
            tableView.setEditable(true);

            selected.setDueDate(newDate);
            return 1;
        } else {

            selected.setDueDate(oldDate);

            return 0;
        }
    }

    //adds a task to a list
    public void addTaskButton() {

        //user clicks add task button
        newTask.setOnAction(event -> {

            //create a new task with the string from the textfields
            //since it is a new task, it will be incomplete at first
            Task errand = new Task(descriptionField.getText(), dueDateField.getText(), false);

            //if user inputs a valid date and the description field is not empty or blank
            if (Validation.isDateValid(dueDateField.getText()) && Validation.isDescrValid(descriptionField.getText())) {

                Actions.addTask(tableView, toDos, errand);
                incItems.add(errand);
                dueDateField.clear();
                descriptionField.clear();
                dueDateField.setPromptText("YYYY-MM-DD");
                descriptionField.setPromptText("What are you doing?");
            }

            //change the prompt text to let user know that they inputted an invalid format
            else if (Validation.isDescrValid(descriptionField.getText()) && !Validation.isDateValid(dueDateField.getText())) {
                dueDateField.setPromptText("Not a valid format!");
                dueDateField.clear();
            } else {
                dueDateField.setPromptText("Not a valid format!");
                dueDateField.clear();
                descriptionField.setPromptText("Too short!");
                descriptionField.clear();
            }

        });

    }

    //removes a task from a list
    public int removeTaskButton() {

        //user clicks the remove task button
        remTask.setOnAction(event -> Actions.removeTask(tableView, compItems, incItems, toDos));

        //return 1 to signify success
        return 1;
    }

    //save a single list
    public void saveListButton() {

        //saveList.setOnAction(event -> Actions.toCSV(tableView, ) );

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

}