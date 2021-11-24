package com.leonardo.rocha.wedding.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

@Entity
@Table(name = "guest_tbl")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private int maxGuest;
    private int confirmedGuest;
    private boolean going;

    public Guest() {
    }

    public Guest(String name, int maxGuest) {
        this.name = name;
        this.maxGuest = maxGuest;
        this.confirmedGuest = 0;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getMaxGuest() {
        return maxGuest;
    }
    public void setMaxGuest(int maxGuest) {
        this.maxGuest = maxGuest;
    }
    public Integer getConfirmedGuest() {
        return confirmedGuest;
    }
    public boolean isGoing() {
        return going;
    }
    public void setGoing(boolean going) {
        this.going = going;
    }

    public void setConfirmedGuest(int confirmedGuest) {
        this.confirmedGuest = confirmedGuest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Guest)) return false;

        Guest guest = (Guest) o;

        return new EqualsBuilder().append(getMaxGuest(), guest.getMaxGuest()).append(getId(), guest.getId()).append(getName(), guest.getName()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getId()).append(getName()).append(getMaxGuest()).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("maxGuest", maxGuest)
                .append("confirmedGuest", confirmedGuest)
                .toString();
    }
}