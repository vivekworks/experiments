package org.openjfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application{
    public void start(Stage primaryStage) throws Exception{
        Button clickButton = new Button("Click me!");
        StackPane layout = new StackPane();
        layout.getChildren().add(clickButton);
        Scene newScene = new Scene(layout,500,500);
        primaryStage.setTitle("Click Me!");
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
