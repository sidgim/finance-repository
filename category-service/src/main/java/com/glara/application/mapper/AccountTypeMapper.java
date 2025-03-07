package com.glara.application.mapper;

import com.glara.domain.model.AccountType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface AccountTypeMapper {

    AccountTypeDTO toDTO(AccountType accountType);

    AccountType toEntity(AccountTypeDTO dto);
}
