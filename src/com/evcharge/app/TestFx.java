package com.evcharge.app;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

public class TestFx {
    public static void main(String[] args) {
        System.out.println("Starting JavaFX Test...");
        JFXPanel panel = new JFXPanel();
        Platform.runLater(() -> {
            try {
                WebView view = new WebView();
                Scene scene = new Scene(view);
                panel.setScene(scene);
                System.out.println("JavaFX WebView initialized successfully!");
                Platform.exit();
                System.exit(0);
            } catch (Throwable t) {
                t.printStackTrace();
                System.exit(1);
            }
        });
    }
}
