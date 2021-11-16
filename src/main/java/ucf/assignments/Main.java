/*
 *  UCF COP3330 Fall 2021 Assignment 4 Solution
 *  Copyright 2021 Celina Alzenor
 */


package ucf.assignments;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws IOException {

        window.setTitle("List Manager");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainStage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }



}
