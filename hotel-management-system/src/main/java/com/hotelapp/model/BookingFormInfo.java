package com.hotelapp.model;

public record BookingFormInfo(
        int roomId,
        String roomTier,
        String roomSpace,
        double roomPrice,
        String checkInDate,
        String checkOutDate
){}
