package com.gupta.booking.controller;

import com.gupta.booking.exception.APIException;
import com.gupta.booking.jpa.model.BookingInfoEntity;
import com.gupta.booking.jpa.model.TransactionDetailsEntity;
import com.gupta.booking.repository.BookingRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.*;

@RestController
@RequestMapping("/hotel")
public class BookingController {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * @param request - BookingInfoEntity
     * @return saved BookingInfoEntity
     */
    @PostMapping("/booking")
    public ResponseEntity<BookingInfoEntity> booking(@RequestBody BookingInfoEntity request){
        //genearte roomPrice calculation
        request.setRoomPrice(1000* request.getNumOfRooms()*((int)Duration.between(request.getFromDate().toInstant(), request.getToDate().toInstant()).toDays()));
        request.setRoomNumbers(StringUtils.join(getRandomNumbers(request.getNumOfRooms()),","));
        request.setBookedOn(new Date());
        BookingInfoEntity response = bookingRepository.save(request);
        //make numOfRooms null to exclude it from json response
        //Used @JsonInclude NONNULL in entity field
        response.setNumOfRooms(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * @param count - numberOfRooms
     * @return randam room numbers list
     */
    public static ArrayList<String> getRandomNumbers(int count){
        Random rand = new Random();
        int upperBound = 100;
        ArrayList<String>numberList = new ArrayList<String>();

        for (int i=0; i<count; i++){
            numberList.add(String.valueOf(rand.nextInt(upperBound)));
        }
        return numberList;
    }

    /**
     * @param request - TransactionDetailsEntity
     * @param bookingId
     * @return save transaction entity and transactionId in booking table
     */
    @PostMapping("booking/{bookingId}/transaction")
    public ResponseEntity transaction(@RequestBody TransactionDetailsEntity request, @PathVariable Integer bookingId){
        if(!request.getPaymentMode().equalsIgnoreCase("UPI") && !request.getPaymentMode().equalsIgnoreCase("CARD")){
            APIException ex = new APIException("Invalid mode of payment", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(ex);
        }
        //get payment service baseurl from service registry
        List<ServiceInstance> instances=discoveryClient.getInstances("payment-service");
        ServiceInstance serviceInstance=instances.get(0);
        String baseUrl=serviceInstance.getUri().toString();

        ResponseEntity<Integer> transactionId = restTemplate.postForEntity(baseUrl+"/payment/transaction", request, Integer.class);
        Optional<BookingInfoEntity> booking = bookingRepository.findById(bookingId);
        if(!booking.isPresent()){
            APIException ex = new APIException(" Invalid Booking Id ", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(ex);
        }
        BookingInfoEntity bookingResponse = booking.get();
        bookingResponse.setTransactionId(transactionId.getBody());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingRepository.save(bookingResponse));
    }
}
