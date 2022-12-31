package de.javafx.study.binding;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BoundPropertyTest {

    private IntegerProperty x;
    private IntegerProperty y;
    private IntegerProperty z;

    @BeforeEach
    void setUp() {
        x = new SimpleIntegerProperty(10);
        y = new SimpleIntegerProperty(20);
        z = new SimpleIntegerProperty(60);

        //creating a binding expression
        z.bind(x.add(y));
    }

    @Test
    void checkingIfItIsBound() {
        assertThat("isBound should be true", z.isBound(), equalTo(Boolean.TRUE));
    }

    @Test
    void checkingTheNewBoundValue() {
        x.set(15);
        y.set(19);
        assertThat(z.get(), equalTo(34));
    }

    @Test
    void whenUnboundValueNotUpdate() {
        z.unbind();
        assertThat("isBound should be false", z.isBound(), equalTo(Boolean.FALSE));
        x.set(15);
        y.set(19);
        assertThat(z.get(), equalTo(30));
    }

    @Test
    void throwsExceptionWhenTryToUpdateBoundValue() {
        Exception exception = assertThrows(Exception.class, () -> z.set(100));
        assertThat(exception, instanceOf(RuntimeException.class));
    }

    @Test
    void notThrowExceptionAfterUnbindAndChangeValue() {
        z.unbind();
        z.set(100);
        assertThat(z.get(), equalTo(100));
    }

}
