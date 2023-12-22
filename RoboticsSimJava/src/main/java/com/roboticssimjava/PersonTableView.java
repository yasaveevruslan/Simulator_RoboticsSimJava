package com.roboticssimjava;

import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;

public class PersonTableView {

    private final SimpleStringProperty firstName;

    private final SimpleStringProperty lastName;
    private final SimpleStringProperty rightPerson;
    private final SimpleBooleanProperty  rightCheckBox;


    public PersonTableView(String name, String lastName, String rightPerson, boolean rightCheckBoxValue)
    {
        this.firstName = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.rightPerson = new SimpleStringProperty(rightPerson);
        this.rightCheckBox = new SimpleBooleanProperty(rightCheckBoxValue);
    }

    public String getName() {
        return firstName.get();
    }

    public SimpleStringProperty nameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public String getRightName() {
        return rightPerson.get();
    }

    public SimpleStringProperty rightProperty() {
        return rightPerson;
    }

    public SimpleBooleanProperty  rightCheckBoxProperty() {
        return rightCheckBox;
    }

    public boolean isRightCheckBoxSelected() {
        return rightCheckBox.get();
    }

    public void setRightCheckBoxSelected(boolean selected) {
        rightCheckBox.set(selected);
    }


}
