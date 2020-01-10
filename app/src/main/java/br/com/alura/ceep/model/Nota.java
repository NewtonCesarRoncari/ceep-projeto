package br.com.alura.ceep.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Nota implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String titulo;
    private String descricao;
    private String cor;
    private int posicaoAdapter;

    @Ignore
    public Nota(Long id, String titulo, String descricao, String cor, int posicaoAdapter) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cor = cor;
        this.posicaoAdapter = posicaoAdapter;
    }

    public Nota() {
    }

    public Nota(Nota nota) {
        this.id = nota.id;
        this.titulo = nota.titulo;
        this.descricao = nota.descricao;
        this.cor = nota.cor;
        this.posicaoAdapter = nota.posicaoAdapter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getPosicaoAdapter() {
        return posicaoAdapter;
    }

    public void setPosicaoAdapter(int posicaoAdapter) {
        this.posicaoAdapter = posicaoAdapter;
    }
}