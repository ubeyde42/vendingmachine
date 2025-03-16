package com.ubeyde.sample.repository;

import com.ubeyde.sample.entity.Deposit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepositRepository extends JpaRepository<Deposit, Long> {

}