package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.repositoryInterfaces;

import java.util.List;
import java.util.Optional;

import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos.TransanctionDTO;

public interface RegisterTransactionRepository {
    
    public TransanctionDTO save(TransanctionDTO transanctionDTO);

    public Optional<List<TransanctionDTO>> getAllWithOptional();

    public List<TransanctionDTO> getAllWithList();

}
