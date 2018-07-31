package com.example.angel.testmusical.Database;

import android.content.Context;

import com.example.angel.testmusical.TrakItem;
import com.example.angel.testmusical.Utils.AssetUtils;
import com.example.angel.testmusical.Utils.RandomUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class JsonDatabase {

    private static final String ID = "ID";
    private static final String COMPOSITOR = "COMPOSITOR";
    private static final String GENERO = "GENERO";
    private static final String MUSICAL_ASSET = "ARCHIVO";
    private static final String NOMBRE_CANCION = "NOMBRE_CANCION";
    private static final String AÑO = "AÑO";

    private static final String MUSIC_DB = "MUSICA";
    private final static String jsonDatabaseFile = "traks.json";


    public static List<TrakItem> getRandomTraks(String json, int n) {
        List<TrakItem> traks = new ArrayList<>();

        try {
            JSONObject jsonReader = new JSONObject(json);
            JSONArray musicDBJson = jsonReader.getJSONArray(MUSIC_DB);

            List<Integer> lista = RandomUtils.generateRandomSuffledIntegerList(musicDBJson.length());

            for (int i = 0; i < n; i++) {
                int id = lista.get(i);
                JSONObject jsonObject = (JSONObject) musicDBJson.get(id);
                TrakItem trakItem = jsonTrak((JSONObject) jsonObject.get(ID + id));
                traks.add(trakItem);
            }

        } catch (JSONException e) {
            Timber.e(e);
        }

        return traks;
    }


    public static List<String> getAllComposersNames(String json) {
        return getAllFieldValues(json, COMPOSITOR);
    }

    public static List<String> getAllYears(String json) {
        return getAllFieldValues(json, AÑO);
    }

    public static List<String> getAllSongName(String json) {
        return getAllFieldValues(json, NOMBRE_CANCION);
    }

    private static List<String> getAllFieldValues(String json, String field) {
        List<String> authorsList = new ArrayList<>();

        try {
            JSONObject jsonReader = new JSONObject(json);
            JSONArray musicDBJson = jsonReader.getJSONArray(MUSIC_DB);


            for (int id = 0; id < musicDBJson.length(); id++) {

                JSONObject jsonObject = (JSONObject) musicDBJson.get(id);
                authorsList.add(((JSONObject) jsonObject.get(ID + id)).get(field).toString());

            }

        } catch (JSONException e) {
            Timber.e(e);
        }

        return authorsList;
    }

    private static TrakItem jsonTrak(JSONObject jsonObject) throws JSONException {
        String cancion = (String) jsonObject.get(NOMBRE_CANCION);
        String compositor = (String) jsonObject.get(COMPOSITOR);
        String genero = (String) jsonObject.get(GENERO);
        String musicalAsset = (String) jsonObject.get(MUSICAL_ASSET);
        String año = (String) jsonObject.get(AÑO);

        return new TrakItem(cancion, compositor, musicalAsset, genero, año);
    }


    public static String loadJSON(Context context) {

        return AssetUtils.getAssetFile(context, jsonDatabaseFile);

    }

}

