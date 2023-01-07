package de.javafx.study.collections;

import javafx.collections.ListChangeListener;

public class ExtractChangeObjFromListChangeListener implements ListChangeListener {

    private Change changeMade;

    @Override
    public void onChanged(Change change) {
        this.changeMade = change;
    }

    public Change getChangeMade() {
        return changeMade;
    }
}
