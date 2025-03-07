package com.glara.application.service;

import io.smallrye.mutiny.Uni;

import java.util.List;
import java.util.UUID;

public interface AccountTypeService {
     Uni<AccountTypeDTO> getAccountType(UUID id);

     Uni<List<AccountTypeDTO>> findAllAccountTypes();

     Uni<AccountTypeDTO> createAccountType(AccountTypeDTO accountTypeDTO);

     Uni<AccountTypeDTO> updateAccountType(AccountTypeDTO accountTypeDTO, UUID id);

     Uni<Void> deleteAccountTypeById(UUID id);
}
