package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos;



import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class TransanctionDTO {
    private String transactionId;
    private String typeTransaction;
    private Double valueTransaction;
    private String typePersonId;
    private Double numberPersonId;
    private String namesPerson;
    private String lastNamePerson;
    private String dateCreated;
    private String userCreated;
    private String dateLastModify;
    private String userLastModify;
}
