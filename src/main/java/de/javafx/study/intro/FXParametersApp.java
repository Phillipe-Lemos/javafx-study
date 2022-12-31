package de.javafx.study.intro;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class FXParametersApp extends Application {

    private static final String NEW_LINE = "\n";

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Get application paramenters
        Parameters parameters = this.getParameters();
        Map<String, String> namedParameters = parameters.getNamed();
        List<String> unnamedParameters = parameters.getUnnamed();
        List<String> rawParameters = parameters.getRaw();

        final StringBuilder sb = new StringBuilder("Named parameters " + namedParameters);
        sb.append(NEW_LINE);
        sb.append("Unnamed parameters : " + unnamedParameters);
        sb.append(NEW_LINE);
        sb.append("Raw parameters : " + rawParameters);


        final TextArea ta = new TextArea(sb.toString());
        Group root = new Group(ta);
        stage.setScene(new Scene(root));
        stage.setTitle("Application parameters");
        stage.show();
    }
}
