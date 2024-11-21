package org.cryptostream.repository;

import org.cryptostream.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    List<Balance> findByUserId(Integer userId);
    Balance findByUserIdAndCurrency(Integer userId, String currency);
}