package com.glara.infrastructure.repository;

import com.glara.dto.VendorDTO;
import com.glara.infrastructure.util.SQLFileReader;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Pool;
import io.vertx.mutiny.sqlclient.RowSet;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class VendorRepository {

    @Inject
    Pool pool;

    public Uni<List<VendorDTO>> findAllVendor() {
        String sql = SQLFileReader.readSQL("vendor", "find_all.sql");
        return pool.preparedQuery(sql)
                .execute()
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .onItem().transform(VendorDTO::fromRow)
                .collect().asList();
    }

    public Uni<VendorDTO> createVendor(VendorDTO vendorDTO) {
        String sql = SQLFileReader.readSQL("vendor", "insert.sql");
        return pool.preparedQuery(sql)
                .execute(Tuple.of(vendorDTO.name()))
                .onItem().transform(RowSet::iterator)
                .onItem().transform(rowSet -> rowSet.hasNext() ? VendorDTO.fromRow(rowSet.next()) : null);
    }

    public Uni<VendorDTO> updateVendor(UUID id, VendorDTO vendorDTO) {
        String sql = SQLFileReader.readSQL("vendor", "update.sql");
        return pool.preparedQuery(sql)
                .execute(Tuple.of(vendorDTO.name(), id))
                .onItem().transform(rowSet -> rowSet.rowCount() > 0 ? vendorDTO : null);
    }

    public Uni<Boolean> deleteVendor(UUID id) {
        String sql = SQLFileReader.readSQL("vendor", "delete.sql");
        return pool.preparedQuery(sql)
                .execute(Tuple.of(id))
                .onItem().transform(rowSet -> rowSet.rowCount() > 0)
                .onFailure().invoke(error ->
                        System.out.println("Error al eliminar el vendedor: " + error.getMessage()));
    }

}
