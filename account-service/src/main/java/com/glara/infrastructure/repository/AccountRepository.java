package com.glara.infrastructure.repository;

import com.glara.domain.model.Account;
import io.quarkus.cache.CacheResult;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<Account, UUID> {

    @CacheResult(cacheName = "account-cache")
    public Uni<Account> findById(UUID id) {
        return find("id = ?1 AND deleted = false", id).firstResult();
    }

    public Uni<List<Account>> findAllAccounts(int page, int size) {
        return find("deleted = false").page(page, size).list();
    }

    public Uni<List<Account>> getAllAccountsByUserId(UUID userId) {
        return find("userId = ?1 AND deleted = false", userId).list();
    }

    public Uni<Account> update(Account account, UUID id) {
        return findById(id)
                .onItem().ifNotNull().invoke(existingAccount -> {
                    existingAccount.setName(account.getName());
                    existingAccount.setCurrentBalance(account.getCurrentBalance());
                })
                .onItem().ifNotNull().call(this::persistAndFlush);
    }


    public Uni<Boolean> deleteById(UUID id) {
        return findById(id)
                .onItem().ifNotNull().invoke(account -> account.setDeleted(true))
                .onItem().ifNotNull().call(this::persistAndFlush)
                .replaceWith(Boolean.TRUE);
    }

    public Uni<Long> count() {
        return count("deleted = false");
    }
}
