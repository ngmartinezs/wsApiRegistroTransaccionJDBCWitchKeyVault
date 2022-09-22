package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.web;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos.StatusDTO;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.dtos.TransanctionDTO;
import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.domain.services.RegisterTransactionService;


@RestController
@RequestMapping("/registerTransaction")
public class RegisterTransactionController {
    
    @Autowired
    private RegisterTransactionService registerTransactionService;

    @PostMapping("/save")
    public ResponseEntity<TransanctionDTO> save(@RequestBody TransanctionDTO transanctionDTO)
    {
        TransanctionDTO lTransanctionDTO = null;
        try {
            lTransanctionDTO = registerTransactionService.saveTransanctionDTO(transanctionDTO);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(lTransanctionDTO, HttpStatus.OK);
    }

    @GetMapping("/getAllOptional")
    public ResponseEntity<List<TransanctionDTO>> getAllOptional()
    {
        try 
        {
            return  registerTransactionService.getAllWithOptional().map(mapper ->new ResponseEntity<>(mapper, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getAllList")
    public ResponseEntity<List<TransanctionDTO>> getAllList()
    {
        List<TransanctionDTO> list= null;
        try 
        {
           list = registerTransactionService.getAllWithList();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/ping")
    public ResponseEntity<StatusDTO> ping(){
        StatusDTO lStatusDTO = new StatusDTO();
        
        lStatusDTO.setStatus("Enabled");
        lStatusDTO.setVersion("1.0");
        lStatusDTO.setDateNow(LocalDateTime.now());
        return new ResponseEntity<>(lStatusDTO,HttpStatus.OK);
    }
}
