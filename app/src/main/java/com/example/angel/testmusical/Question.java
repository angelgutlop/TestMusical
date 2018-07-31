package com.example.angel.testmusical;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import com.example.angel.testmusical.Utils.AssetUtils;

public class Question {

    public int respuestaCorrecta = 0;
    String opciones[];
    String imagen;
    Uri audioUri;
    String descipcion;
    String pregunta;


    public Question(String pregunta, int correcta, String opciones[], String imagen, String audioUriStr, String descripcion) {
        this.respuestaCorrecta = correcta;
        this.opciones = opciones;
        this.imagen = imagen;
        this.audioUri = AssetUtils.getAudioUri(audioUriStr);
        this.descipcion = descripcion;
        this.pregunta = pregunta;
    }


    public Boolean verificar(int nopcionsel) {
        return nopcionsel == respuestaCorrecta;
    }

    public Bitmap getBitmap(Context context) {

        return AssetUtils.getAssetBitmap(context, imagen);
    }
}
