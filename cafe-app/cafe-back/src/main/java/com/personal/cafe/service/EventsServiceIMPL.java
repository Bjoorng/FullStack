package com.personal.cafe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.cafe.entities.Event;
import com.personal.cafe.repositories.EventsRepository;

@Service
public class EventsServiceIMPL implements EventsService{
	
	@Autowired
	private EventsRepository eventsRepository;

	@Override
	public List<Event> findAll() {
		return eventsRepository.findAll();
	}

	@Override
	public Event save(Event event) {
		Event e = new Event();
		e.setEvName(event.getEvName());
		e.setGuestName(event.getGuestName());
		e.setSeats(event.getSeats());
		e.setIsPrivate(event.getIsPrivate());
		e.setDate(event.getDate());
		eventsRepository.save(e);
		return e;
	}

	@Override
	public void deleteById(Long id) {
		Event e = eventsRepository.getById(id);
		eventsRepository.delete(e);
	}

	@Override
	public Event getById(Long id) {
		Optional<Event> event = eventsRepository.findById(id);
		return event.orElse(null);
	}
	
}
