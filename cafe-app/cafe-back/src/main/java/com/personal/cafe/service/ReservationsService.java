package com.personal.cafe.service;

import java.util.List;

import com.personal.cafe.entities.Event;
import com.personal.cafe.entities.Reservation;
import com.personal.cafe.entities.User;

public interface ReservationsService {
	List<Reservation> findAll();
	List<Reservation> findByUser(User user);
    Reservation save(User user, Event event, Integer seats);
    Reservation getById(Long id);
	void deleteById(Long id);
}
