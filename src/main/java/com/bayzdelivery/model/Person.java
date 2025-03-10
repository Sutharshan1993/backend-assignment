package com.bayzdelivery.model;

import com.bayzdelivery.utils.PersonRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 432154291451321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull
    @Email
    private String email;

    private String registrationNumber;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(email, person.email) &&
                Objects.equals(name, person.name) &&
                Objects.equals(registrationNumber, person.registrationNumber) &&
                role == person.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, name, registrationNumber, role);
    }
}
