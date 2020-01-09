package br.com.alura.ceep.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import br.com.alura.ceep.model.Nota;
import br.com.alura.ceep.repository.NotaRepository;

public class ListaNotasViewModel extends ViewModel {

    private final NotaRepository repository;

    public ListaNotasViewModel(NotaRepository repository) {
        this.repository = repository;
    }

    public LiveData<Nota> insere(Nota nota) {
        return repository.insere(nota);
    }

    public void altera(Nota nota) {
        repository.altera(nota);
    }
}
