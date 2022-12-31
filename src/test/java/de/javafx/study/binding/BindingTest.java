package de.javafx.study.binding;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BindingTest {

    private IntegerProperty x;

    private IntegerProperty y;

    private NumberBinding sum;

    @BeforeEach
    void setUp() {
        x = new SimpleIntegerProperty(100);
        y = new SimpleIntegerProperty(200);

        //create a binding : sum = x + y
        sum = x.add(y);
    }

    @Test
    void checkIfIsValidAfterCreation() {
        assertThat("After creation is expected to be false the binding valid()", sum.isValid(), equalTo(Boolean.FALSE));
    }

    @Test
    void becomesValidAfterGetTheValue() {
        assertThat(sum.getValue(), equalTo(300));
        assertThat("After change a value, the binding is valid", sum.isValid(), equalTo(Boolean.TRUE));
    }

    @Test
    void becomingInvalidAfterChangeValue() {
        assertThat(sum.getValue(), equalTo(300));
        assertThat("After change a value, the binding is valid", sum.isValid(), equalTo(Boolean.TRUE));
        x.set(250);
        assertThat("After change a value, the binding is valid", sum.isValid(), equalTo(Boolean.FALSE));
    }
}
