package com.rideflowauthservice.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends BaseModel{
    @Column(nullable = false)
    private String passengerName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password; // encrypted password
    @Column(nullable = false)
    private String phoneNumber;
    @OneToMany(mappedBy = "passenger")
    private List<Booking>bookingList=new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
