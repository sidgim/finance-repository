package com.glara.application.service;

import com.glara.application.mapper.AccountTypeMapper;
import com.glara.domain.model.AccountType;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountTypeServiceImpl implements AccountTypeService {
    private static final Logger LOGGER = Logger.getLogger(AccountTypeServiceImpl.class);

    @Inject
    AccountTypeRepository accountTypeRepository;

    @Inject
    AccountTypeMapper accountTypeMapper;

    @WithSession
    @Override
    public Uni<AccountTypeDTO> getAccountType(UUID id) {
        return accountTypeRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("AccountType not found"))
                .onItem().transform(accountTypeMapper::toDTO)
                .onFailure().invoke(error -> LOGGER.error("Failed to getAccountType: " + error.getMessage(), error));
    }

    @WithSession
    @Override
    public Uni<List<AccountTypeDTO>> findAllAccountTypes() {
        return accountTypeRepository.findAll().list()
                .onItem().transform(accountTypes -> accountTypes.stream()
                        .map(accountTypeMapper::toDTO)
                        .toList())
                .onFailure().recoverWithItem(List.of());
    }

    @WithTransaction
    @Override
    public Uni<AccountTypeDTO> createAccountType(AccountTypeDTO accountTypeDTO) {
        AccountType accountType = accountTypeMapper.toEntity(accountTypeDTO);
        return accountTypeRepository.persist(accountType)
                .replaceWith(accountType)
                .onItem().invoke(createdAccountType -> LOGGER.infof("AccountType created successfully: %s", createdAccountType.getId()))
                .onFailure().invoke(error -> LOGGER.error("Failed to createAccountType: " + error.getMessage(), error))
                .map(accountTypeMapper::toDTO);
    }

    @WithTransaction
    @Override
    public Uni<AccountTypeDTO> updateAccountType(AccountTypeDTO accountTypeDTO, UUID id) {
        return accountTypeRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("AccountType not found"))
                .onItem().invoke(existingAccountType -> {
                    existingAccountType.setName(accountTypeDTO.name());
                })
                .call(accountTypeRepository::persistAndFlush)
                .onItem().transform(accountTypeMapper::toDTO);
    }



    @WithTransaction
    @Override
    public Uni<Void> deleteAccountTypeById(UUID id) {
        return accountTypeRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("AccountType not found"))
                .onItem().ifNotNull().call(existingAccountType -> accountTypeRepository.deleteById(id))
                .onFailure().invoke(error -> LOGGER.error("Error al eliminar el tipo de cuenta: " + error.getMessage(), error))
                .replaceWith(true)
                .onFailure().recoverWithNull().replaceWithVoid();
    }
}