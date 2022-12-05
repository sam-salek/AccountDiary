package com.samsalek.accountdiary;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Same as the account name, but in lowercase with whitespaces and non-visible characters removed.
     * Used as an identifier for the account.
     */
    private final String id;

    /**
     * The name used when displaying the account.
     */
    private final String name;

    /**
     * A list of bitmap values for each image associated with the account.
     */
    private final List<Bitmap> imageBitmaps = new ArrayList<>();

    public Account(String name) {
        this.name = name;
        this.id = nameToId(name);
    }

    public static String nameToId(String accountName) {
        return accountName.replaceAll("\\s+","").toLowerCase();
    }

    public void addImageBitmap(Bitmap imageBitmap) {
        imageBitmaps.add(imageBitmap);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Bitmap> getImageBitmaps() {
        return Collections.unmodifiableList(imageBitmaps);
    }
}
