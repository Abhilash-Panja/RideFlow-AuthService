package com.rideflowauthservice.mapper;


import com.rideflow.rideflowentityservice.models.Booking;
import com.rideflowauthservice.dto.booking.BookingResponseDTO;
import com.rideflowauthservice.dto.review.ReviewSummaryDTO;


public class BookingMapper {

    // reviewSummary is nullable — most bookings won't have a review yet
    public static BookingResponseDTO toResponseDTO(Booking booking, ReviewSummaryDTO reviewSummary) {
        if (booking == null) return null;
        return BookingResponseDTO.builder()
                .id(booking.getId())
                .startTime(booking.getStartTime())
                .endTime(booking.getEndTime())
                .totalDistance(booking.getTotalDistance())
                .bookingStatus(booking.getBookingStatus())
                .driver(DriverMapper.toSummaryDTO(booking.getDriver()))
                .passenger(PassengerMapper.toSummaryDTO(booking.getPassenger()))
                .review(reviewSummary)
                .build();
    }
}