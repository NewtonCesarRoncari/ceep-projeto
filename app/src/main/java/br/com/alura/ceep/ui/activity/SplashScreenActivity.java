package br.com.alura.ceep.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.ceep.R;
import br.com.alura.ceep.repository.SharedPreferencesRepository;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String NOME_PREFERENCIA = "splash_screen_preference";
    public static final String PREFERENCIA_PRIMEIRA_VEZ = "primeira_vez";
    private int delay = 2000;
    private SharedPreferences preferences;
    private SharedPreferencesRepository preference =
            new SharedPreferencesRepository();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        this.preferences = getSharedPreferences(NOME_PREFERENCIA, MODE_PRIVATE);

        verificaAppAbertoPrimeiraVez();

        configuraTempoSplasScreen(this.delay);
    }

    private void verificaAppAbertoPrimeiraVez() {
        if (!preference.verificaPreferencia(preferences, PREFERENCIA_PRIMEIRA_VEZ)) {
            preference.adicionarPreference(preferences, PREFERENCIA_PRIMEIRA_VEZ);
        } else {
            this.delay = 500;
        }
    }

    private void configuraTempoSplasScreen(int tempoDaSplashScreen) {
        Handler handler = new Handler();
        handler.postDelayed(() -> vaiParaListaNotasActivity(), tempoDaSplashScreen);
    }

    private void vaiParaListaNotasActivity() {
        startActivity(new Intent(
                SplashScreenActivity.this, ListaNotasActivity.class));
        finish();
    }

}
