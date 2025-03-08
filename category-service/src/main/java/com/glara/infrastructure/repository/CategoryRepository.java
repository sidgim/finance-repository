package com.glara.infrastructure.repository;

import com.glara.dto.CategoryDTO;
import com.glara.infrastructure.utils.SQLFileReader;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.SqlResult;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class CategoryRepository {

    @Inject
    Pool client;

    public Uni<List<CategoryDTO>> findAll(Integer size, Integer page) {
        boolean usePagination = (size != null && size > 0) && (page != null && page > 0);

        String sql = usePagination ?   SQLFileReader.readSQL("category", "find_all_filter.sql") :SQLFileReader.readSQL("category", "find_all.sql");

        Tuple params = usePagination ? Tuple.of(size, (page - 1) * size) : Tuple.tuple();

        return client.preparedQuery(sql)
                .execute(params)
                .onItem().transformToMulti( rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(CategoryDTO::fromRow)
                .collect().asList();
    }


    public Uni<CategoryDTO> findById(UUID id) {
        String sql = SQLFileReader.readSQL("category", "find_by_id.sql");
        return client.preparedQuery(sql)
                .execute(Tuple.of(id)) // PreparedStatement  -> Tuple.of(id)
                .onItem().transform(rows -> rows.iterator().hasNext() ? CategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull();
    }

    public Uni<CategoryDTO> createCategory(CategoryDTO categoryDTO) {
        String sql = SQLFileReader.readSQL("category", "insert.sql");
        System.out.println("SQL: " + sql);
        return client.preparedQuery(sql)
                .execute(Tuple.of(categoryDTO.name(), categoryDTO.description()))
                .onItem().transform(rows -> rows.iterator().hasNext() ? CategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull();
    }

    public Uni<CategoryDTO> updateCategory(UUID id, CategoryDTO categoryDTO) {
        String sql = SQLFileReader.readSQL("category", "update.sql");
        return client.preparedQuery(sql)
                .execute(Tuple.of(categoryDTO.name(), categoryDTO.description(), id))
                .onItem().transform(rows -> rows.iterator().hasNext() ? CategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull();
    }

    public Uni<Integer> deleteCategory(UUID id) {
        String sql = SQLFileReader.readSQL("category", "delete.sql");
        return client.preparedQuery(sql)
                .execute(Tuple.of(id))
                .onItem().transform(SqlResult::rowCount);
    }

}
