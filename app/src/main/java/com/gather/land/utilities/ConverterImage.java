package com.gather.land.utilities;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class ConverterImage {


    public static byte[] getBytsFromFile(File file) {
        try {
            byte bytes[] = new byte[(int) file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(bytes);
            return bytes;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmapImage(byte[] imageProfileRAW) {
         return BitmapFactory.decodeByteArray(imageProfileRAW, 0, imageProfileRAW.length);
    }
}
