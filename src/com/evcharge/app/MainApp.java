package com.evcharge.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApp extends Application {

    private BorderPane root;
    private VBox content;

    @Override
    public void start(Stage stage) {

        root = new BorderPane();
        root.setStyle("-fx-background-color: #F3F6FA;");

        // HEADER
        Label title = new Label("EV CHARGING MANAGEMENT SYSTEM");
        title.setFont(Font.font("Arial", 24));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Smart Charging | Simple Payments");
        subtitle.setTextFill(Color.LIGHTBLUE);

        VBox header = new VBox(6, title, subtitle);
        header.setPadding(new Insets(22));
        header.setStyle("-fx-background-color: #102A43;");

        root.setTop(header);

        // SIDEBAR
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #173B57;");

        String[] menus = {
            "Dashboard",
            "Drivers & Vehicles",
            "Charging Stations",
            "Bookings",
            "Charging Sessions",
            "Payments",
            "Reviews",
            "Database"
        };

        for (String menu : menus) {

            Button button = new Button(menu);

            button.setMaxWidth(Double.MAX_VALUE);
            button.setAlignment(Pos.CENTER_LEFT);
            button.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-text-fill: white;" +
                "-fx-font-size: 14px;" +
                "-fx-padding: 12px;"
            );

            button.setOnAction(event -> showPage(menu));

            sidebar.getChildren().add(button);
        }

        root.setLeft(sidebar);

        // MAIN CONTENT
        content = new VBox(20);
        content.setPadding(new Insets(30));

        root.setCenter(content);

        showPage("Dashboard");

        Scene scene = new Scene(root, 1150, 750);

        stage.setTitle("EV Charging Management System");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private void showPage(String page) {

        content.getChildren().clear();

        Label heading = new Label(page);
        heading.setFont(Font.font("Arial", 26));
        heading.setTextFill(Color.web("#102A43"));

        content.getChildren().add(heading);

        if (page.equals("Dashboard")) {
            showDashboard();
        } else {
            Label message = new Label(
                page + " screen will be connected to the backend next."
            );

            message.setFont(Font.font("Arial", 16));
            message.setWrapText(true);

            content.getChildren().add(message);
        }
    }

    private void showDashboard() {

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        String[] headings = {
            "Total Drivers",
            "Charging Stations",
            "Active Bookings",
            "Completed Sessions",
            "Payments",
            "Customer Reviews"
        };

        String[] values = {
            "120",
            "35",
            "18",
            "250",
            "₹45,000",
            "4.8 / 5"
        };

        for (int i = 0; i < headings.length; i++) {

            Label heading = new Label(headings[i]);
            heading.setFont(Font.font("Arial", 15));
            heading.setTextFill(Color.web("#526579"));

            Label value = new Label(values[i]);
            value.setFont(Font.font("Arial", 27));
            value.setTextFill(Color.web("#087F8C"));

            VBox card = new VBox(15, heading, value);

            card.setPadding(new Insets(22));
            card.setPrefSize(250, 140);

            card.setStyle(
                "-fx-background-color: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #DCE5EE;" +
                "-fx-border-radius: 12;"
            );

            grid.add(card, i % 2, i / 2);
        }

        content.getChildren().add(grid);

        Label info = new Label(
            "Welcome! Select a module from the sidebar."
        );

        info.setFont(Font.font("Arial", 15));
        info.setTextFill(Color.web("#526579"));

        content.getChildren().add(info);
    }

    public static void main(String[] args) {
        launch(args);
    }
}