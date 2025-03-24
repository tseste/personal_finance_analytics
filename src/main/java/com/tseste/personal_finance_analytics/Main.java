package com.tseste.personal_finance_analytics;

import com.tseste.personal_finance_analytics.database.DatabaseManager;
import com.tseste.personal_finance_analytics.database.ExpenseImporter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.createTables();
        Button openButton = new Button("Select CSV File");
        Label label = new Label("Waiting for a CSV import");
        openButton.setOnAction(actionEvent -> {
            openFilePicker(primaryStage);
            List<Object> resultList = DatabaseManager.getFirstExpense();
            label.setText("First row: " + resultList.toString());
        });
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(label, openButton);
        Scene scene = new Scene(hBox, 400, 300);

        primaryStage.setTitle("Personal finance analytics");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openFilePicker(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            logger.log(Level.INFO, "Selected file: " + selectedFile.getAbsolutePath());
            ExpenseImporter.importFromCSV(selectedFile.getAbsolutePath());
        } else {
            logger.log(Level.INFO, "No file selected.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
