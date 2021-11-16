/*
 *  UCF COP3330 Fall 2021 Assignment 4 ActionsTest Class  file
 *  Copyright 2021 Celina Alzenor
 *//*




package ucf.assignments;

import com.sun.javafx.fxml.builder.JavaFXSceneBuilder;
import de.saxsys.javafx.test.JfxRunner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JfxRunner.class)
public class ActionsTest {
    private JavaFXSceneBuilder panel = new JavaFXSceneBuilder();

    ObservableList<Task> incomplete = FXCollections.observableArrayList();
    ObservableList<Task> complete = FXCollections.observableArrayList();
    ObservableList<Task> all = FXCollections.observableArrayList();



    public void add()
    {
        incomplete.add(new Task("Wash car","2022-05-01",false));
        incomplete.add(new Task("Go grocery shopping","2022-05-02",false));
        incomplete.add(new Task("Drop off cookies at Anna's","2022-05-07",false));

        complete.add(new Task("Walk dog","2022-05-01",true));

        all.addAll(incomplete);
        all.addAll(complete);

    }

    @Test
    void addTask() {

        Task test = new Task("Pick up Nathan","2022-05-08",false);
        Task test1 = new Task("Buy flowers","2022-05-05",true);
        Task test2 = new Task("Pick up Nathan","2022-05-08",false);

        Actions.addTask(all,test);
        assertTrue(all.contains(test));

        Actions.addTask(all,test1);
        assertTrue(all.contains(test1));

        Actions.addTask(all,test2);
        assertTrue(all.contains(test));
    }


    @Test
    void removeTask() {

        add();
        Task delete = all.get(0);
        Actions.removeTask(delete,complete,incomplete,all);

        assertFalse(complete.contains(delete));
        assertFalse(incomplete.contains(delete));
        assertFalse(all.contains(delete));

    }

    @Test
    void saveList() {

        //assertTrue(Actions.save(fileChoose,tableView).mkdirs());
    }

    @Test
    void loadList() {

    }

    @Test
    void displayComp() {

        //get the displayComp method from the FXMLController class
        //use assert equals to check that the method worked.
    }

    @Test
    void displayIncomp() {


        //get the displayComp method from the FXMLController class
        //use assert equals to check that the method worked.
    }

}



*/
