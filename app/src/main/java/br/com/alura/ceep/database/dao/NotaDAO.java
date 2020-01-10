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

    @Query("SELECT * FROM nota ORDER BY nota.posicaoAdapter DESC")
    List<Nota> todos();

    @Query("SELECT * FROM nota WHERE id = (SELECT max(id) FROM nota)")
    Nota buscaUltimaNotaSalva();
}
