package com.glara.infrastructure.repository;

import com.glara.domain.model.AccountType;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class AccountTypeRepository implements PanacheRepositoryBase<AccountType, UUID> {
}
