package com.roboticssimjava;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;

public class ImgTableView {

    private final SimpleStringProperty name;
    private final SimpleStringProperty data;
    private final SimpleObjectProperty<Image> image;
    private final SimpleBooleanProperty checkBoxSelectedDelete;
    private final SimpleBooleanProperty checkBoxSelectedShow;

    private final int id;
    public ImgTableView(int id, String name, byte[] image, String data, boolean checkBoxSelectedDelete,  boolean checkBoxSelectedShow){
        this.name = new SimpleStringProperty(name);
        this.data = new SimpleStringProperty(data);
        this.id = id;
        Image img = new Image(new ByteArrayInputStream(image));
        this.image = new SimpleObjectProperty<>(img);
        this.checkBoxSelectedDelete = new SimpleBooleanProperty(checkBoxSelectedDelete);
        this.checkBoxSelectedShow = new SimpleBooleanProperty(checkBoxSelectedShow);

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public Image getImage() {
        return image.get();
    }

    public SimpleObjectProperty<Image> imageProperty() {
        return image;
    }

    public boolean isCheckBoxSelectedDelete() {
        return checkBoxSelectedDelete.get();
    }

    public void setCheckBoxSelectedDelete(boolean selected) {
        checkBoxSelectedDelete.set(selected);
    }

    public SimpleBooleanProperty checkBoxSelectedDeleteProperty() {
        return checkBoxSelectedDelete;
    }

    public boolean isCheckBoxSelectedShow() {
        return checkBoxSelectedShow.get();
    }

    public void setCheckBoxSelectedShow(boolean selected) {
        checkBoxSelectedShow.set(selected);
    }

    public SimpleBooleanProperty checkBoxSelectedShowProperty() {
        return checkBoxSelectedShow;
    }
}
