package com.example.angel.testmusical.Utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AssetUtils {
    private static String imageFolder = "imagenes/";
    private static String songFolder = "canciones/";
    public static String assetsPrefix = "asset:///";

    public static Bitmap getAssetBitmap(Context context, String imageName) {

        try {
            String fullFilePath = imageFolder + imageName;
            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(fullFilePath);
            return BitmapFactory.decodeStream(is);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getAssetFile(Context context, String filename) {

        try {

            AssetManager assetManager = context.getAssets();
            InputStream is = assetManager.open(filename);
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder buf = new StringBuilder();

            String str;
            while ((str = in.readLine()) != null) buf.append(str);
            in.close();

            return buf.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


    public static Uri getAudioUri(String songName) {

        String uriStr = assetsPrefix + songFolder + songName;
        return Uri.parse(uriStr);

    }


}
