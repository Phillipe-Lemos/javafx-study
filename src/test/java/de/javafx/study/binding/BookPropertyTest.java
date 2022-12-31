package de.javafx.study.binding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BookPropertyTest {

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book("Harnessing JavaFX", 9.99, "0123456789");
    }

    @Test
    void checkingInitialValues() {
        assertThat(book.getISBN(), equalTo("0123456789"));
        assertThat(book.getPrice(), equalTo(9.99));
        assertThat(book.getTitle(), equalTo("Harnessing JavaFX"));
    }

    @Test
    void checkingValuesAfterUpdate() {
        book.setTitle("Harnessing JavaFX 17");
        book.setPrice(19.99);

        assertThat(book.getPrice(), equalTo(19.99));
        assertThat(book.getTitle(), equalTo("Harnessing JavaFX 17"));

    }

}
