package com.bayzdelivery.dto;


import com.bayzdelivery.utils.PersonRole;
import jakarta.validation.constraints.NotNull;


public record PersonRegisterResponse(Long id, String name, String email, String registrationNumber, PersonRole role) {
    public PersonRegisterResponse(Long id, String name, String registrationNumber, @NotNull(message = "Role is required") PersonRole role) {
        this(id, name, null, registrationNumber, role);
    }
}
