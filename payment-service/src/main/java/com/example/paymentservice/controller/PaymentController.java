package com.example.paymentservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {
	
	private int temporaryFailureCount = 0;
   
	@GetMapping("/api/payments")
	public String processPayment(
	        @RequestParam(defaultValue = "normal") String mode)
	        throws InterruptedException {

	    if ("slow".equalsIgnoreCase(mode)) {

	        Thread.sleep(10000);

	        return "Payment completed after delay";
	    }

	    if ("fail".equalsIgnoreCase(mode)) {

	        throw new RuntimeException("Payment Service failed");
	    }

	    if ("temporary".equalsIgnoreCase(mode)) {

	        temporaryFailureCount++;

	        if (temporaryFailureCount <= 2) {

	            throw new RuntimeException(
	                    "Temporary Payment failure - attempt "
	                    + temporaryFailureCount
	            );
	        }

	        return "Payment successful after temporary failure";
	    }

	    return "Payment successful";
	}
    
}