package de.javafx.study.binding;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BidirecionalBindingTest {

    private IntegerProperty x;

    private IntegerProperty y;

    private IntegerProperty z;

    @BeforeEach
    void setUp() {
        x = new SimpleIntegerProperty(1);
        y = new SimpleIntegerProperty(2);
        z = new SimpleIntegerProperty(3);
    }

    @Test
    void checkingValuesBeforeBinding() {
        assertThat(x.get(), equalTo(1));
        assertThat(y.get(), equalTo(2));
        assertThat(z.get(), equalTo(3));
    }


    @Test
    void checkingBindingXAndY() {
        x.bindBidirectional(y);
        assertThat(x.get(), equalTo(2));
        assertThat(y.get(), equalTo(2));
        x.unbindBidirectional(y);
    }

    @Test
    void afterBidingTheLastOneAllShouldBeEqual() {
        x.bindBidirectional(y);
        x.bindBidirectional(z);
        assertThat(x.get(), equalTo(3));
        assertThat(y.get(), equalTo(3));
        assertThat(z.get(), equalTo(3));
    }

}
