package com.richmondstudio.galleryslideshow;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.prefs.Preferences;

import static com.richmondstudio.galleryslideshow.Main.*;

public class ApplicationHelper {
    static Preferences prefs = Preferences.systemNodeForPackage(Main.class);
    private static Stage mStage;
    private static Timeline mSwitchPhotoTrigger;
    public static Boolean switchedPhotoWhilePaused = false;

    public static ContextMenu menu(double x, double y) {
        ContextMenu rightClickMenu = new ContextMenu();
        MenuItem random = new MenuItem("Random");
        MenuItem setInterval = new MenuItem("Set Interval");
        MenuItem exit = new MenuItem("Exit");

        exit.setOnAction(event -> exit(mStage, mSwitchPhotoTrigger));
        random.setOnAction(event -> random());
        setInterval.setOnAction(event -> intervalList(mSwitchPhotoTrigger).show(mStage, x, y));

        rightClickMenu.getItems().addAll(random, setInterval, exit);

        return rightClickMenu;
    }

    private static ContextMenu intervalList(Timeline mSwitchPhotoTrigger) {
        ContextMenu iList = new ContextMenu();
        MenuItem three = new MenuItem("3 Seconds");
        MenuItem five = new MenuItem("5 Seconds");
        MenuItem ten = new MenuItem("10 Seconds");

        three.setOnAction(event -> setInterval(mSwitchPhotoTrigger, 3));
        five.setOnAction(event -> setInterval(mSwitchPhotoTrigger, 5));
        ten.setOnAction(event -> setInterval(mSwitchPhotoTrigger, 10));

        iList.getItems().addAll(three, five, ten);
        return iList;
    }

    private static void setInterval(Timeline mSwitchPhotoTrigger, int i) {
        prefs.putInt(INTERVAL, i);
        interval = i;
        mSwitchPhotoTrigger.stop();
        mSwitchPhotoTrigger = SlideShowHelper.updateSlideShowInterval(i);
        mSwitchPhotoTrigger.setCycleCount(Timeline.INDEFINITE);
        mSwitchPhotoTrigger.play();
    }

    private static void random(){
        if (!prefs.getBoolean(RANDOMIZE, false)) {
            shuffleArray(imagePaths);
            prefs.putBoolean(RANDOMIZE, true);
        } else {
            prefs.putBoolean(RANDOMIZE, false);
        }
    }

    public static void exit(Stage stage ,Timeline switchPhotoTrigger){
        switchPhotoTrigger.stop();
        stage.close();
    }

    public static Timeline initiza(Stage stage, BorderPane root, Timeline switchPhotoTrigger) {
        mStage = stage;
        mSwitchPhotoTrigger = switchPhotoTrigger;

        //Close when escape is hit
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                exit(stage, mSwitchPhotoTrigger);
            }
            //Pause with Spacebar
            if (KeyCode.SPACE == event.getCode()) {
                if (mSwitchPhotoTrigger.getStatus() == Animation.Status.RUNNING) {
                    mSwitchPhotoTrigger.pause();
                } else {
                    if (switchedPhotoWhilePaused) {
                        mSwitchPhotoTrigger.playFrom(Duration.seconds(0.00));
                        switchedPhotoWhilePaused = false;
                    } else {
                        mSwitchPhotoTrigger.play();
                    }

                }
            }

            if (KeyCode.LEFT == event.getCode()) {
                ITERATOR -= 2;
                if (ITERATOR < 0) {
                    ITERATOR = imagePaths.length - 2;
                }
                triggerSlide(mSwitchPhotoTrigger);
                mSwitchPhotoTrigger.playFrom(Duration.seconds(0));
            }
            if (KeyCode.RIGHT == event.getCode()) {
                if (ITERATOR > imagePaths.length - 1) {
                    ITERATOR = 0;
                }
                triggerSlide(mSwitchPhotoTrigger);
                mSwitchPhotoTrigger.playFrom(Duration.seconds(0));
            }

        });

        //Handle Mouse Events
        root.setOnMouseClicked(event -> {
            MouseButton button = event.getButton();
            if(button==MouseButton.PRIMARY){
                if (ITERATOR > imagePaths.length - 1) {
                    ITERATOR = 0;
                }
                triggerSlide(mSwitchPhotoTrigger);
                mSwitchPhotoTrigger.playFrom(Duration.seconds(0));
            }else if(button==MouseButton.SECONDARY){
                menu(event.getX(), event.getY()).show(stage, event.getX(), event.getY());
            }
        });
        mSwitchPhotoTrigger.setCycleCount(Timeline.INDEFINITE);
        mSwitchPhotoTrigger.play();

        return mSwitchPhotoTrigger;
    }

    private static void triggerSlide(Timeline switchPhotoTrigger){
        SlideShowHelper.setImageView(SlideShowHelper.imageView);
        if (switchPhotoTrigger.getStatus() == Animation.Status.PAUSED) {
            switchedPhotoWhilePaused = true;
        }

    }
}
