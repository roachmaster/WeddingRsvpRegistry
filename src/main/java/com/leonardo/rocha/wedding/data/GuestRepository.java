package com.leonardo.rocha.wedding.data;

import org.springframework.data.repository.CrudRepository;

public interface GuestRepository extends CrudRepository<Guest, Integer> {
    Guest findByName(String name);
    Guest findById(int id);
}