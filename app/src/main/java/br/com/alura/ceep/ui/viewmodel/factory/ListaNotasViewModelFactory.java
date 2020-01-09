package br.com.alura.ceep.ui.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.alura.ceep.repository.NotaRepository;
import br.com.alura.ceep.ui.viewmodel.ListaNotasViewModel;

public class ListaNotasViewModelFactory implements ViewModelProvider.Factory {

    private final NotaRepository repository;

    public ListaNotasViewModelFactory(NotaRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListaNotasViewModel(repository);
    }
}
