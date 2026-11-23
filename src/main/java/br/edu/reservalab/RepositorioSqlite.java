package br.edu.reservalab;

import java.util.List;

/**
 * TODO da atividade: implementar persistência JDBC usando SQLite.
 * A aplicação começa com RepositorioMemoria; a troca para esta classe
 * só deve ser feita depois que estes métodos funcionarem.
 */
public class RepositorioSqlite implements RepositorioReservas {
    public RepositorioSqlite() {
        // TODO 1: criar a pasta data/, conectar ao banco e criar as tabelas.
        // TODO 2: inserir o catálogo inicial sem duplicá-lo a cada execução.
    }
    @Override public List<Equipamento> listarEquipamentos() {
        // TODO 3: SELECT dos equipamentos; mapear ResultSet.
        throw new UnsupportedOperationException("Implementar listarEquipamentos() com JDBC");
    }
    @Override public List<Reserva> listarReservas() {
        // TODO 4: SELECT das reservas, incluindo seu status.
        throw new UnsupportedOperationException("Implementar listarReservas() com JDBC");
    }
    @Override public Reserva criar(String equipamentoId, String responsavel, String data, String turno) {
        // TODO 5: INSERT parametrizado; impedir reserva dupla no mesmo período.
        throw new UnsupportedOperationException("Implementar criar() com JDBC");
    }
    @Override public boolean cancelar(long id) {
        // TODO 6: UPDATE para status CANCELADA, sem apagar o histórico.
        throw new UnsupportedOperationException("Implementar cancelar() com JDBC");
    }
}
