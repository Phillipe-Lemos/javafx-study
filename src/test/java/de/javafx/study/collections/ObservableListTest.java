package de.javafx.study.collections;

import javafx.beans.Observable;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The {@linkplain javafx.beans.property.MapProperty} and {@linkplain javafx.beans.property.SetProperty} have a lot of
 * similarities with {@linkplain javafx.beans.property.ListProperty}, so I will skip this tests
 */
public class ObservableListTest {

    private ObservableList<String> list;

    private ObservableList<String> secondList;

    private ExtractChangeObjFromListChangeListener changeListener = new ExtractChangeObjFromListChangeListener();

    @BeforeEach
    void setUp() {
        list = FXCollections.observableArrayList("first", "second");
        secondList = FXCollections.observableArrayList("1", "2");
    }

    @Test
    void successTrackingBindinContentgAnotherCollection() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);
        final ListProperty<String> propertyList2 = new SimpleListProperty<>(secondList);
        propertyList.bindContent(propertyList2);

        propertyList2.add("3");

        assertThat(secondList.indexOf("3"), equalTo(2));
        assertThat(list.indexOf("3"), equalTo(2));
    }

    @Test
    void failTrackingBindinContentgFromAnotherCollectionDueUpdateCollection() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);
        final ListProperty<String> propertyList2 = new SimpleListProperty<>(secondList);
        propertyList.bindContent(propertyList2);

        // As bindContent is bidirecional, adding element in propertyList does not
        // reflect into propertyList2, but the other way around yes. Check the example
        // above.
        propertyList.add("3");

        assertThat(secondList.indexOf("3"), equalTo(-1));
        assertThat(list.indexOf("3"), equalTo(2));
    }


    @Test
    void throwingExceptionWhenTryToBindingTheSammeProperty() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);
        assertThrows(IllegalArgumentException.class, () -> propertyList.bindContent(propertyList));
    }

    @Test
    void throwingNullPointerExceptionWhenNullIsPassed() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);
        assertThrows(NullPointerException.class, () -> propertyList.bindContent(null));
    }

    @Test
    void successTrackingBindContentBidirectionalAnotherCollection() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);
        final ListProperty<String> propertyList2 = new SimpleListProperty<>(secondList);
        propertyList.bindContentBidirectional(propertyList2);

        propertyList2.add("3");

        assertThat(secondList.indexOf("3"), equalTo(2));
        assertThat(list.indexOf("3"), equalTo(2));

        propertyList.add("4");
        assertThat(secondList.indexOf("4"), equalTo(3));
        assertThat(list.indexOf("4"), equalTo(3));
    }

    @Test
    void failBindContentBidirectionalUsingTheSameProperty() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);

        assertThrows(IllegalArgumentException.class, () -> propertyList.bindContentBidirectional(propertyList));
    }

    @Test
    void failBindContentBidirectionalUsingNullProperty() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(list);

        assertThrows(NullPointerException.class, () -> propertyList.bindContentBidirectional(null));
    }


    @Test
    void checkingInvalidateListener() {
        list.addListener(InvalidateListener::invalidate);
        list.add("third");

        assertThat(list.size(), equalTo(3));
        Observable observable = InvalidateListener.getList();
        assertThat(observable, notNullValue());
        assertThat(observable, equalTo(list));
        list.removeListener(InvalidateListener::invalidate);
    }

    @Test
    void checkingChangeListener() {
        list.addListener(changeListener);
        list.add("fourth");
        ListChangeListener.Change changeDone = changeListener.getChangeMade();


        assertThat(changeDone, notNullValue());
        while (changeDone.next()) {
            assertThat(changeDone.wasAdded(), equalTo(Boolean.TRUE));
            assertThat(changeDone.wasRemoved(), equalTo(Boolean.FALSE));
            assertThat(changeDone.wasUpdated(), equalTo(Boolean.FALSE));
            assertThat(changeDone.wasPermutated(), equalTo(Boolean.FALSE));
            assertThat(changeDone.wasReplaced(), equalTo(Boolean.FALSE));
            assertThat(changeDone.getList(), equalTo(list));
        }

        list.removeListener(changeListener);
    }

    @Test
    void trackingOneElementFromTheList() {
        final ListProperty<String> propertyList = new SimpleListProperty<>(FXCollections.observableArrayList());
        //bind the last item element
        ObjectBinding<String> last = propertyList.valueAt(propertyList.sizeProperty().subtract(1));

        propertyList.addAll("1", "2", "3");

        assertThat(last.get(), equalTo("3"));
    }

    @Test
    void bindingAStringPropertyWithListPropertiesSizeAndEmptyProperties() {
        final ObservableList<String> initialList = FXCollections.observableArrayList();
        final ListProperty<String> propertyList = new SimpleListProperty<>(initialList);
        final StringProperty initial = new SimpleStringProperty("Size of: ");
        final StringProperty description = new SimpleStringProperty();
        description.bind(initial.concat(propertyList.sizeProperty())
                .concat(", Empty: ")
                .concat(propertyList.emptyProperty())
                .concat(", List: ")
                .concat(propertyList.asString()));

        assertThat(description.get(), equalTo("Size of: 0, Empty: true, List: []"));
        propertyList.addAll("1", "2", "3");
        assertThat(description.get(), equalTo("Size of: 3, Empty: false, List: [1, 2, 3]"));
        initialList.add("4");
        assertThat(description.get(), equalTo("Size of: 4, Empty: false, List: [1, 2, 3, 4]"));
    }

}
