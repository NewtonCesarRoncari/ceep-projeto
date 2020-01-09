package br.com.alura.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Cores;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.ui.recyclerview.adapter.CoresAdapter;

import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_COR_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_NOTA;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.CHAVE_POSICAO;
import static br.com.alura.ceep.ui.activity.NotaActivityConstantes.POSICAO_INVALIDA;

public class FormularioNotaActivity extends AppCompatActivity {


    public static final String TITULO_APPBAR_INSERE = "Insere nota";
    public static final String TITULO_APPBAR_ALTERA = "Altera nota";
    private int posicaoRecibida = POSICAO_INVALIDA;
    private TextView titulo;
    private TextView descricao;
    private ConstraintLayout formularioLayout;
    private String corNota = Cores.BRANCO.getValor();
    private Nota notaRecebida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERE);
        inicializaCampos();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERA);
            this.notaRecebida = (Nota) dadosRecebidos
                    .getSerializableExtra(CHAVE_NOTA);
            posicaoRecibida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_INVALIDA);
            preencheCampos(notaRecebida);
        }

        configuraRecyclerView();
        recuperaCorEmCicloDeVida(savedInstanceState);
    }

    private void preencheCampos(Nota notaRecebida) {
        titulo.setText(notaRecebida.getTitulo());
        descricao.setText(notaRecebida.getDescricao());
        formularioLayout.setBackgroundColor(Color.parseColor(notaRecebida.getCor()));
        this.corNota = notaRecebida.getCor();
    }

    private void inicializaCampos() {
        titulo = findViewById(R.id.formulario_nota_titulo);
        descricao = findViewById(R.id.formulario_nota_descricao);
        formularioLayout = findViewById(R.id.formulario_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota_salva, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (ehMenuSalvaNota(item)) {
            Nota notaCriada = criaNota();
            retornaNota(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void retornaNota(Nota nota) {
        Intent resultadoInsercao = new Intent();
        resultadoInsercao.putExtra(CHAVE_NOTA, nota);
        resultadoInsercao.putExtra(CHAVE_POSICAO, posicaoRecibida);
        setResult(Activity.RESULT_OK, resultadoInsercao);
    }

    @NonNull
    private Nota criaNota() {
        if (!ehNotaASerEditada()) {
            return new Nota(notaRecebida.getId(), titulo.getText().toString(),
                    descricao.getText().toString(),
                    this.corNota, notaRecebida.getPosicaoAdapter());
        } else {
            return new Nota(null, titulo.getText().toString(),
                    descricao.getText().toString(),
                    this.corNota, 0);
        }
    }

    private boolean ehNotaASerEditada() {
        return notaRecebida == null;
    }

    private boolean ehMenuSalvaNota(MenuItem item) {
        return item.getItemId() == R.id.menu_formulario_nota_ic_salva;
    }

    private void configuraAdapter(RecyclerView listaCores) {
        CoresAdapter adapter = new CoresAdapter(this);
        listaCores.setAdapter(adapter);
        adapter.setOnItemNotaClickListener(cor -> {
            formularioLayout.setBackgroundColor(Color.parseColor(cor));
            corNota = cor;
        });
    }

    private void configuraRecyclerView() {
        RecyclerView listaCores = findViewById(R.id.cores_notas_recyclerview);
        configuraAdapter(listaCores);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(CHAVE_COR_NOTA, this.corNota);
    }

    private void recuperaCorEmCicloDeVida(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            formularioLayout.setBackgroundColor(Color.parseColor(
                    savedInstanceState.getString(CHAVE_COR_NOTA)));
            this.corNota = savedInstanceState.getString(CHAVE_COR_NOTA);
        }
    }
}
