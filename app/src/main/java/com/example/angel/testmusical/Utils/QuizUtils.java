package com.example.angel.testmusical.Utils;

import android.content.Context;

import com.example.angel.testmusical.Database.JsonDatabase;
import com.example.angel.testmusical.Question;
import com.example.angel.testmusical.R;
import com.example.angel.testmusical.TrakItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuizUtils {

    private final static Integer NPOSIBILIDAES_PREGUNTA = 4;
    private static Context mContext;
    private static Random random = new Random();
    //Todo sakvar next question y list trakitem en memoria cuando cambia de estado la app
    private static Integer currentQuestion = -1;
    public static List<TrakItem> randomTraks = new ArrayList<>();
    public static List<String> authorsList = new ArrayList<>();
    public static List<String> songsList = new ArrayList<>();
    public static List<String> yearsList = new ArrayList<>();
    private static List<Integer> tipoPregunta;

    private enum QUESTION_TYPE {
        NOMBRE_CANCION,
        NOMBRE_COMPOSITOR,
        AÑO_CANCION
    }


    public static void initializeBatchOfQuestions(Context context, int nQuestions) {
        currentQuestion = 0;
        String jsonStr = JsonDatabase.loadJSON(context);
        mContext = context;
        authorsList = JsonDatabase.getAllComposersNames(jsonStr);
        songsList = JsonDatabase.getAllSongName(jsonStr);
        yearsList = JsonDatabase.getAllYears(jsonStr);

        randomTraks = JsonDatabase.getRandomTraks(jsonStr, nQuestions);

        tipoPregunta = RandomUtils.generateRandomIntegerArray(QUESTION_TYPE.values().length, nQuestions);

    }


    public static Boolean cuestionCompleted() {

        return currentQuestion++ >= randomTraks.size() - 1;
    }


    public static Integer getNumberOfQuestions() {

        return randomTraks.size();
    }

    public static String getCuestionProgressString() {
        int preg = currentQuestion + 1;
        int npreg = randomTraks.size();
        return preg + "/" + npreg;
    }

    public static Question getCurrentQuestion() {
        return getnQuestion(currentQuestion);
    }

    public static Question getnQuestion(int nq) {
        List<Integer> lista = RandomUtils.generateRandomSuffledIntegerList(NPOSIBILIDAES_PREGUNTA);
        TrakItem trakItem = randomTraks.get(nq);
        String[] respuestas = new String[NPOSIBILIDAES_PREGUNTA];


        QUESTION_TYPE question_type = QUESTION_TYPE.values()[tipoPregunta.get(nq)];
        List<String> randomAnswers = new ArrayList<>();
        String pregunta = "";
        switch (question_type) {
            case NOMBRE_COMPOSITOR:
                randomAnswers = getRandomStrings(authorsList, trakItem.compositor, NPOSIBILIDAES_PREGUNTA);
                pregunta = mContext.getResources().getString(R.string.tipo_pregunta_compositor_texto);
                break;

            case NOMBRE_CANCION:
                randomAnswers = getRandomStrings(songsList, trakItem.trakname, NPOSIBILIDAES_PREGUNTA);
                pregunta = mContext.getResources().getString(R.string.tipo_pregunta_nombre_canción_texto);
                break;

            case AÑO_CANCION:
                randomAnswers = getRandomStrings(yearsList, trakItem.año, NPOSIBILIDAES_PREGUNTA);
                pregunta = mContext.getResources().getString(R.string.tipo_pregunta_año_texto);
                break;

        }
        for (int i = 0; i < lista.size(); i++) respuestas[lista.get(i)] = randomAnswers.get(i);

        return new Question(pregunta, lista.get(0), respuestas, randomTraks.get(nq).albumImageArt, trakItem.musicalAsset, trakItem.getDescription());
    }


    private static List<String> getRandomStrings(List<String> listaStrings, String correctName, int nposib) {

        List<String> respuestas = new ArrayList<>();
        respuestas.add(correctName);

        List<Integer> lista = RandomUtils.generateRandomSuffledIntegerList(listaStrings.size());


        for (int i = 0; i < lista.size(); i++) {
            int id = lista.get(i);
            String randomAuthor = listaStrings.get(id);

            if (!randomAuthor.equals(correctName)) respuestas.add(randomAuthor);
            if (respuestas.size() == nposib) break;
        }

        return respuestas;

    }

}



