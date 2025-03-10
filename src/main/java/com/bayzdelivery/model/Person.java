package com.bayzdelivery.model;

import com.bayzdelivery.utils.PersonRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 432154291451321L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name")
    String name;
    @NotNull
    @Email
    @Column(name = "email")
    String email;
    @Column(name = "registration_number")
    String registrationNumber;
    @Column(name = "role")
    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    PersonRole role;

    public Person() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Person other = (Person) obj;
        if ( email == null ) {
            if ( other.email != null )
                return false;
        } else if ( !email.equals(other.email) )
            return false;
        if ( id == null ) {
            if ( other.id != null )
                return false;
        } else if ( !id.equals(other.id) )
            return false;
        if ( name == null ) {
            if ( other.name != null )
                return false;
        } else if ( !name.equals(other.name) )
            return false;
        if ( registrationNumber == null ) {
            if ( other.registrationNumber != null )
                return false;
        } else if ( !registrationNumber.equals(other.registrationNumber) )
            return false;
        if ( role == null ) {
            return other.role == null;
        } else return role.equals(other.role);
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", email=" + email + ", registrationNumber=" + registrationNumber + "]" + role + "]";
    }


}
