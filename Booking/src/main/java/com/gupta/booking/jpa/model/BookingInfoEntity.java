package com.gupta.booking.jpa.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class BookingInfoEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer bookingId;

    @Column
    private Date fromDate;

    @Column
    private Date toDate;

    @Column
    private String aadharNumber;

    @Column
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer numOfRooms;

    @Column
    private String roomNumbers;

    @Column
    private Integer roomPrice;

    @Column
    private int transactionId;

    @Column
    private Date bookedOn;
}