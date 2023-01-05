package de.javafx.study.binding;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * The {@linkplain javafx.beans.binding.Bindings} is a helper class with a lot of utilities functions.
 */
public class BindingClassTest {

    private DoubleProperty radius;

    private DoubleProperty area;

    private static final String ZIP = "36106";
    private ObjectProperty<Person> p;

    public class Address {
        private StringProperty zip = new SimpleStringProperty(ZIP);

        public StringProperty zipProperty() {
            return zip;
        }
    }

    private class Address1 {
        private StringProperty zip = new SimpleStringProperty(ZIP);

        public StringProperty zipProperty() {
            return zip;
        }
    }

    public class Person {
        private ObjectProperty<Address> addr = new SimpleObjectProperty<>(new Address());

        private ObjectProperty<Address1> addr1 = new SimpleObjectProperty<>(new Address1());

        public ObjectProperty<Address> addrProperty() {
            return addr;
        }

        public ObjectProperty<Address1> addr1Property() {
            return addr1;
        }
    }


    @BeforeEach
    void setUp() {
        radius = new SimpleDoubleProperty(7D);
        area = new SimpleDoubleProperty(0D);
        p = new SimpleObjectProperty<>(new Person());
    }

    @Test
    void bindingSuccessfully() {
        area.bind(Bindings.multiply(
                Bindings.multiply(radius, radius), Math.PI));

        assertThat(area.isBound(), equalTo(Boolean.TRUE));
        assertThat(area.get(), closeTo(153.93804, 0.0000002));

        radius.set(14D);
        assertThat(area.isBound(), equalTo(Boolean.TRUE));
        assertThat("The changes in radius is reflected in area",
                area.get(), closeTo(615.7521601, 0.0000001));
    }

    @Test
    void comparingTwoValues() {
        final BooleanProperty conditionProperty = new SimpleBooleanProperty();
        conditionProperty.bind(
                Bindings.greaterThan(radius, radius.divide(2D))
        );
        assertThat(conditionProperty.isBound(), equalTo(Boolean.TRUE));
        assertThat(conditionProperty.get(), equalTo(Boolean.TRUE));
    }

    @Test
    void successfulGetSelectString() {
        StringBinding zipBinding = Bindings.selectString(p, "addr", "zip");
        assertThat("It is initially created invalid", zipBinding.isValid(), equalTo(Boolean.FALSE));
        assertThat(zipBinding, notNullValue());
        assertThat(zipBinding.get(), equalTo(ZIP));
        assertThat("When the get is call the value is calculated again and the property is validated.",
                zipBinding.isValid(), equalTo(Boolean.TRUE));
    }

    @Test
    void failSelectStringBindingDueClassVisibility() {
        StringBinding zipBinding = Bindings.selectString(p, "addr1", "zip");
        assertThat(zipBinding.isValid(), equalTo(Boolean.FALSE));
        assertThat(zipBinding, notNullValue());
        assertThat(zipBinding.get(), nullValue());
    }


}
