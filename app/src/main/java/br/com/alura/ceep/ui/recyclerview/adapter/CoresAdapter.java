package br.com.alura.ceep.ui.recyclerview.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.R;

public class CoresAdapter extends RecyclerView.Adapter<CoresAdapter.CorViewHolder> {

    private final List<String> cores = new ArrayList<>();
    private final Context context;

    public CoresAdapter(Context context) {
        this.context = context;
        preencheListaCores();
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
        cores.add("#408EC9");
        cores.add("#FFFFFF");
        cores.add("#EC2F4B");
        cores.add("#9ACD32");
        cores.add("#F9F256");
        cores.add("#F1CBFF");
        cores.add("#D2D4DC");
        cores.add("#A47C48");
        cores.add("#BE29EC");
    }

    class CorViewHolder extends RecyclerView.ViewHolder {

        private View viewCor;

        CorViewHolder(View itemView) {
            super(itemView);
            viewCor = itemView.findViewById(R.id.view_cor);
        }

        void vincula(String cor) {
            Drawable drawable = viewCor.getBackground();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawable.setTint(Color.parseColor(cor));
            }
        }


    }
}
