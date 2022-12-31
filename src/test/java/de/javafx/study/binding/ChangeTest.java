package de.javafx.study.binding;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ChangeTest {

    private IntegerProperty counter;

    private Integer oldValue;

    private Integer newValue;

    @BeforeEach
    void setUp() {
        oldValue = 100;
        counter = new SimpleIntegerProperty(oldValue);
        counter.addListener(this::changed);
    }

    @Test
    void checkValues() {
        assertThat(counter.get(), equalTo(100));
        counter.set(102);
        assertThat(oldValue, equalTo(100));
        assertThat(newValue, equalTo(102));

        //no changes, same values
        counter.set(102);
        assertThat(oldValue, equalTo(100));
        assertThat(newValue, equalTo(102));

        //change again
        counter.set(112);
        assertThat(oldValue, equalTo(102));
        assertThat(newValue, equalTo(112));
    }


    private void changed(ObservableValue<? extends Number> prop, Number oldValue, Number newValue) {
        this.oldValue = oldValue.intValue();
        this.newValue = newValue.intValue();
    }
}
