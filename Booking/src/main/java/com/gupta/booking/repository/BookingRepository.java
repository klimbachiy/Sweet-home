package com.gupta.booking.repository;

import com.gupta.booking.jpa.model.BookingInfoEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepository extends CrudRepository<BookingInfoEntity, Integer> {
}
