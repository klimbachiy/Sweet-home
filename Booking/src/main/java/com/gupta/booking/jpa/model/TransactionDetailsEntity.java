package com.gupta.booking.jpa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionDetailsEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer transactionId;

    @Column
    private String paymentMode;

    @Column
    private Integer bookingId;

    @Column
    private String upiId;

    @Column
    private String cardNumber;

}
