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
public class Driver extends BaseModel{
    private String driverName;
    private  String licenceNumber;
    @OneToMany(mappedBy = "driver")
    List<Booking> bookingList=new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
