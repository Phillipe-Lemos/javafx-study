package de.javafx.study.binding;

import javafx.beans.property.*;

public class Book {

    private static final String UNKNOW = "Unknow";

    private final StringProperty title = new SimpleStringProperty(this, "title", UNKNOW);

    private final DoubleProperty price = new SimpleDoubleProperty(this, "price", 0D);

    private final ReadOnlyStringWrapper ISBN = new ReadOnlyStringWrapper(this, "ISBN", UNKNOW);

    public Book() {}

    public Book(final String title, final double price, final String ISBN){
        this.title.set(title);
        this.price.set(price);
        this.ISBN.set(ISBN);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getISBN() {
        return ISBN.get();
    }

    public ReadOnlyStringWrapper ISBNProperty() {
        return ISBN;
    }

}
