package de.javafx.study.intro;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImproveHelloFXApp extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label nameLbl = new Label("Enter your name:");
        TextField nameFld = new TextField();

        Label msg = new Label();
        msg.setStyle("-fx-text-fill: blue;");

        //create buttons
        Button sayHelloBtn = new Button("Say Hello");
        Button exitBtn = new Button("Exit");

        //add then event handler for say Hello button
        sayHelloBtn.setOnAction( e -> {
            String name = nameFld.getText();
            if(name.trim().length()  > 0) {
                msg.setText("Hello " + name);
            } else {
                msg.setText("Hello there!!!");
            }
        });

        //add the event handler for Exit button
        exitBtn.setOnAction(e -> Platform.exit());

        //create the root node
        VBox root = new VBox();

        //set the vertical spacing between children
        root.setSpacing(5);

        //Add children to root node

        root.getChildren().addAll(nameLbl, nameFld, msg, sayHelloBtn, exitBtn);

        Scene scene = new Scene(root, 350, 150);
        stage.setScene(scene);
        stage.setTitle("Improved Hello JavaFX Application");
        stage.show();
    }
}