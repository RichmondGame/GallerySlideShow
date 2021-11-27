package com.richmondstudio.galleryslideshow;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.*;

import static com.richmondstudio.galleryslideshow.Main.*;

public class SlideShowHelper {

    private static ImageView imageView;

    public static String[] getImagePathsFromWorkingDirectory(File workingDirectory) {
        FilenameFilter filter = (file, name) -> {
            name = name.toLowerCase();
            if (name.endsWith(".png") || name.endsWith(".jpg")
                    || name.endsWith(".jpeg") || name.endsWith(".gif")
                    || name.endsWith(".bmp"))
                return true;

            return false;
        };
        return workingDirectory.list(filter);
    }

    public static ImageView setImageViewPref(Rectangle2D screenBounds) {
        ImageView theImageView = new ImageView();
        theImageView.setPreserveRatio(true);
        theImageView.setFitWidth(screenBounds.getWidth());
        theImageView.setFitHeight(screenBounds.getHeight());
        imageView = theImageView;
        return imageView;
    }

    public static ImageView setImageView(ImageView iView) {
        InputStream stream = null;
        if (ITERATOR > imagePaths.length) {
            ITERATOR = 0;
        }
        try {
            stream = new FileInputStream(WORKING_DIRECTORY +
                    "\\" + imagePaths[++ITERATOR]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(stream);
        iView.setImage(image);
        return iView;
    }

    public static Timeline createSlideShowTimer(int mInterval, ImageView iView) {
        return new Timeline(
                new KeyFrame(Duration.seconds(mInterval),
                        event -> setImageView(iView)));
    }

    public static Timeline updateSlideShowInterval(int i) {
        return new Timeline(
                new KeyFrame(Duration.seconds(i),
                        event -> setImageView(imageView)));
    }

}
