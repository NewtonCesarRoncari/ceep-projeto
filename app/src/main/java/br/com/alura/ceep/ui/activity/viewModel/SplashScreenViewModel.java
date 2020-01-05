package br.com.alura.ceep.ui.activity.viewModel;

import android.content.SharedPreferences;

public class SplashScreenViewModel {

    public SplashScreenViewModel() {
    }

    public void adicionarPreferenceSplashScreen(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(chavePreferencia, true);
        editor.apply();
    }

    public Boolean verificaPreferencia(SharedPreferences preferences, String chavePreferencia) {
        return preferences.contains(chavePreferencia);
    }
}
