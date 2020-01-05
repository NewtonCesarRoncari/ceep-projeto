package br.com.alura.ceep.ui.activity.viewModel;

import android.content.SharedPreferences;

public class ListaNotasViewModel {

    public ListaNotasViewModel() {
    }

    public void adicionarPreferenceLayout(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(chavePreferencia, true);
        editor.apply();
    }

    public void removerPreferenceLayout(SharedPreferences preferences, String chavePreferencia) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(chavePreferencia);
        editor.apply();
    }
}
