package br.com.alura.ceep.ui.activity;

import android.content.SharedPreferences;

class ListaNotasViewModel {

    ListaNotasViewModel() {
    }

    void adicionarPreferenceLayout(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(chavePreferencia, true);
        editor.apply();
    }

    void removerPreferenceLayout(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(chavePreferencia);
        editor.apply();
    }
}
