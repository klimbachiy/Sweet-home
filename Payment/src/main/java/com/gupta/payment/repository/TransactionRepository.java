package com.gupta.payment.repository;

import com.gupta.payment.jpa.model.TransactionDetailsEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionDetailsEntity, Integer> {
}
