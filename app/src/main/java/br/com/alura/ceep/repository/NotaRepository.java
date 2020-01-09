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

    public List<Nota> todos() {
        List<Nota> notas = new ArrayList<>();
        new BaseAsyncTask<>(dao::todos, notas::addAll).execute();
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
        return buscaUltimaNota();
    }

    private LiveData<Nota> buscaUltimaNota() {
        MutableLiveData<Nota> notaSalva = new MutableLiveData<>();
        new BaseAsyncTask<>(this.dao::buscaUltimaNota, notaSalva::setValue).execute();
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
        notaPosicaoInicial.setPosicaoAdapter(notaPosicaoFinal.getPosicaoAdapter());
        notaPosicaoFinal.setPosicaoAdapter(notaPosicaoInicial.getPosicaoAdapter());
        altera(notaPosicaoInicial);
        altera(notaPosicaoFinal);
    }

}
