package com.bayzdelivery.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "delivery")
public class Delivery implements Serializable{

  @Serial
  private static final long serialVersionUID = 123765351514001L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull
  @Column(name = "start_time")
  Instant startTime;

  @NotNull
  @Column(name = "end_time")
  Instant endTime;

  @Column(name = "distance")
  Long distance;

  @Column(name = "price")
  Long price;

  @Column(name = "comission")
  Long comission;

  @ManyToOne
  @JoinColumn(name = "delivery_man_id", referencedColumnName = "id")
  Person deliveryMan;

  @ManyToOne
  @JoinColumn(name = "customer_id", referencedColumnName = "id")
  Person customer;


    @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((distance == null) ? 0 : distance.hashCode());
    result = prime * result + ((deliveryMan == null) ? 0 : deliveryMan.hashCode());
    result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((customer == null) ? 0 : customer.hashCode());
    result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Delivery other = (Delivery) obj;
    if (distance == null) {
      if (other.distance != null)
        return false;
    } else if (!distance.equals(other.distance))
      return false;
    if (deliveryMan == null) {
      if (other.deliveryMan != null)
        return false;
    } else if (!deliveryMan.equals(other.deliveryMan))
      return false;
    if (endTime == null) {
      if (other.endTime != null)
        return false;
    } else if (!endTime.equals(other.endTime))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (customer == null) {
      if (other.customer != null)
        return false;
    } else if (!customer.equals(other.customer))
      return false;
    if (startTime == null) {
        return other.startTime == null;
    } else return startTime.equals(other.startTime);
  }

  @Override
  public String toString() {
    return "Delivery [id=" + id + ", startTime=" + startTime + ", endTime=" + endTime + ", distance=" + distance + ", deliveryMan=" + deliveryMan + ", customer=" + customer + "]";
  }



}
