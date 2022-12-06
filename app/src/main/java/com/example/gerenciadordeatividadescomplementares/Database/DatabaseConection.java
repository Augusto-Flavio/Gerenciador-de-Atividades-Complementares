package com.example.gerenciadordeatividadescomplementares.Database;

import com.example.gerenciadordeatividadescomplementares.AtividadeComplementar.AtividadeComplementar;

import java.util.List;

public abstract class DatabaseConection {
    public abstract boolean cadastrarAtividade(AtividadeComplementar atividadeComplementar);
    public abstract boolean editarAtividade(String id, AtividadeComplementar atividadeComplementar);
    public abstract boolean excluirAtividade(String id);
    public abstract boolean inserirDadosDoAluno(String id);
    public abstract List<AtividadeComplementar> getAllAtividades();
}
