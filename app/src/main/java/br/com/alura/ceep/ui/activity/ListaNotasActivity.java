package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.database.ConnectionDatabase;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.repository.NotaRepository;
import br.com.alura.ceep.repository.SharedPreferencesRepository;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;
import br.com.alura.ceep.ui.recyclerview.helper.callback.NotaItemTouchHelperCallback;
import br.com.alura.ceep.ui.viewmodel.ListaNotasViewModel;
import br.com.alura.ceep.ui.viewmodel.factory.ListaNotasViewModelFactory;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_ALTERA_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CODIGO_REQUISICAO_INSERE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class ListaNotasActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR = "Notas";
    public static final String NOME_PREFERENCIA = "layout_preferences";
    public static final String PREFERENCIA_GRID_LAYOUT = "grid_layout";
    private ListaNotasAdapter adapter;
    private RecyclerView listaNotas;
    private SharedPreferencesRepository preference = new SharedPreferencesRepository();
    private SharedPreferences preferences;
    private ConnectionDatabase connectionDatabase;
    private ListaNotasViewModel viewModel;
    private List<Nota> todasNotas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);

        setTitle(TITULO_APPBAR);

        this.connectionDatabase = (ConnectionDatabase) ConnectionDatabase.getInstance(this);
        this.preferences = getSharedPreferences(NOME_PREFERENCIA, MODE_PRIVATE);

        configuraInstanciaViewModel();
        pegaTodasNotas();
        configuraRecyclerView(todasNotas);
        configuraBotaoInsereNota();
    }

    private void configuraInstanciaViewModel() {
        ListaNotasViewModelFactory factory = new ListaNotasViewModelFactory(
                new NotaRepository(connectionDatabase));
        ViewModelProvider provider = ViewModelProviders.of(this, factory);
        this.viewModel = provider.get(ListaNotasViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        final MenuItem itemGrid = menu.findItem(R.id.menu_lista_notas_layout_ic_grid);
        final MenuItem itemLinear = menu.findItem(R.id.menu_lista_notas_layout_ic_linear);
        final MenuItem itemFeedback = menu.findItem(R.id.menu_lista_notas_feedback_formulario);

        if (preference.verificaPreferenciaSalva(preferences, PREFERENCIA_GRID_LAYOUT)) {
            configuraGridLayout(itemGrid, itemLinear);
        } else {
            configuraLinearLayout(itemGrid, itemLinear);
        }

        itemGrid.setOnMenuItemClickListener(menuItem -> {
            configuraGridLayout(itemGrid, itemLinear);
            preference.adicionarPreference(
                    preferences, PREFERENCIA_GRID_LAYOUT);
            return true;
        });

        itemLinear.setOnMenuItemClickListener(menuItem -> {
            configuraLinearLayout(itemGrid, itemLinear);
            preference.removerPreference(
                    preferences, PREFERENCIA_GRID_LAYOUT);
            return true;
        });

        itemFeedback.setOnMenuItemClickListener(menuItem -> {
            vaiParaFormularioFeedbackActivity();
            return true;
        });
        return super.onPrepareOptionsMenu(menu);
    }

    private void configuraGridLayout(MenuItem itemGrid, MenuItem itemLinear) {
        itemGrid.setVisible(false);
        itemLinear.setVisible(true);
        listaNotas.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
    }

    private void configuraLinearLayout(MenuItem itemGrid, MenuItem itemLinear) {
        itemGrid.setVisible(true);
        itemLinear.setVisible(false);
        listaNotas.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    private void configuraBotaoInsereNota() {
        TextView botaoInsereNota = findViewById(R.id.lista_notas_insere_nota);
        botaoInsereNota.setOnClickListener(view -> vaiParaFormularioNotaActivityInsere());
    }

    private void vaiParaFormularioNotaActivityInsere() {
        Intent iniciaFormularioNota =
                new Intent(ListaNotasActivity.this,
                        FormularioNotaActivity.class);
        startActivityForResult(iniciaFormularioNota,
                CODIGO_REQUISICAO_INSERE_NOTA);
    }

    private void vaiParaFormularioFeedbackActivity() {
        Intent vaiParaFormularioFeedback =
                new Intent(ListaNotasActivity.this,
                        FormularioFeedbackActivity.class);
        startActivity(vaiParaFormularioFeedback);
    }

    private void pegaTodasNotas() {
        viewModel.todos().observe(this, notasRetornadas ->
                this.todasNotas.addAll(notasRetornadas));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ehResultadoInsereNota(requestCode, data)) {

            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                insere(notaRecebida);
            }

        }

        if (ehResultadoAlteraNota(requestCode, data)) {
            if (resultadoOk(resultCode)) {
                Nota notaRecebida = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                int posicaoRecebida = data.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
                if (ehPosicaoValida(posicaoRecebida)) {
                    altera(notaRecebida, posicaoRecebida);
                }
            }
        }
    }

    private void altera(Nota nota, int posicaoRecebida) {
        viewModel.altera(nota);
        adapter.altera(posicaoRecebida, nota);
    }

    private boolean ehPosicaoValida(int posicaoRecebida) {
        return posicaoRecebida > POSICAO_INVALIDA;
    }

    private boolean ehResultadoAlteraNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoAlteraNota(requestCode) &&
                temNota(data);
    }

    private boolean ehCodigoRequisicaoAlteraNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_ALTERA_NOTA;
    }

    private void insere(Nota nota) {
        viewModel.insere(adapter.adicionaPosicaoNota(nota)).observe(this, notaSalva ->
                adapter.insere(notaSalva));
    }

    private boolean ehResultadoInsereNota(int requestCode, Intent data) {
        return ehCodigoRequisicaoInsereNota(requestCode) &&
                temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra(CHAVE_NOTA);
    }

    private boolean resultadoOk(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoRequisicaoInsereNota(int requestCode) {
        return requestCode == CODIGO_REQUISICAO_INSERE_NOTA;
    }

    private void configuraRecyclerView(List<Nota> todasNotas) {
        this.listaNotas = findViewById(R.id.lista_notas_recyclerview);
        configuraAdapter(todasNotas, listaNotas);
        configuraItemTouchHelper(listaNotas);
    }

    private void configuraItemTouchHelper(RecyclerView listaNotas) {
        ItemTouchHelper itemTouchHelper =
                new ItemTouchHelper(new NotaItemTouchHelperCallback(adapter, connectionDatabase));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void configuraAdapter(List<Nota> todasNotas, RecyclerView listaNotas) {
        adapter = new ListaNotasAdapter(this, todasNotas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemNotaClickListener(this::vaiParaFormularioNotaActivityAltera);
    }

    private void vaiParaFormularioNotaActivityAltera(Nota nota, int posicao) {
        Intent abreFormularioComNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        abreFormularioComNota.putExtra(CHAVE_NOTA, nota);
        abreFormularioComNota.putExtra(CHAVE_POSICAO, posicao);
        startActivityForResult(abreFormularioComNota, CODIGO_REQUISICAO_ALTERA_NOTA);
    }

}
