package com.richmondstudio.galleryslideshow;

import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.prefs.*;

import java.io.*;

public class Main extends Application {
    public static File WORKING_DIRECTORY = new File(System.getProperty("user.dir"));
    //public static int INTERVAL = 3; //time between photos
    public static int ITERATOR = 0;

    public static String INTERVAL = "interval";
    public static int interval;
    public static String RANDOMIZE = "randomize";

    Timeline switchPhotoTrigger;
    ImageView imageView;
    public static String[] imagePaths;
    Preferences prefs;

    @Override
    public void start(Stage stage) {
        prefs = Preferences.userNodeForPackage(Main.class);
        imagePaths = SlideShowHelper.getImagePathsFromWorkingDirectory(WORKING_DIRECTORY);
        if (imagePaths.length == 0) {
            JOptionPane.showMessageDialog(null, "No Images Found");
            System.exit(0);
        }
        if (prefs.getBoolean(RANDOMIZE, false)) {
            shuffleArray(imagePaths);
        }
        interval = prefs.getInt(INTERVAL, 5);
        //https://www.vogella.com/tutorials/JavaPreferences/article.html
        // prefs.put to set value


        stage.setFullScreen(true);
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        imageView = SlideShowHelper.setImageViewPref(screenBounds);
        SlideShowHelper.setImageView(imageView);

        BorderPane pane = new BorderPane();

        pane.setCenter(imageView);
        pane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(pane);
        scene.setFill(Color.BLACK);
        switchPhotoTrigger = ApplicationHelper.initiza(
                stage, pane,SlideShowHelper.createSlideShowTimer(interval, imageView));

        stage.setScene(scene);
        stage.show();

        System.out.println("Working Directory = " + WORKING_DIRECTORY);
    }

    static void shuffleArray(String[] ar) {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = ThreadLocalRandom.current();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}