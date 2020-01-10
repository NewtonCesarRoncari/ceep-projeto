package br.com.alura.ceep.ui.recyclerview.helper.callback;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import br.com.alura.ceep.database.ConnectionDatabase;
import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.repository.NotaRepository;
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final ListaNotasAdapter adapter;
    private final ConnectionDatabase connectionDatabase;

    public NotaItemTouchHelperCallback(ListaNotasAdapter adapter,
                                       ConnectionDatabase connectionDatabase) {
        this.adapter = adapter;
        this.connectionDatabase = connectionDatabase;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int marcacoesDeDeslize = ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        int marcacoesDeArrastar = ItemTouchHelper.DOWN | ItemTouchHelper.UP
                | ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        return makeMovementFlags(marcacoesDeArrastar, marcacoesDeDeslize);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Nota notaPosicaoInicial = adapter.retornaNotaPorPosicao(viewHolder.getAdapterPosition());
        Nota notaPosicaoFinal = adapter.retornaNotaPorPosicao(target.getAdapterPosition());

        trocaNoBanco(notaPosicaoInicial, notaPosicaoFinal);
        adapter.troca(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    private void trocaNoBanco(Nota notaPosicaoInicial, Nota notaPosicaoFinal) {
        new NotaRepository(connectionDatabase).troca(notaPosicaoInicial, notaPosicaoFinal);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoDaNotaDeslizada);
    }

    private void removeNota(int posicaoDaNotaDeslizada) {
        new NotaRepository(connectionDatabase).remove(adapter.remove(posicaoDaNotaDeslizada));
    }
}
