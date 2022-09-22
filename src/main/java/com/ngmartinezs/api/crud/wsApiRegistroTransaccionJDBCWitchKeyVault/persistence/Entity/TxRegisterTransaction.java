package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.Entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@NoArgsConstructor
@ToString
public class TxRegisterTransaction {
    @Id
    private int id;
    private String typeTransaction;
    private Double valueTransaction;
    private String typePersonId;
    private Double numberPersonId;
    private String namesPerson;
    private String lastNamePerson;
    private LocalDateTime dateCreated;
    private String userCreated;
    private LocalDateTime dateLastModify;
    private String userLastModify;
}
