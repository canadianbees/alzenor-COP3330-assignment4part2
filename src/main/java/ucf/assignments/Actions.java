/*
 *  UCF COP3330 Fall 2021 assignment_name Solution
 *  Copyright 2021 Celina Alzenor
 */

package ucf.assignments;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.io.*;

public class Actions {

    public static void removeTask(TableView<Task> tableView, ObservableList<Task> compItems, ObservableList<Task> incItems, ObservableList<Task> toDos) {
        Task select = tableView.getSelectionModel().getSelectedItem();
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


    public static void toCSV(TableView<Task> tableView,String fileName) throws IOException {

        Writer writer = null;
        try {
            File file = new File("ucf/assignments/"+fileName+".csv.");
            writer = new BufferedWriter(new FileWriter(file));
            for (Task e : tableView.getItems()) {

                String text = e.getDescription() + "," + e.getDueDate() + "," + e.getSelected() + "\n";

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

}
