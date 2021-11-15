/*
 *  UCF COP3330 Fall 2021 assignment_name Solution
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.*;

public class Actions {

    //removes the selected task
    public static void removeTask(Task select, TableView<Task> tableView, ObservableList<Task> compItems, ObservableList<Task> incItems, ObservableList<Task> toDos) {
        //get the selected task

        //remove from table and all lists
        tableView.getItems().remove(select);
        compItems.remove(select);
        incItems.remove(select);
        toDos.remove(select);
    }

    public static void addTask(TableView<Task> tableView, ObservableList<Task> list, Task errand) {

        //add description field and due date field to observable list
        list.add(errand);

        //refresh the table
        tableView.setItems(list);

    }

    public static void toCSV(TableView<Task> tableView, File file) throws IOException {

        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));
            for (Task e : tableView.getItems()) {
                String text = e.getDescription() + "," + e.getDueDate() + "," + e.isComplete() + "\n";
                writer.write(text);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }

    public static void fromCSV(TableView<Task> tableView, File file, ObservableList<Task> comp, ObservableList<Task> inc, ObservableList<Task> all) throws IOException {
        String splitter = ",", line;
        String descr = "", date = "";
        Boolean done = null;
        String[] reader = null;
        BufferedReader read = new BufferedReader(new FileReader(file));

        //read data from file
        while ((line = read.readLine()) != null) {
            reader = line.split(splitter);
        }

        String[] info = new String[0];
        if (reader != null) {
            info = new String[reader.length];
            System.arraycopy(reader, 0, info, 0, reader.length);
        }

        for (String e : info) {

            if (Validation.isDescrValid(e)) {
                descr = e;
            } else if (e.equals("false") || e.equals("true")) {
                done = !e.matches("false");
            } else {
                date = e;
            }

            //if all the information is filled out
            if (!descr.isEmpty() && !date.isEmpty() && done != null) {
                Task action = new Task(descr, date, done);

                //if the task isn't either list
                if (!comp.contains(action) && !inc.contains(action)) {
                    if (Boolean.TRUE.equals(done)) {
                        comp.add(action);
                    } else {
                        inc.add(action);
                    }
                }
            }
        }

        //add both lists to the all list
        all.addAll(inc);
        all.addAll(comp);

        //clear the current list
        tableView.getItems().clear();
        //display all items in the list
        tableView.setItems(all);
    }

    public static void save(Window saveStage, FileChooser saver, TableView<Task> table) {
        saver.setTitle("Save Dialog");
        saver.setInitialFileName("myToDoList");
        saver.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv"));

        try {
            File file = saver.showSaveDialog(saveStage);
            toCSV(table, file);

            saver.setInitialDirectory(file.getParentFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void open(Window openStage, FileChooser saver, TableView<Task> table, ObservableList<Task> comp, ObservableList<Task> inc, ObservableList<Task> all) {
        saver.setTitle("Load Dialog");
        saver.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv"));

        try {
            File file = saver.showOpenDialog(openStage);
            fromCSV(table, file, comp, inc, all);
            saver.setInitialDirectory(file.getParentFile());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
