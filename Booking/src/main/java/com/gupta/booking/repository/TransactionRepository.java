package com.gupta.booking.repository;

import com.gupta.booking.jpa.model.TransactionDetailsEntity;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<TransactionDetailsEntity, Integer> {
}
