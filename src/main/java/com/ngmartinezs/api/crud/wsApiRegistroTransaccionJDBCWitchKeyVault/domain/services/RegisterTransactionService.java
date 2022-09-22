package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos.TransanctionDTO;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.repositoryInterfaces.RegisterTransactionRepository;


@Service
public class RegisterTransactionService {
    
    @Autowired
    private RegisterTransactionRepository registerTransactionRepository;

    public Optional<List<TransanctionDTO>> getAllWithOptional()
    {
        Optional<List<TransanctionDTO>> listTransactionDTO= null;
        listTransactionDTO = registerTransactionRepository.getAllWithOptional();
        
        return listTransactionDTO;
    }

    public List<TransanctionDTO> getAllWithList()
    {
        List<TransanctionDTO> listTransactionDTO= null;
        listTransactionDTO = registerTransactionRepository.getAllWithList();
        
        return listTransactionDTO;
    }

    public TransanctionDTO saveTransanctionDTO(TransanctionDTO transanctionDTO)
    {
        //Random lRandom = new Random();
        //transanctionDTO.setTransactionId(String.valueOf(lRandom.nextInt(1,2000)));
        return registerTransactionRepository.save(transanctionDTO);
    }
}
