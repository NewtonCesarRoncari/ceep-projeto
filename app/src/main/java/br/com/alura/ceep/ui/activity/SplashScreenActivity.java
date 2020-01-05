package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.ui.activity.viewModel.SplashScreenViewModel;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String NOME_PREFERENCIA = "splash_screen_preference";
    public static final String PREFERENCIA_PRIMEIRA_VEZ = "primeira_vez";
    private int delay = 2000;
    private SharedPreferences preferences;
    private SplashScreenViewModel viewModel = new SplashScreenViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.preferences = getSharedPreferences(NOME_PREFERENCIA, MODE_PRIVATE);

        verificaAppAbertoPrimeiraVez();

        configuraTempoSplasScreen(this.delay);
    }

    private void verificaAppAbertoPrimeiraVez() {
        if (!viewModel.verificaPreferencia(preferences, PREFERENCIA_PRIMEIRA_VEZ)) {
            viewModel.adicionarPreferenceSplashScreen(preferences, PREFERENCIA_PRIMEIRA_VEZ);
        } else {
            this.delay = 500;
        }
    }

    private void configuraTempoSplasScreen(int tempoDaSplashScreen) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                vaiParaListaNotasActivity();
            }
        }, tempoDaSplashScreen);
    }

    private void vaiParaListaNotasActivity() {
        startActivity(new Intent(
                SplashScreenActivity.this, ListaNotasActivity.class));
        finish();
    }

}
