package br.com.alura.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.alura.ceep.R;

public class FormularioFeedbackActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Feedback";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_feedback);

        setTitle(TITULO_APPBAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (ehMenuEnviarFeedback(item)) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean ehMenuEnviarFeedback(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_feedback_enviar;
    }
}
