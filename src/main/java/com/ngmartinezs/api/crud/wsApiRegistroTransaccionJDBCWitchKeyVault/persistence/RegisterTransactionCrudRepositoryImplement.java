package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos.TransanctionDTO;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.repositoryInterfaces.RegisterTransactionRepository;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.Entity.TxRegisterTransaction;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.crud.RegisterTransactionCrudRepository;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.mapper.RegisterTransactionDTOMapper;

@Repository
public class RegisterTransactionCrudRepositoryImplement implements RegisterTransactionRepository {
    
    @Autowired
    RegisterTransactionCrudRepository registerTransactionCrudRepository;

    @Autowired
    RegisterTransactionDTOMapper registerTransactionDTOMapper;


    public TransanctionDTO save(TransanctionDTO transanctionDTO){
        
        TxRegisterTransaction registerTransaction = null;
        
        registerTransaction =  registerTransactionCrudRepository.save(registerTransactionDTOMapper.toRegistreTransaction(transanctionDTO));

        return registerTransactionDTOMapper.toTransanctionDTO(registerTransaction);
    }

    public Optional<List<TransanctionDTO>> getAllWithOptional()
    {
        List<TransanctionDTO> list = new ArrayList<TransanctionDTO>();
        
        registerTransactionCrudRepository.findAll().forEach((element) -> list.add( registerTransactionDTOMapper.toTransanctionDTO(element)));

        return Optional.of(list);
    }

    public List<TransanctionDTO> getAllWithList()
    {
        List<TransanctionDTO> list = new ArrayList<TransanctionDTO>();   
        registerTransactionCrudRepository.findAll().forEach((element) -> list.add(registerTransactionDTOMapper.toTransanctionDTO(element)));
        return list;
    }

}
