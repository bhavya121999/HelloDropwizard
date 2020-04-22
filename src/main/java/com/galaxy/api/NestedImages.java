package com.galaxy.api;

import java.util.List;

public class NestedImages {
    List<Image> images;

    public NestedImages() {
    }

    public NestedImages(List<Image> images) {
        this.images = images;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}


