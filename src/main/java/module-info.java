module com.example.galleryslideshow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.prefs;
    requires java.desktop;


    opens com.richmondstudio.galleryslideshow to javafx.fxml;
    exports com.richmondstudio.galleryslideshow;
}