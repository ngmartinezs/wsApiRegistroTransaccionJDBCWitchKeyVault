package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.mapper;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos.TransanctionDTO;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.Entity.TxRegisterTransaction;

@Mapper(componentModel="spring")
public interface RegisterTransactionDTOMapper {
   
    @Mappings({
        @Mapping(source="transactionId",target="id"),
        @Mapping(source="typeTransaction",target="typeTransaction"),
        @Mapping(source="valueTransaction",target="valueTransaction"),
        @Mapping(source="typePersonId",target="typePersonId"),
        @Mapping(source="numberPersonId",target="numberPersonId"),
        @Mapping(source="namesPerson",target="namesPerson"),
        @Mapping(source="lastNamePerson",target="lastNamePerson"),
        @Mapping(source="dateCreated",target="dateCreated", qualifiedByName = "toLocalDateTime"),
        @Mapping(source="userCreated",target="userCreated"),
        @Mapping(source="dateLastModify", target="dateLastModify",qualifiedByName = "toLocalDateTime")
    }) TxRegisterTransaction toRegistreTransaction(TransanctionDTO transactionDTO);

    @InheritInverseConfiguration
    @Mapping(source="id",target="transactionId")
    @Mapping(source="dateCreated",target="dateCreated", qualifiedByName = "toString")
    @Mapping(source = "dateLastModify",target = "dateLastModify", qualifiedByName = "toString")
    @Mapping(source = "userLastModify",target = "userLastModify")
    TransanctionDTO toTransanctionDTO(TxRegisterTransaction registerTransaction);

    List<TransanctionDTO> toTransanctionDTOs(List<TxRegisterTransaction> registersTransaction);


    @Named("toLocalDateTime")
    public static LocalDateTime toLocalDateTime(String timeCreate)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return timeCreate != null || ! timeCreate.isEmpty() ?LocalDateTime.parse(timeCreate,dateTimeFormatter) : null;
    }

    @Named("toString")
    public static String toString(LocalDateTime localDateTime)
    {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return localDateTime != null? dateTimeFormatter.format(localDateTime):null;
    }
}
