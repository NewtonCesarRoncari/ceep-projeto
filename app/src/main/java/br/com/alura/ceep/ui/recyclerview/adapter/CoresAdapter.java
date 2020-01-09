package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;
import br.com.alura.ceep.model.Cores;

public class CoresAdapter extends RecyclerView.Adapter<CoresAdapter.CorViewHolder> {

    private final List<String> cores = new ArrayList<>();
    private final Context context;
    private CoresAdapter.OnItemClickListener onItemClickListener;

    public CoresAdapter(Context context) {
        this.context = context;
        preencheListaCores();
    }

    public void setOnItemNotaClickListener(CoresAdapter.OnItemClickListener onItemNotaClickListener) {
        this.onItemClickListener = onItemNotaClickListener;
    }

    @Override
    public CoresAdapter.CorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_cor, parent, false);
        return new CorViewHolder(viewCriada);
    }

    @Override
    public void onBindViewHolder(CorViewHolder holder, int position) {
        holder.vincula(cores.get(position));
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    private void preencheListaCores() {
        cores.add(Cores.AZUL.getValor());
        cores.add(Cores.BRANCO.getValor());
        cores.add(Cores.VERMELHO.getValor());
        cores.add(Cores.VERDE.getValor());
        cores.add(Cores.AMARELO.getValor());
        cores.add(Cores.LILAS.getValor());
        cores.add(Cores.CINZA.getValor());
        cores.add(Cores.MARROM.getValor());
        cores.add(Cores.ROXO.getValor());
    }

    public interface OnItemClickListener {
        void onItemClick(String cor);
    }

    class CorViewHolder extends RecyclerView.ViewHolder {

        private View viewCor;

        CorViewHolder(View itemView) {
            super(itemView);
            viewCor = itemView.findViewById(R.id.view_cor);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(cores.get(getAdapterPosition()));
                }
            });
        }

        void vincula(String cor) {
            Drawable drawable = viewCor.getBackground();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setTint(Color.parseColor(cor));
            }
        }

    }
}
