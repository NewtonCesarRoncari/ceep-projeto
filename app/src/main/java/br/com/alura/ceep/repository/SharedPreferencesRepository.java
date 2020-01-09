package br.com.alura.ceep.repository;

import android.content.SharedPreferences;

public class SharedPreferencesRepository {

    public SharedPreferencesRepository() {
    }

    public void adicionarPreference(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(chavePreferencia, true);
        editor.apply();
    }

    public void removerPreference(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(chavePreferencia);
        editor.apply();
    }

    public Boolean verificaPreferencia(SharedPreferences preferences, String chavePreferencia) {
        return preferences.contains(chavePreferencia);
    }
}
