package com.example.gerenciadordeatividadescomplementares.AtividadeComplementar;

import java.util.Date;

public class AtividadeComplementar {
    private String id;
    private String nome;
    private float cargaHoraria;
    private String periodo;
    private Tipo tipo;
    private String arquivo;

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(float cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public AtividadeComplementar() {}

    public AtividadeComplementar(String nome, float cargaHoraria, String periodo, Tipo tipo, String arquivo) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.periodo = periodo;
        this.tipo = tipo;
        this.arquivo = arquivo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public enum Tipo {
        PESQUISA,
        EXTENSAO,
        ENSINO,
        GESTAO;
    }
}
