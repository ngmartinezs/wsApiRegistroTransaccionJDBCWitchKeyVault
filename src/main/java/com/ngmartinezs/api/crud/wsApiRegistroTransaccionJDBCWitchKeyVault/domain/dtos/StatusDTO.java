package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos;


import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatusDTO {
    
    private String status;
    private String version;
    private LocalDateTime dateNow;
}
