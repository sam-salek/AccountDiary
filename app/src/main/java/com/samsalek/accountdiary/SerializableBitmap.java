package com.samsalek.accountdiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Gil SH on StackOverflow -> https://stackoverflow.com/a/52644816
 */
public class SerializableBitmap implements Serializable {

    private Bitmap bitmap;
    // REMOVED BECAUSE IT COULD NOT BE SERIALIZED. COMPRESS FORMAT IS INSTEAD SET DIRECTLY IN the 'writeObject' method
    //private Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
    private int compressQuality = 100;

    public SerializableBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void recycle() {
        if (bitmap!=null && !bitmap.isRecycled()) bitmap.recycle();
    }

    private void writeObject(ObjectOutputStream out) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, compressQuality, stream);    // COMPRESS FORMAT IS SET ON THIS LINE
        byte[] byteArray = stream.toByteArray();

        try {
            out.writeInt(byteArray.length);
            out.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream in) {

        int bufferLength = 0;
        try {
            bufferLength = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] byteArray = new byte[bufferLength];

        int pos = 0;
        do {
            int read = 0;
            try {
                read = in.read(byteArray, pos, bufferLength - pos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (read != -1) pos += read;
            else break;

        } while (pos < bufferLength);

        bitmap = BitmapFactory.decodeByteArray(byteArray, 0, bufferLength);
    }

    // GETTERS AND SETTERS REMOVED BECAUSE VARIABLE 'CompressFormat' COULD NOT BE SERIALIZED
    /*
    public Bitmap.CompressFormat getCompressFormat() {
        return compressFormat;
    }

    public void setCompressFormat(Bitmap.CompressFormat compressFormat) {
        this.compressFormat = compressFormat;
    }
    */

    public int getCompressQuality() {
        return compressQuality;
    }

    public void setCompressQuality(int compressQuality) {
        this.compressQuality = compressQuality;
    }
}
