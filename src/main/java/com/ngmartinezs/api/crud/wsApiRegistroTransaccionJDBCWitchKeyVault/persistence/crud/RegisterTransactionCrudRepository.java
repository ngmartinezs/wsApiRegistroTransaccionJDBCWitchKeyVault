package com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.crud;

import org.springframework.data.repository.CrudRepository;

import com.ngmartinezs.api.crud.wsApiRegistroTransaccionJDBCWitchKeyVault.persistence.Entity.TxRegisterTransaction;

public interface RegisterTransactionCrudRepository extends CrudRepository<TxRegisterTransaction,String> {
    
}
