package br.com.alura.ceep.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import br.com.alura.ceep.database.dao.NotaDAO;
import br.com.alura.ceep.model.Nota;

@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class ConnectionDatabase extends RoomDatabase {

    private static final String NOME_BANCO = "ceep";
    private static ConnectionDatabase INSTANCIA_CONEXAO;

    public static RoomDatabase getInstance(Context context) {
        if (INSTANCIA_CONEXAO == null) {
            INSTANCIA_CONEXAO = Room.databaseBuilder(context, ConnectionDatabase.class, NOME_BANCO)
                    .build();
        }
        return INSTANCIA_CONEXAO;
    }

    public abstract NotaDAO getNotaDAO();
}
