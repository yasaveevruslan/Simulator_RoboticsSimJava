package com.roboticssimjava;

import javafx.scene.image.ImageView;

public class ImageHolder {
    private ImageView image;
    public ImageHolder(ImageView imageView){
        this.image = imageView;
    }

    public ImageView getImage() {
        return image;
    }
}
