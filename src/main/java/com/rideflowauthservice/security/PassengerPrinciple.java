package com.rideflowauthservice.security;


import com.rideflow.rideflowentityservice.models.Passenger;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
@Getter

// PassengerPrincipal (wraps your User entity so Spring Security understands it):
public class PassengerPrinciple implements UserDetails {
    private final Passenger user;

    public PassengerPrinciple(Passenger user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getPassengerName();
    }


    public String getUserEmail() {
        return user.getEmail();
    }

    // the four boolean methods below are also from UserDetails
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }


}