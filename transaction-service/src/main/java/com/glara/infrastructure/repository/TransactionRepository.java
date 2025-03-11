package com.glara.infrastructure.repository;

import com.glara.dto.TransactionDTO;
import com.glara.infrastructure.util.SQLFileReader;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;


import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransactionRepository {
    private static final Logger log = Logger.getLogger(TransactionRepository.class);
    private final Pool pool;

    @Inject
    public TransactionRepository(Pool pool) {
        this.pool = pool;
    }

    // Método privado para extraer el primer registro del RowSet
    private TransactionDTO extractFirstRow(RowSet<Row> rows) {
        Iterator<Row> iterator = rows.iterator();
        return iterator.hasNext() ? TransactionDTO.fromRow(iterator.next()) : null;
    }

    public Uni<TransactionDTO> findByIdTransaction(UUID id) {
        String query = SQLFileReader.readSQL("transaction", "find_by_id.sql");
        return pool.preparedQuery(query)
                .execute(Tuple.of(id))
                .onItem().transform(this::extractFirstRow)
                .onFailure().invoke(error ->
                        log.errorf("Error al buscar transacción con id %s: %s", id, error.getMessage()));
    }

    public Uni<List<TransactionDTO>> findAllTransaction(Integer size, Integer page) {
        boolean usePagination = (size != null && size > 0) && (page != null && page > 0);
        String sql = usePagination
                ? SQLFileReader.readSQL("transaction", "find_all_filter.sql")
                : SQLFileReader.readSQL("transaction", "find_all.sql");
        Tuple params = usePagination
                ? Tuple.of(size, (page - 1) * size)
                : Tuple.tuple();
        return pool.preparedQuery(sql)
                .execute(params)
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .onItem().transform(TransactionDTO::fromRow)
                .onFailure().invoke(error ->
                        log.errorf("Error al consultar transacciones: %s", error.getMessage()))
                .collect().asList();
    }

    public Uni<TransactionDTO> createTransaction(TransactionDTO transactionDTO) {
        String sql = SQLFileReader.readSQL("transaction", "insert.sql");
        System.out.println("SQL: " + transactionDTO.type().toString());
        return pool.preparedQuery(sql)
                .execute(Tuple.of(
                        transactionDTO.accountId(),
                        transactionDTO.subcategoryId(),
                        transactionDTO.vendorId(),
                        transactionDTO.amount(),
                        transactionDTO.description(),
                        transactionDTO.type().toString()
                ))
                .onItem().transform(this::extractFirstRow)
                .onFailure().invoke(error ->
                        log.errorf("Error al crear transacción: %s", error.getMessage()));
    }

    public Uni<TransactionDTO> updateTransaction(UUID id, TransactionDTO transactionDTO) {
        String sql = SQLFileReader.readSQL("transaction", "update.sql");
        Tuple params = Tuple.of(
                transactionDTO.description(),
                transactionDTO.accountId(),
                transactionDTO.subcategoryId(),
                transactionDTO.type().toString(),
                transactionDTO.amount(),
                transactionDTO.createdAt()
        ).addValue(id);
        return pool.preparedQuery(sql)
                .execute(params)
                .onItem().transform(this::extractFirstRow)
                .onFailure().invoke(error ->
                        log.errorf("Error al actualizar transacción con id %s: %s", id, error.getMessage()));
    }

    public Uni<Boolean> deleteTransaction(UUID id) {
        String sql = SQLFileReader.readSQL("transaction", "delete.sql");
        return pool.preparedQuery(sql)
                .execute(Tuple.of(id))
                .onItem().transform(rows -> rows.rowCount() == 1)
                .onFailure().invoke(error ->
                        log.errorf("Error al eliminar transacción con id %s: %s", id, error.getMessage()));
    }

    public Uni<List<TransactionDTO>> findByAccountId(UUID accountId) {
        String sql = SQLFileReader.readSQL("transaction", "find_all_transaction_by_account_id.sql");
        return pool.preparedQuery(sql)
                .execute(Tuple.of(accountId))
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .onItem().transform(TransactionDTO::fromRow)
                .onFailure().invoke(error ->
                        log.errorf("Error al buscar transacciones por id de cuenta %s: %s", accountId, error.getMessage()))
                .collect().asList();
    }
}

