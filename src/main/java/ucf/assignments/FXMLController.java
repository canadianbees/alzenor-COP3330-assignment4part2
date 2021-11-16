/*
 *  UCF COP3330 Fall 2021 Assignment 4 FXMLController Class  file
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
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

    FileChooser fileChoose = new FileChooser();


    @Override
    //initializes the application
    public void initialize(URL url, ResourceBundle rb) {

        //directory for saving
        System.out.println("before direct");
        fileChoose.setInitialDirectory(new File(Paths.get(".").toAbsolutePath().normalize().toString()));
        System.out.println("after direct");

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
        CheckCol.setCellValueFactory(new PropertyValueFactory<>("select"));

        //set the table to be editable and allow the description column and dueDate column
        tableView.setEditable(true);
        descriptCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        tableView.setPlaceholder(new Label("No tasks to display.\nTry adding one below."));

    }

    public void displayAll(ActionEvent event) {

        System.out.println("displaying all");
        tableView.setItems(toDos);

    }

    //this method will allow a user to display only completed tasks
    public void displayComp(ActionEvent event) {


        System.out.println("displaying comp");
        Validation.getChecked(tableView, compItems, incItems);
        tableView.setItems(compItems);

    }

    //this method will allow a user to display only incomplete tasks
    public void displayIncomp(ActionEvent event) {

        System.out.println("displaying inc");
        Validation.getChecked(tableView, compItems, incItems);
        tableView.setItems(incItems);

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
    public void addTaskButton(ActionEvent event) {

        //create a new task with the string from the textfields
        //since it is a new task, it will be incomplete at first
        Task errand = new Task(descriptionField.getText(), dueDateField.getText(), false);

        //if user inputs a valid date and the description field is not empty or blank
        if (Validation.isDateValid(dueDateField.getText()) && Validation.isDescrValid(descriptionField.getText())) {

            Actions.addTask(toDos,errand);
            //refresh the table
            tableView.setItems(toDos);
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

    }

    //removes a task from a list
    public void removeTaskButton(ActionEvent event) {

        //user selects the task they want removed from the table
        Task select = tableView.getSelectionModel().getSelectedItem();
        //remove from table and all lists
        tableView.getItems().remove(select);
        Actions.removeTask(select,compItems, incItems, toDos);
    }

    //save a single list
    public void saveListButton(ActionEvent event) {
        Actions.save(fileChoose,toDos);
    }

    //load a single list
    public int loadListButton(ActionEvent event) {

        Actions.open(fileChoose,compItems,incItems,toDos);
        //clear the current list
        tableView.getItems().clear();
        //display all items in the list
        tableView.setItems(toDos);
        return 1;
    }

}