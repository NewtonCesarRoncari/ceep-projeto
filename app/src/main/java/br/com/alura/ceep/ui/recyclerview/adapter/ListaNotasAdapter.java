package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Nota;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {

    private final List<Nota> notas;
    private final Context context;
    private ListaNotasAdapter.OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context, List<Nota> notas) {
        this.context = context;
        this.notas = notas;
    }

    public void setOnItemNotaClickListener(ListaNotasAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListaNotasAdapter.NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_nota, parent, false);
        return new NotaViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(ListaNotasAdapter.NotaViewHolder holder, int position) {
        holder.vincula(notas.get(position));
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }


    public void insere(Nota nota) {
        notas.add(0, nota);
        notifyItemInserted(0);
    }

    public void altera(int posicao, Nota nota) {
        notas.set(posicao, nota);
        notifyDataSetChanged();
    }

    public Nota remove(int posicao) {
        Nota nota = notas.get(posicao);
        notas.remove(posicao);
        notifyItemRemoved(posicao);

        diminuiPosicaoAdapterDasNotasPosteriores(nota);
        return nota;
    }

    private void diminuiPosicaoAdapterDasNotasPosteriores(Nota nota) {
        for (Nota notaASerDiminuidaPosicaoAdapter : notas) {

            if (notaASerDiminuidaPosicaoAdapter.getPosicaoAdapter() > nota.getPosicaoAdapter()) {
                notaASerDiminuidaPosicaoAdapter.setPosicaoAdapter(
                        notaASerDiminuidaPosicaoAdapter.getPosicaoAdapter() - 1);
            }
        }
    }

    public Nota retornaNotaPorPosicao(int posicao) {
        return notas.get(posicao);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public Nota adicionaPosicaoNota(Nota nota) {
        nota.setPosicaoAdapter(notas.size());
        return nota;
    }

    public interface OnItemClickListener {
        void onItemClick(Nota nota, int posicao);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {

        private final TextView titulo;
        private final TextView descricao;
        private CardView cardView;
        private Nota nota;

        NotaViewHolder(View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.item_nota_titulo);
            descricao = itemView.findViewById(R.id.item_nota_descricao);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(view ->
                    onItemClickListener.onItemClick(nota, getAdapterPosition()));
        }

        void vincula(Nota nota) {
            this.nota = nota;
            preencheCampo(nota);
        }

        private void preencheCampo(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
            cardView.setCardBackgroundColor(Color.parseColor(nota.getCor()));
        }
    }

}
