package de.javafx.study.binding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;

/**
 * Tests the basic concepts from {@linkplain javafx.beans.property.DoubleProperty}. The other propertis classes like (
 * {@linkplain javafx.beans.property.IntegerProperty}, {@linkplain javafx.beans.property.LongProperty} and
 * {@linkplain javafx.beans.property.FloatProperty}) have the same methods with corresponding data type.
 * So here I will create unit-tests only.
 */
public class DoublePropertyTest {

    private DoubleProperty radius;


    @BeforeEach
    void setUp() {
        radius = new SimpleDoubleProperty(7D);
    }

    @Test
    void checkingSuccessffulMultiplyBinding() {
        DoubleBinding area = radius.multiply(radius).multiply(Math.PI);
        assertThat(radius.get(), equalTo(7D));
        assertThat(area.get(), closeTo(153.93804, 0.0000002));

        //changing radius
        radius.set(14D);
        assertThat(radius.get(), equalTo(14D));
        assertThat(area.get(), closeTo(615.7521601, 0.0000001));
    }

    @Test
    void checkingSuccessffulDivisionBinding() {
        DoubleBinding resultOfDivision = radius.divide(7D);
        assertThat(radius.get(), equalTo(7D));
        assertThat(resultOfDivision.get(), equalTo(1D));

        radius.set(14D);
        assertThat(radius.get(), equalTo(14D));
        assertThat("When the value is increased the bound value should be changed :",
                resultOfDivision.get(), equalTo(2D));
    }

    @Test
    void checkingIfIsBound() {
        DoubleProperty area = new SimpleDoubleProperty();
        assertThat(area.isBound(), equalTo(Boolean.FALSE));

        area.bind(radius.multiply(radius).multiply(Math.PI));

        assertThat("Should be true because the bind call.",
                area.isBound(), equalTo(Boolean.TRUE));
        assertThat(area.get(), closeTo(153.93804, 0.0000002));

        radius.set(14D);
        assertThat("The changes in radius is reflected in area",
                area.get(), closeTo(615.7521601, 0.0000001));
    }

    @Test
    void afterUnbindThereIsNoChangesPropagated() {
        DoubleProperty area = new SimpleDoubleProperty();
        assertThat(area.isBound(), equalTo(Boolean.FALSE));

        area.bind(radius.multiply(radius).multiply(Math.PI));

        assertThat(area.isBound(), equalTo(Boolean.TRUE));
        assertThat(area.get(), closeTo(153.93804, 0.0000002));

        area.unbind();
        assertThat("Should return false due the unbind() call.", area.isBound(), equalTo(Boolean.FALSE));
        radius.set(14D);
        assertThat("Although radius have change, area is not anymore bound to it, so it remains with the same value",
                area.get(), closeTo(153.93804, 0.0000002));
    }

    @Test
    void comparingValues() {
        DoubleProperty radius1 = new SimpleDoubleProperty(15D);
        final BooleanProperty conditionProperty = new SimpleBooleanProperty();
        //Here can be constructed a more complex boolean expression
        conditionProperty.bind(radius1.greaterThan(radius));

        assertThat(conditionProperty.get(), equalTo(Boolean.TRUE));
        radius1.set(3D);
        assertThat(conditionProperty.get(), equalTo(Boolean.FALSE));
    }


}
