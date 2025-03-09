package com.glara.infrastructure.repository;

import com.glara.dto.SubcategoryDTO;
import com.glara.infrastructure.utils.SQLFileReader;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class SubcategoryRepository {

    private final Pool client;

    @Inject
    public SubcategoryRepository(Pool client) {
        this.client = client;
    }

    public Uni<List<SubcategoryDTO>> findAllSubcategory(Integer size, Integer page) {
        boolean usePagination = (size != null && size > 0) && (page != null && page > 0);
        String sql = usePagination ? SQLFileReader.readSQL("subcategory", "find_all_filter.sql") : SQLFileReader.readSQL("subcategory", "find_all.sql");
        return client.preparedQuery(sql)
                .execute()
                .onItem().transformToMulti(rows -> Multi.createFrom().iterable(rows))
                .onItem().transform(SubcategoryDTO::fromRow)
                .collect().asList();
    }

    public Uni<SubcategoryDTO> findByIdSubcategory(UUID id) {
        String sql = SQLFileReader.readSQL("subcategory", "find_by_id.sql");
        return client.preparedQuery(sql)
                .execute()
                .onItem().transform(rows -> rows.iterator().hasNext() ? SubcategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull();
    }

    public Uni<SubcategoryDTO> createSubcategory(SubcategoryDTO subcategoryDTO) {
        String sql = SQLFileReader.readSQL("subcategory", "insert.sql");
        System.out.println("SQL: " + sql);
        return client.preparedQuery(sql)
                .execute(Tuple.of(
                        subcategoryDTO.name(),
                        subcategoryDTO.description(),
                        subcategoryDTO.categoryId()
                ))
                .onItem().transform(rows -> rows.iterator().hasNext() ? SubcategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull();
    }

    public Uni<SubcategoryDTO> updateSubcategory(UUID id, SubcategoryDTO subcategoryDTO) {
        String sql = SQLFileReader.readSQL("subcategory", "update.sql");
        return client.preparedQuery(sql)
                .execute(Tuple.of( subcategoryDTO.name(),
                        subcategoryDTO.description(),
                        subcategoryDTO.categoryId(),
                        id))
                .onItem().transform(rows -> rows.iterator().hasNext() ? SubcategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull();
    }

    public Uni<Void> deleteSubcategory(UUID id) {
        String sql = SQLFileReader.readSQL("subcategory", "delete.sql");
        return client.preparedQuery(sql)
                .execute()
                .onItem().transform(rows -> rows.iterator().hasNext() ? SubcategoryDTO.fromRow(rows.iterator().next()) : null)
                .onFailure().recoverWithNull()
                .replaceWithVoid();
    }

}
