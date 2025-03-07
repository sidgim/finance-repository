package com.glara.application.service;

import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.UUID;

public interface AccountService {


    Uni<AccountDTO> getAccount(UUID id);

    Uni<List<AccountDTO>> findAllAccounts(int page, int size);

    Uni<AccountDTO> createAccount(AccountDTO accountDTO);

    Uni<AccountDTO> updateAccount(AccountDTO accountDTO, UUID id);

    Uni<Void> deleteAccountById(UUID id);

   // Uni<List<AccountDTO>> getAllAccountsByUserId(Long userId);
}
