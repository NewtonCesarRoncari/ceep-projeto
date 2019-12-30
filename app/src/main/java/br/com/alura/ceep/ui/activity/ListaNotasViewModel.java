package br.com.alura.ceep.ui.activity;

import android.content.SharedPreferences;

class ListaNotasViewModel {

    public static final String PREFERENCIA_GRID_LAYOUT = "grid_layout";

    ListaNotasViewModel() { }

    void adicionarPreferenceLayout(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREFERENCIA_GRID_LAYOUT, true);
        editor.apply();
    }

    void removerPreferenceLayout(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREFERENCIA_GRID_LAYOUT);
        editor.apply();
    }
}
