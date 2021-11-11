/*
 *  UCF COP3330 Fall 2021 Assignment 4 FXMLController Class  file
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        CheckCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().descriptionProperty().toString()));

        //set the table to be editable and allow the description column and dueDate column
        tableView.setEditable(true);
        descriptCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dueDateCol.setCellFactory(TextFieldTableCell.forTableColumn());
        CheckCol.setCellFactory(col -> isChecked());
        tableView.setPlaceholder(new Label("No tasks to display.\nTry adding one below."));

    }

    public void displayAll()
    {
        allButton.setOnAction(e -> tableView.setItems(toDos));
    }

    //this method will allow a user to display only completed tasks
    public void displayComp()
    {
        compButton.setOnAction(e -> tableView.setItems(compItems));
    }

    //this method will allow a user to display only incomplete tasks
    public int displayIncomp()
    {
        incButton.setOnAction(e -> tableView.setItems(incItems));
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
        } else {

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
                Task errand = new Task(descriptionField.getText(), dueDateField.getText());
                toDos.add(errand);

                //refresh the table
                tableView.setItems(toDos);
                dueDateField.clear();
                descriptionField.clear();
                dueDateField.setPromptText("YYYY-MM-DD");

            }
            //change the prompt text to let user know that they inputted an invalid format
            else {
                dueDateField.setPromptText("Not a valid format!");
                dueDateField.clear();
            }

        });

        //return 1 to signify success
        return 1;
    }

    //removes a task from a list
    public int removeTaskButton() {

        //user clicks the remove task button
        remTask.setOnAction(event -> {

            // get the task that user selected
            Task selected = tableView.getSelectionModel().getSelectedItem();
            //remove it from the tableview
            tableView.getItems().remove(selected);
            compItems.remove(selected);
            incItems.remove(selected);
            toDos.remove(selected);

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

    public CheckBoxTableCell<Task, String> isChecked() {

        //if checkbox is checked or not
        BooleanProperty comp = new SimpleBooleanProperty();

        CheckBoxTableCell<Task, String> row = new CheckBoxTableCell<>(index -> comp);

        // update set of selected indices if checkbox state changes:
        comp.addListener((obs, wasSelected, isNowSelected) -> {

            int cell = row.getIndex();
            Task pass = tableView.getItems().get(cell);

            if (isNowSelected && !compItems.contains(pass)) {
                compItems.add(pass);
            }


        });

        /*compItems.addListener((ListChangeListener<? super Task>) (change) -> comp.set(compItems.contains(tableView.getItems().get(row.getIndex())))); */

        //row.itemProperty().addListener((obs, oldItem, newItem) -> comp.set(newItem != null && compItems.contains(newItem)));
        return row;

    }
}