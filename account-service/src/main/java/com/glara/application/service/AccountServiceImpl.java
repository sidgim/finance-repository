package com.glara.application.service;

import com.glara.domain.model.AccountType;
import com.glara.dto.AccountDTO;
import com.glara.application.mapper.AccountMapper;
import com.glara.dto.TransactionDTO;
import com.glara.infrastructure.external.TransactionServiceClient;
import com.glara.infrastructure.repository.AccountRepository;
import com.glara.domain.model.Account;

import com.glara.infrastructure.repository.AccountTypeRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class);
    @Inject
    AccountTypeRepository accountTypeRepository;

    @Inject
    AccountRepository accountRepository;

    @Inject
    AccountMapper accountMapper;

    @RestClient
    TransactionServiceClient transactionServiceClient;

    @WithSession
    @Override
    public Uni<AccountDTO> getAccount(UUID id) {
        return accountRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Account not found"))
                .onItem().transform(accountMapper::toDTO)
                .onFailure().invoke(error -> LOGGER.error("Failed to getAccount: " + error.getMessage(), error));
    }

    @WithSession
    @Override
    public Uni<List<AccountDTO>> findAllAccounts(int page, int size) {
        return accountRepository.findAllAccounts(page, size)
                .onItem().transform(accounts -> accounts.stream()
                        .map(accountMapper::toDTO)
                        .toList())
                .onFailure().recoverWithItem(List.of());
    }

    @WithTransaction
    @Override
    public Uni<AccountDTO> createAccount(AccountDTO accountDTO) {
        return accountTypeRepository.findById(UUID.fromString(accountDTO.accountTypeId()))
                .onItem().ifNull().failWith(() -> new NotFoundException("AccountType not found"))
                .flatMap(accountType -> {
                    Account account = accountMapper.toEntity(accountDTO);
                    return accountRepository.persist(account)
                            .replaceWith(account)
                            .map(accountMapper::toDTO);
                });
    }


    @WithTransaction
    @Override
    public Uni<AccountDTO> updateAccount(AccountDTO dto, UUID id) {
        Account account = accountMapper.toEntity(dto);

        return accountRepository.update(account, id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Account not found"))
                .onItem().transform(accountMapper::toDTO)
                .onFailure().invoke(error -> LOGGER.error("Failed to update account: " + error.getMessage(), error));
    }

    @WithTransaction
    @Override
    public Uni<Void> deleteAccountById(UUID id) {
        return accountRepository.findById(id)
                .onItem().ifNull().failWith(() -> new NotFoundException("Account not found"))
                .onItem().ifNotNull().call(existingAccount -> accountRepository.deleteById(id))
                .onFailure().invoke(error -> LOGGER.error("Error al eliminar la cuenta: " + error.getMessage(), error))
                .replaceWith(true)
                .onFailure().recoverWithNull().replaceWithVoid();
    }

    public Uni<AccountDTO> getTransactionsForAccount(UUID id) {
        return getAccount(id)
                .onItem().transformToUni(accountDTO ->
                        transactionServiceClient.getTransactionsByAccount(id)
                                .onItem().transform(transactions -> {
                                        System.out.println("Transactions: " + transactions);
                                         return new AccountDTO(
                                                accountDTO.id(),
                                                accountDTO.name(),
                                                accountDTO.currentBalance(),
                                                accountDTO.accountTypeId(),
                                                accountDTO.userId(),
                                                transactions
                                        );
                                        }
                                )
                );
    }




}