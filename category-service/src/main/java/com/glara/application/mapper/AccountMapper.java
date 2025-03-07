package com.glara.application.mapper;

import com.glara.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {
    @Mapping(target = "accountTypeId", source = "accountType.id")
    AccountDTO toDTO(Account account);


    @Mapping(target = "accountType.id", source = "accountTypeId")
    Account toEntity(AccountDTO dto);
}





