package org.binay.roachsample.repository;

import org.binay.roachsample.accounts.Account;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public interface AccountRepository extends ReactiveCrudRepository<Account, Long> {
    @Query(value = "select balance from Account where id=?1")
    BigDecimal getBalance(Long id);

    @Modifying
    @Query("update Account set balance = balance + ?2 where id=?1")
    void updateBalance(Long id, BigDecimal balance);
}
