/*
 *  UCF COP3330 Fall 2021 Actions Class File
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Actions {

    //remvoes the task from the lists
    public static void removeTask(Task select,ObservableList<Task> compItems, ObservableList<Task> incItems, ObservableList<Task> toDos) {
        compItems.remove(select);
        incItems.remove(select);
        toDos.remove(select);
    }

    //adds tasks to main list
    public static void addTask( ObservableList<Task> list, Task errand) {

        list.add(errand);
    }

    //takes in data from the main list and exports it to a csv file
    public static void toCSV(ObservableList<Task> all, File file) throws IOException {

        Writer writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(file));

            //for each task get its description, due date, and completion status
            for (Task e : all) {
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

    //takes in a csv file and parses the data to the main list
    public static void fromCSV( File file, ObservableList<Task> comp, ObservableList<Task> inc, ObservableList<Task> all) throws IOException {
        String splitter = ",", line;
        String descr = "", date = "";
        Boolean done = null;
        String[] reader = null;
        BufferedReader read = new BufferedReader(new FileReader(file));

        //read data from file
        while ((line = read.readLine()) != null) {
            reader = line.split(splitter);
        }

        //string array to hold info
        String[] info = new String[0];
        if (reader != null) {
            //make info array same size as reader
            info = new String[reader.length];
            //copy the contents of reader to info
            System.arraycopy(reader, 0, info, 0, reader.length);
        }

        //for each string in info
        for (String e : info) {

            //if it is the description
            if (Validation.isDescrValid(e)) {
                descr = e;
                //if it is a boolean value, set it to the correct one
            } else if (e.equals("false") || e.equals("true")) {
                done = !e.matches("false");
                //if its anything else then its the date
            } else {
                date = e;
            }

            //if all the information is filled out
            if (!descr.isEmpty() && !date.isEmpty() && done != null) {

                //create new task
                Task action = new Task(descr, date, done);

                //if the task isn't either list, add it
                if (!comp.contains(action) && !inc.contains(action)) {
                    if (Boolean.TRUE.equals(done)) {
                        comp.add(action);
                    } else {
                        inc.add(action);
                    }
                }
            }
        }

        //add both lists to the main list
        all.addAll(inc);
        all.addAll(comp);
    }

    //saves the file
    public static void save(FileChooser saver, ObservableList<Task> all) {

        //for save dialog window
        saver.setTitle("Save Dialog");
        saver.setInitialFileName("myToDoList");
        //adds the file extension type
        saver.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv"));

        try {
            //open the window and get the file
            File file = saver.showSaveDialog(new Stage());
            toCSV(all, file);

            saver.setInitialDirectory(file.getParentFile());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //opens a file
    public static void open(FileChooser saver, ObservableList<Task> comp, ObservableList<Task> inc, ObservableList<Task> all) {
        //for open dialog
        saver.setTitle("Load Dialog");
        saver.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("csv file", "*.csv"));

        try {
            //open the dialog and get it
            File file = saver.showOpenDialog(new Stage());
            fromCSV(file, comp, inc, all);
            //sets default directory
            saver.setInitialDirectory(file.getParentFile());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
