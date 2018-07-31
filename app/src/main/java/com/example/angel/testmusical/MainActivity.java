package com.example.angel.testmusical;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.angel.testmusical.Database.TrakProvider;
import com.example.angel.testmusical.Utils.QuizUtils;
import com.example.angel.testmusical.Utils.SharedPrefsUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final int NUMBER_OF_QUESTIONS = 5;

    private static final int LOADER_TRAKS_ID = 001;


    @BindView(R.id.last_Score_textView)
    TextView lastScoreTextView;

    @BindView(R.id.max_Score_textView)
    TextView maxScoreTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
        getSupportLoaderManager().initLoader(LOADER_TRAKS_ID, null, this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarMarcadores();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.borrar_marcas_menu_item:
                SharedPrefsUtils.saveInt(this, R.string.last_score_key, 0);
                SharedPrefsUtils.saveInt(this, R.string.last_score_nquestions_key, 0);
                SharedPrefsUtils.saveInt(this, R.string.max_high_score_key, 0);
                SharedPrefsUtils.saveInt(this, R.string.max_high_score_nquestions_key, 0);
                actualizarMarcadores();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.nuevo_juego_button)
    public void onClick(View v) {

        int id = v.getId();

        switch (id) {
            case R.id.nuevo_juego_button:
                QuizUtils.initializeBatchOfQuestions(this, NUMBER_OF_QUESTIONS);
                Intent intent = new Intent(this, QuizActivity.class);
                startActivity(intent);
                return;
        }
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {

        switch (id) {
            case LOADER_TRAKS_ID: {
                Uri u = TrakProvider.Traks.TRAKS;

                return new CursorLoader(this, u, null, null, null, null);

            }

        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {

        int id = loader.getId();

        switch (id) {

            case LOADER_TRAKS_ID: {
                Cursor cursor = (Cursor) data;

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToPosition(i);
                    Timber.d("id = %d", cursor.getInt(0));
                }
                return;
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    private void actualizarMarcadores() {
        int maxScore = SharedPrefsUtils.getInt(this, R.string.max_high_score_key, 0);
        int maxScoreNQuestions = SharedPrefsUtils.getInt(this, R.string.max_high_score_nquestions_key, 0);

        int lasScore = SharedPrefsUtils.getInt(this, R.string.last_score_key, 0);
        int lasScoreNQuestions = SharedPrefsUtils.getInt(this, R.string.last_score_nquestions_key, 0);

        lastScoreTextView.setText(lasScore + " / " + lasScoreNQuestions);
        maxScoreTextView.setText(maxScore + " / " + maxScoreNQuestions);
    }
}
