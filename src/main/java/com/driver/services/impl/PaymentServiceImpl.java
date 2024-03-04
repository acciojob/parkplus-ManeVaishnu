package com.driver.services.impl;

import com.driver.model.Payment;
import com.driver.model.PaymentMode;
import com.driver.model.Reservation;
import com.driver.repository.PaymentRepository;
import com.driver.repository.ReservationRepository;
import com.driver.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    ReservationRepository reservationRepository2;
    @Autowired
    PaymentRepository paymentRepository2;

    @Override
    public Payment pay(Integer reservationId, int amountSent, String mode) throws Exception {
       
        PaymentMode paymentMode;
        try {
            paymentMode = PaymentMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception("Payment mode not detected");
        }

        Reservation reservation = reservationRepository2.findById(reservationId)
        .orElseThrow(() -> new Exception("Reservation not found"));
        
        int billAmount = reservation.getTotalPrice();
        if (amountSent < billAmount) {
            throw new Exception("Insufficient Amount");
        }

        Payment payment = new Payment();
        payment.setReservation(reservation);
        payment.setAmountPaid(amountSent);
        payment.setPaymentMode(paymentMode);

        return paymentRepository2.save(payment);
    }
}
