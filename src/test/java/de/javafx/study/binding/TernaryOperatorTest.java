package de.javafx.study.binding;

import javafx.beans.binding.StringBinding;
import javafx.beans.binding.When;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TernaryOperatorTest {

    private IntegerProperty num;

    private StringBinding desc;


    @BeforeEach
    void setUp() {
        num = new SimpleIntegerProperty(10);
    }

    @Test
    void checkingTernayOperator() {
        desc = new When(num.divide(2).multiply(2).isEqualTo(num))
                .then("even")
                .otherwise("odd");

        assertThat(desc.get(), equalTo("even"));
        num.set(11);
        assertThat(desc.get(), equalTo("odd"));
    }

}
