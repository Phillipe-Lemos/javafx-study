module javafx.study {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.swing;
    requires javafx.media;
    requires jdk.jsobject;


    opens de.javafx.study to javafx.graphics, javafx.base;
}