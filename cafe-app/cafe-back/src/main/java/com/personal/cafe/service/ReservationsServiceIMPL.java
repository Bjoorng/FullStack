package com.personal.cafe.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.cafe.entities.Event;
import com.personal.cafe.entities.Reservation;
import com.personal.cafe.entities.User;
import com.personal.cafe.repositories.ReservationsRepository;

@Service
public class ReservationsServiceIMPL implements ReservationsService{

	@Autowired
	ReservationsRepository reservationsRepository;
	
	@Override
	public List<Reservation> findAll() {
		return reservationsRepository.findAll();
	}
	
	@Override
	public Reservation getById(Long reservationId) {
		Reservation res = reservationsRepository.getById(reservationId);
		return res;
	}

	@Override
	public List<Reservation> findByUser(User user) {
		List<Reservation> resList = reservationsRepository.findAll();
		List<Reservation> filteredList = new ArrayList<>();
		for(Reservation res : resList) {
			if(res.getUser().getUsername().equals(user.getUsername())) {
				filteredList.add(res);
			}
		}
		return filteredList;
	}

	@Override
	public Reservation save(User user, Event event, Integer seats) {
		Reservation res = new Reservation();
		res.setReservationDate(Date.valueOf(LocalDate.now()));
		res.setReservedSeats(seats);
		res.setReservationCost(event.getTicketPrice()*seats);
		res.setEvent(event);
		res.setUser(user);
		reservationsRepository.save(res);
		return res;
	}

	@Override
	public void deleteById(Long id) {
		Reservation res = reservationsRepository.getById(id);
		reservationsRepository.delete(res);
		
	}

}
