package br.com.alura.ceep.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.ceep.model.Nota;

@Dao
public interface NotaDAO {

    @Insert
    void insere(Nota nota);

    @Update
    void altera(Nota nota);

    @Delete
    void remove(Nota nota);

    @Query("SELECT * FROM nota WHERE id LIKE :id")
    Nota buscaPorId(Integer id);

    @Query("SELECT * FROM nota ORDER BY nota.posicaoAdapter DESC")
    List<Nota> todos();

    @Query("SELECT * FROM nota WHERE :posicaoAdapter")
    Nota buscaPorPosicaoAdapter(int posicaoAdapter);

    @Query("SELECT * FROM nota WHERE id = (SELECT max(id) FROM nota)")
    Nota buscaUltimaNota();

//    private final static ArrayList<Nota> notas = new ArrayList<>();
//
//    public List<Nota> todos() {
//        return (List<Nota>) notas.clone();
//    }
//
//    public void insere(Nota... notas) {
//        NotaDAO.notas.addAll(Arrays.asList(notas));
//    }
//
//    public void altera(int posicao, Nota nota) {
//        notas.set(posicao, nota);
//    }
//
//    public void remove(int posicao) {
//        notas.remove(posicao);
//    }
//
//    public void troca(int posicaoInicio, int posicaoFim) {
//        Collections.swap(notas, posicaoInicio, posicaoFim);
//    }
//
//    public void removeTodos() {
//        notas.clear();
//    }
}
