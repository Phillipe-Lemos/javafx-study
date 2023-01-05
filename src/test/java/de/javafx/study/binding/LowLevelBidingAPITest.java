package de.javafx.study.binding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;

public class LowLevelBidingAPITest {

    private DoubleProperty radius;

    private DoubleBinding areaBinding;

    @BeforeEach
    void setUp() {
        radius = new SimpleDoubleProperty(7D);

        areaBinding = new DoubleBinding() {
            {
                this.bind(radius);
            }

            @Override
            protected double computeValue() {
                double r = radius.get();
                double area = Math.PI * r * r;
                return area;
            }

        };
    }

    @Test
    void checkingBinding() {
        assertThat(areaBinding.get(), closeTo(153.93804, 0.0000002));

        //changing the radius value
        radius.set(14D);
        assertThat("The changes in radius is reflected in area",
                areaBinding.get(), closeTo(615.7521601, 0.0000001));
    }
}
