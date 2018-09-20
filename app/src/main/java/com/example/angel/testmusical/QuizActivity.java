package com.example.angel.testmusical;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.angel.testmusical.Utils.AssetUtils;
import com.example.angel.testmusical.Utils.QuizUtils;
import com.example.angel.testmusical.Utils.SharedPrefsUtils;
import com.google.android.exoplayer2.ui.PlayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import info.hoang8f.widget.FButton;


public class QuizActivity extends AppCompatActivity {

    @BindView(R.id.questionProgreessTextView)
    public TextView questionProgreesTextView;

    @BindView(R.id.playerView)
    public PlayerView playerView;
    @BindView(R.id.songDescriptionTextView)
    public TextView songDescriptionTextView;
    @BindView(R.id.preguntaTextView)
    public TextView preguntaTextView;
    @BindView(R.id.animacion_pregunta)
    public com.airbnb.lottie.LottieAnimationView animacionPregunta;
    @BindView(R.id.texto_estado_pregunta_textView)
    public TextView textoEstadoPreguntaTextView;

    private Question question;
    private Integer nAciertos;
    private PlayerControl playerControl;
    private Boolean seHaRespondido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);

        playerControl = new PlayerControl(this, playerView);


        animacionPregunta.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                textoEstadoPreguntaTextView.setVisibility(View.VISIBLE);
                finishQuestion();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        nAciertos = 0;
        presentQuestion();
    }

    @Override
    protected void onResume() {
        playerControl.resume();
        super.onResume();
    }


    @Override
    protected void onPause() {
        playerControl.pause();
        super.onPause();
    }

    private void presentQuestion() {

        seHaRespondido = false;

        playerControl.stop();
        songDescriptionTextView.setVisibility(View.INVISIBLE);
        preguntaTextView.setVisibility(View.VISIBLE);
        animacionPregunta.setVisibility(View.INVISIBLE);
        textoEstadoPreguntaTextView.setVisibility(View.INVISIBLE);

        questionProgreesTextView.setText(QuizUtils.getCuestionProgressString());
        Bitmap bmp = AssetUtils.getAssetBitmap(this, Compositores.getComposerImageName(Compositores.IMAGENES.INCOGNITA));
        playerControl.setImage(bmp);
        question = QuizUtils.getCurrentQuestion();
        preguntaTextView.setText(question.pregunta);

        for (int i = 0; i < question.opciones.length; i++) {

            FButton button = getButton(i);

            button.setText(question.opciones[i]);
            button.setButtonColor(getResources().getColor(R.color.opcionBotonNormal));

        }

        playerControl.play(question.audioUri);

    }


    @OnClick({R.id.respuestaAButton, R.id.respuestaBButton, R.id.respuestaCButton, R.id.respuestaDButton})
    public void buttonClick(View view) {

        if (seHaRespondido) return;
        seHaRespondido = true;

        FButton button = (FButton) view;
        int opcion = getButtonOption(view.getId());

        Boolean acierto = question.verificar(opcion);

        songDescriptionTextView.setText(question.descipcion);
        songDescriptionTextView.setVisibility(View.VISIBLE);
        preguntaTextView.setVisibility(View.INVISIBLE);
        animacionPregunta.setVisibility(View.VISIBLE);
        playerControl.setImage(question.getBitmap(this));

        final String textoEstadoRespuesta;
        if (acierto) {
            button.setButtonColor(getResources().getColor(R.color.respuestaCorrecta));
            textoEstadoRespuesta = getResources().getString(R.string.texto_respuesta_correcta);

            animacionPregunta.setAnimation("animaciones/estrella.json");
            nAciertos++;

        } else {
            button.setButtonColor(getResources().getColor(R.color.respuestaIncorrecta));
            getButton(question.respuestaCorrecta).setButtonColor(getResources().getColor(R.color.respuestaCorrecta));
            textoEstadoRespuesta = getResources().getString(R.string.texto_respuesta_erronea);

            animacionPregunta.setAnimation("animaciones/emoji_shock.json");
        }

        textoEstadoPreguntaTextView.setText(textoEstadoRespuesta);
        animacionPregunta.playAnimation();

    }

    public void finishQuestion() {

        Boolean finisTest = QuizUtils.cuestionCompleted();

        if (!finisTest) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    presentQuestion();
                }
            }, 2000);

        } else {

            Integer maxHighsScore = SharedPrefsUtils.getInt(this, R.string.max_high_score_key, 0);
            if (maxHighsScore < nAciertos) {
                SharedPrefsUtils.saveInt(this, R.string.max_high_score_key, nAciertos);
                SharedPrefsUtils.saveInt(this, R.string.max_high_score_nquestions_key, QuizUtils.getNumberOfQuestions());
            }

            SharedPrefsUtils.saveInt(this, R.string.last_score_key, nAciertos);
            SharedPrefsUtils.saveInt(this, R.string.last_score_nquestions_key, QuizUtils.getNumberOfQuestions());

            playerControl.stop();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 2000);

        }

    }


    private FButton getButton(int n) {
        return (FButton) findViewById(getButtonId(n));
    }

    private Integer getButtonId(int n) {
        switch (n) {
            case 0:
                return R.id.respuestaAButton;
            case 1:
                return R.id.respuestaBButton;
            case 2:
                return R.id.respuestaCButton;
            case 3:
                return R.id.respuestaDButton;
        }
        return null;

    }

    private Integer getButtonOption(int id) {

        for (int i = 0; i < 4; i++) {
            if (id == getButtonId(i)) return i;
        }
        return null;
    }

}
