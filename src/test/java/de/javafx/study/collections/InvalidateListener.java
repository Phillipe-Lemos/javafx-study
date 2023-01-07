package de.javafx.study.collections;

import javafx.beans.Observable;

public class InvalidateListener {

    private static Observable list;


    public static Observable getList() {
        return list;
    }

    public static void invalidate(Observable list) {
        InvalidateListener.list = list;
    }
}
