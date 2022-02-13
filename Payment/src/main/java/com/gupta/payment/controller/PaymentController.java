package com.gupta.payment.controller;

import com.gupta.payment.jpa.model.TransactionDetailsEntity;
import com.gupta.payment.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    TransactionRepository transactionRepository;

    /**
     * @param request
     * @return saved entities transactionId(primary key)
     */
    @PostMapping("/transaction")
    public ResponseEntity<Integer> generateTransactionId(@RequestBody TransactionDetailsEntity request){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionRepository.save(request).getTransactionId());
    }

    /**
     * @param transactionId
     * @return transactionEntity for given transactionId
     */
    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<TransactionDetailsEntity> getDetails(@PathVariable Integer transactionId){
        return ResponseEntity.ok().body(transactionRepository.findById(transactionId).get());
    }
}
