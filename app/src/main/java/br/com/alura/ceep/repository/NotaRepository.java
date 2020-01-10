package br.com.alura.ceep.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ceep.asynctask.BaseAsyncTask;
import br.com.alura.ceep.database.ConnectionDatabase;
import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

public class NotaRepository {

    private final NotaDAO dao;

    public NotaRepository(ConnectionDatabase connectionDatabase) {
        this.dao = connectionDatabase.getNotaDAO();
    }

    public LiveData<List<Nota>> todos() {
        MutableLiveData<List<Nota>> notas = new MutableLiveData<>();
        new BaseAsyncTask<>(dao::todos, notas::setValue).execute();
        return notas;
    }

    public void altera(Nota nota) {
        new BaseAsyncTask<>(() -> {
            this.dao.altera(nota);
            return null;
        }, resultado -> {
        }).execute();
    }

    public LiveData<Nota> insere(final Nota nota) {
        new BaseAsyncTask<>(() -> {
            this.dao.insere(nota);
            return null;
        }, resultado -> {
        }).execute();
        return buscaUltimaNotaSalva();
    }

    private LiveData<Nota> buscaUltimaNotaSalva() {
        MutableLiveData<Nota> notaSalva = new MutableLiveData<>();
        new BaseAsyncTask<>(this.dao::buscaUltimaNotaSalva, notaSalva::setValue).execute();
        return notaSalva;
    }

    public void remove(Nota nota) {
        new BaseAsyncTask<>(() -> {
            this.dao.remove(nota);
            return null;
        }, resultado -> {
        }).execute();
    }

    public void troca(Nota notaPosicaoInicial, Nota notaPosicaoFinal) {
        int posicaoDaNotaInicial = notaPosicaoInicial.getPosicaoAdapter();
        notaPosicaoInicial.setPosicaoAdapter(notaPosicaoFinal.getPosicaoAdapter());
        notaPosicaoFinal.setPosicaoAdapter(posicaoDaNotaInicial);
        altera(notaPosicaoInicial);
        altera(notaPosicaoFinal);
    }

}
