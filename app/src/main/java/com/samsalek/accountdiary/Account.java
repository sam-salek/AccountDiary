package com.samsalek.accountdiary;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account {

    private String name;
    private List<Bitmap> imageBitmaps = new ArrayList<>();

    public Account(String name) {
        this.name = name;
    }

    public void addImageBitmap(Bitmap imageBitmap) {
        imageBitmaps.add(imageBitmap);
    }

    public String getName() {
        return name;
    }

    public List<Bitmap> getImageBitmaps() {
        return Collections.unmodifiableList(imageBitmaps);
    }
}
